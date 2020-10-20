package pers.zdl1004.SchoolLeaveSystem.service.impl;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.zdl1004.SchoolLeaveSystem.Messages;
import pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao;
import pers.zdl1004.SchoolLeaveSystem.dao.MajorDao;
import pers.zdl1004.SchoolLeaveSystem.dao.PermissionClazzDao;
import pers.zdl1004.SchoolLeaveSystem.dao.PermissionCollageDao;
import pers.zdl1004.SchoolLeaveSystem.dao.UserDao;
import pers.zdl1004.SchoolLeaveSystem.pojo.Clazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.Major;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionClazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionCollage;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.pojo.view.UserInfoView;
import pers.zdl1004.SchoolLeaveSystem.service.UserService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;
import pers.zdl1004.SchoolLeaveSystem.util.RSAUtil;
import pers.zdl1004.SchoolLeaveSystem.util.SHA256Util;

/**
 * 用户模块的逻辑实现
 *
 * @author dzj0821
 *
 */
@Service
public class UserServiceImpl implements UserService {
	private static final String USERNAME_REGEX = "^[0-9]{6,10}$";
	private static final String NAME_REGEX = "[\\u4e00-\\u9fa5]{2,4}";
	private static final String TELEPHONE_REGEX = "^(13|14|15|17|18|19)[0-9]{9}$";
	private static final int USER_COUNT_PER_PAGE = 10;

	@Autowired
	private UserDao userDao;
	@Autowired
	private PermissionClazzDao permissionClazzDao;
	@Autowired
	private PermissionCollageDao permissionCollageDao;
	@Autowired
	private ClazzDao clazzDao;
	@Autowired
	private MajorDao majorDao;

	private Logger logger = LogManager.getLogger(UserServiceImpl.class);

//	注册模块
	@Override
	public JSONResult register(String username, String base64RSAPassword, String name, String telephone, PrivateKey privateKey) {
		// TODO 通过注解进行参数验证
		// 验证输入
		if (!Pattern.matches(USERNAME_REGEX, username)) { // 用户名不合法
			return new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidUsername"), null); //$NON-NLS-1$
		}
		if (!Pattern.matches(NAME_REGEX, name)) { // 姓名不合法
			return new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidName"), null); //$NON-NLS-1$
		}
		if (!Pattern.matches(TELEPHONE_REGEX, telephone)) { // 电话号码不合法
			return new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidTelephone"), null); //$NON-NLS-1$
		}

		JSONResult Invalidpassword = new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidPassword"), //$NON-NLS-1$
				null);
		String password = null;

		// 密码解码
		try {
			password = new String(RSAUtil.decrypt(Base64.getDecoder().decode(base64RSAPassword), privateKey));
		} catch (Exception e) {
			return Invalidpassword;
		}
		if (!valifyPassword(password)) { // $NON-NLS-1$
			return Invalidpassword;
		}

		// 验证用户名是否存在
		User user = null;
		try {
			user = userDao.selectUserByUsername(username);//调用UserDao
		} catch (Exception e) {
			logger.warn(Messages.getString("SQLError"), e); //$NON-NLS-1$
			return JSONResult.SERVER_ERROR;
		}

		if (user != null) {
			return new JSONResult(JSONCodeType.REGISTER_USERNAME_ALREADY_EXIST, Messages.getString("UsernameAlreadyExist"), //页面400，UsernameAlreadyExist=\u7528\u6237\u540d\u5df2\u5b58\u5728
					null);
		}


		// 验证结束 用户信息存入数据库
		user = new User(null, username, SHA256Util.encrypt(password), UserType.NORMAL_USER, name, telephone, null, null, null);
		try {
			userDao.insertUser(user);
		} catch (Exception e) {
			logger.warn(Messages.getString("SQLError"), e); //$NON-NLS-1$
			return JSONResult.SERVER_ERROR;
		}
//注册成功
		JSONResult success = new JSONResult(JSONCodeType.SUCCESS, Messages.getString("RegisterSuccess"), null); //$NON-NLS-1$
		success.put("user", user); //$NON-NLS-1$
		return success;
	}


//	登录模块
	@Override
	public JSONResult login(String username, String base64RSAPassword, PrivateKey privateKey) {
		// TODO 通过注解进行参数验证
		// 验证输入
		if (!Pattern.matches(USERNAME_REGEX, username)) { // $NON-NLS-1$
			return new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidUsername"), null); //$NON-NLS-1$
		}
		JSONResult Invalidpassword = new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidPassword"), //$NON-NLS-1$
				null);


		String password = null;
		// 密码解码
		try {
			password = new String(RSAUtil.decrypt(Base64.getDecoder().decode(base64RSAPassword), privateKey));
		} catch (Exception e) {
			return Invalidpassword;
		}
		if (!valifyPassword(password)) { // $NON-NLS-1$
			return Invalidpassword;
		}
		// 用户信息已存入数据库，验证用户名是否存在
		User user = null;
		try {
			user = userDao.selectUserByUsername(username);
		} catch (Exception e) {
			logger.warn(Messages.getString("SQLError"), e); //$NON-NLS-1$
			return JSONResult.SERVER_ERROR;
		}

		if (user == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, Messages.getString("UserNotFound"), //$NON-NLS-1$
					null);
		}
		// 判断密码是否与数据库密码一直一致
		String sha256Password = SHA256Util.encrypt(password);

		if (!sha256Password.equals(user.getPassword())) {
			return new JSONResult(JSONCodeType.USERNAME_OR_PASSWORD_ERROR, Messages.getString("UsernameOrPasswordError"), null); //$NON-NLS-1$
		}
//		登陆成功
		JSONResult result = new JSONResult(JSONCodeType.SUCCESS, Messages.getString("LoginSuccess"), null); //$NON-NLS-1$
		result.put("user", user); //$NON-NLS-1$
		return result;
	}

//	用户个人信息修改
	@Override
	public JSONResult modify(User user, String base64RSAOldPassword, String base64RSANewPassword, String name, String telephone, PrivateKey privateKey) {
		// TODO 通过注解进行参数验证
		// 开始验证
		if ("".equals(name)) { //$NON-NLS-1$
			name = null;
		}
		if (name != null && !Pattern.matches(NAME_REGEX, name)) { // $NON-NLS-1$
			return new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidName"), null); //$NON-NLS-1$
		}
		if ("".equals(telephone)) { //$NON-NLS-1$
			telephone = null;
		}
		if (telephone != null && !Pattern.matches(TELEPHONE_REGEX, telephone)) { // $NON-NLS-1$
			return new JSONResult(JSONCodeType.INVALID_PARAMS, Messages.getString("InvalidTelephone"), null); //$NON-NLS-1$
		}
		// 密码解密
		JSONResult invalidOldPassword = new JSONResult(JSONCodeType.INVALID_PARAMS,
				Messages.getString("InvalidOldPassword"), null); //$NON-NLS-1$
		String oldPassword = null;
		try {
			oldPassword = new String(RSAUtil.decrypt(Base64.getDecoder().decode(base64RSAOldPassword), privateKey));
		} catch (Exception e) {
			return invalidOldPassword;
		}
		if (!valifyPassword(oldPassword)) {
			return invalidOldPassword;
		}
		JSONResult invalidNewPassword = new JSONResult(JSONCodeType.INVALID_PARAMS,
				Messages.getString("InvalidNewPassword"), null); //$NON-NLS-1$
		String newPassword = null;
		try {
			newPassword = new String(RSAUtil.decrypt(Base64.getDecoder().decode(base64RSANewPassword), privateKey));
		} catch (Exception e) {
			return invalidNewPassword;
		}
		if ("".equals(newPassword)) { //$NON-NLS-1$
			newPassword = null;
		}
		if (newPassword != null && !valifyPassword(newPassword)) {
			return invalidNewPassword;
		}
		// 验证完毕
		String sha256Password = SHA256Util.encrypt(oldPassword);
		if (!user.getPassword().equals(sha256Password)) {
			return new JSONResult(JSONCodeType.OLD_PASSWORD_ERROR, Messages.getString("OldPasswordError"), null); //$NON-NLS-1$
		}
		// 更新用户信息
		User updateUser = new User();
		updateUser.setId(user.getId());
		if (newPassword != null) {
			updateUser.setPassword(SHA256Util.encrypt(newPassword));
		}
		updateUser.setName(name);
		updateUser.setTelephone(telephone);
		try {
			userDao.updateUserById(updateUser);
			user = userDao.selectUserById(user.getId());
		} catch (Exception e) {
			logger.warn(Messages.getString("SQLError"), e); //$NON-NLS-1$
			return JSONResult.SERVER_ERROR;
		}
//		修改成功
		JSONResult result = new JSONResult(JSONCodeType.SUCCESS, Messages.getString("ModifyUserInfoSuccess"), null); //$NON-NLS-1$
		result.put("user", user); //$NON-NLS-1$
		return result;
	}
//登出
	@Override
	public JSONResult logout(User user) {
		if (user == null) {
			return new JSONResult(JSONCodeType.ACCESS_DENIED, "尚未登录", null);
		}
		return new JSONResult(JSONCodeType.SUCCESS, "登出成功", null);
	}

	@Override
	public JSONResult getUserInfo(int willGetUserId, User fromUser) {
		// 未登录不能查看用户信息
		if (fromUser == null) {
			return JSONResult.ACCESS_DENIED;
		}
		// 获取被查询的用户
		User willGetUser = null;
		try {
			willGetUser = userDao.selectUserById(willGetUserId);
		} catch (Exception e) {
			logger.warn(Messages.getString("ServerError"), e); //$NON-NLS-1$
			return JSONResult.SERVER_ERROR;
		}
		// 如果没有这个用户
		if (willGetUser == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, Messages.getString("UserNotFound"), null); //$NON-NLS-1$
		}

		// 如果查看的不是自己的账号信息 验证权限
		if (willGetUserId != fromUser.getId()) {
			check: switch (fromUser.getType()) {
			case NORMAL_USER:
				return JSONResult.ACCESS_DENIED;

			case CLAZZ_ADMIN:
				// 验证被查询的用户是否是属于查询者管理的班级
				List<PermissionClazz> permissionClazzes = null;
				try {
					permissionClazzes = permissionClazzDao.selectPermissionClazzesByUserId(fromUser.getId());
				} catch (Exception e) {
					logger.warn(e);
					return JSONResult.SERVER_ERROR;
				}
				for (PermissionClazz permissionClazz : permissionClazzes) {
					if (permissionClazz.getClazz().getId().equals(willGetUser.getClazz().getId())) {
						break check;
					}
				}
				return JSONResult.ACCESS_DENIED;

			case COLLAGE_ADMIN:
				List<PermissionCollage> permissionCollages = null;
				try {
					permissionCollages = permissionCollageDao.selectPermissionCollagesByUserId(fromUser.getId());
				} catch (Exception e) {
					logger.warn(e);
					return JSONResult.SERVER_ERROR;
				}
				for (PermissionCollage permissionCollage : permissionCollages) {
					if (permissionCollage.getId().equals(willGetUser.getClazz().getMajor().getCollage().getId())) {
						break check;
					}
				}
				return JSONResult.ACCESS_DENIED;
			case SUPER_ADMIN:
				break check;
			default:
				return JSONResult.ACCESS_DENIED;
			}
		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put(Messages.getString("UserInfoDataUserName"), new UserInfoView(willGetUser)); //$NON-NLS-1$
		return new JSONResult(JSONCodeType.SUCCESS, null, data);
	}

	private boolean valifyPassword(String password) {
		if (password == null) {
			return false;
		}
		return Pattern.matches("^[a-zA-Z0-9]{9,18}$", password) //$NON-NLS-1$
				&& !Pattern.matches("^[0-9]*$", password) //$NON-NLS-1$
				&& !Pattern.matches("^[a-zA-Z]*$", password); //$NON-NLS-1$
	}

//	班级权限
	@Override
	public JSONResult getManageClazzes(User user) {
		//未登录 或 权限低于班级管理员
		if(user == null || user.getType().getCode() > UserType.CLAZZ_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		//set去重 防止出现重复的班级
		Set<Clazz> set = new HashSet<Clazz>();

		//查询用户管理的班级和年级
		List<PermissionClazz> permissionClazzs = null;
		try {
			permissionClazzs = permissionClazzDao.selectPermissionClazzesByUserId(user.getId());
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}

		List<PermissionCollage> permissionCollages = null;
		try {
			permissionCollages = permissionCollageDao.selectPermissionCollagesByUserId(user.getId());
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}

		//加入可选择名单
		for (PermissionClazz permissionClazz : permissionClazzs) {
			set.add(permissionClazz.getClazz());
		}
		for (PermissionCollage permissionCollage : permissionCollages) {
			//对于每个院级，先筛选出院里的专业
			List<Major> majors = null;
			try {
				majors = majorDao.selectMajorsByCollageId(permissionCollage.getCollage().getId());
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			for (Major major : majors) {
				//每个专业再筛选出班级
				List<Clazz> clazzs = null;
				try {
					clazzs = clazzDao.selectClazzesByMajorId(major.getId());
				} catch (Exception e) {
					logger.warn(e);
					return JSONResult.SERVER_ERROR;
				}
				for (Clazz clazz : clazzs) {
					set.add(clazz);
				}
			}
		}
		if(set.isEmpty()) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "您尚未管理任何班级", null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clazzes", set);
		return new JSONResult(JSONCodeType.SUCCESS, null, map);
	}

//	批量注册
	@Override
	public JSONResult batchRegister(String text, Integer clazzId, User user) {
		//如果用户权限低于班级管理员权限
		if(user == null || user.getType().getCode() > UserType.CLAZZ_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		JSONResult invalidClazzIdResult = new JSONResult(JSONCodeType.INVALID_PARAMS, "班级序号有误", null);
		//班级序号不存在的情况
		if(clazzId == null) {
			return invalidClazzIdResult;
		}
		//验证班级是否存在
		Clazz clazz = null;
		try {
			clazz = clazzDao.selectClazzById(clazzId);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if(clazz == null) {
			return invalidClazzIdResult;
		}
		//验证是否有班级权限
		PermissionClazz permissionClazz = null;
		try {
			permissionClazz = permissionClazzDao.selectPermissionClazzByUserIdAndClazzId(user.getId(), clazzId);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if(permissionClazz == null) {
			//验证是否有院级权限
			PermissionCollage permissionCollage = null;
			try {
				permissionCollage = permissionCollageDao.selectPermissionCollageByUserIdAndCollageId(user.getId(), clazz.getMajor().getCollage().getId());
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			if(permissionCollage == null) {
				//没有权限
				return invalidClazzIdResult;
			}
		}
		//有班级权限 验证批量注册部分text格式
		if(text == null || text.trim().equals("")) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "信息格式有误", null);
		}
		text = text.trim();
		//每行是一个账号，顺序为学号、姓名、手机，用\t分隔
		String[] lines = text.split("\n");
		ArrayList<String> haveReigstedUserList = new ArrayList<String>();
		//生成一个随机密码 3位小写字母+6位数字
		Random random = new Random();
		StringBuilder passwordBuilder = new StringBuilder();
		for(int i = 0; i < 3; i++) {
			passwordBuilder.append((char)(random.nextInt(26) + 'a'));
		}
		for(int i = 0; i < 6; i++) {
			passwordBuilder.append(random.nextInt(10));
		}
		String encPassword = SHA256Util.encrypt(passwordBuilder.toString());

		ArrayList<User> willReigsteUsers = new ArrayList<User>(lines.length);
		for (int i = 0; i < lines.length; i++) {
			String[] infos = lines[i].split("\t");
			if(infos.length != 3) {
				return new JSONResult(JSONCodeType.INVALID_PARAMS, "第" + (i + 1) + "行格式有误", null);
			}
			String studentId = infos[0];
			String name = infos[1];
			String telephone = infos[2];
			//学号
			if(!Pattern.matches("^[0-9]{10}$", studentId)) {
				return new JSONResult(JSONCodeType.INVALID_PARAMS, "第" + (i + 1) + "行学号格式有误", null);
			}
			//姓名
			if(!Pattern.matches(NAME_REGEX, name)) {
				return new JSONResult(JSONCodeType.INVALID_PARAMS, "第" + (i + 1) + "行姓名格式有误", null);
			}
			//手机
			if(!Pattern.matches(TELEPHONE_REGEX, telephone)) {
				return new JSONResult(JSONCodeType.INVALID_PARAMS, "第" + (i + 1) + "行手机格式有误", null);
			}
			//判断是否已注册过
			User willReigsteUser = null;
			try {
				willReigsteUser = userDao.selectUserByUsername(studentId);
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			if(willReigsteUser != null) {
				//注册过了
				haveReigstedUserList.add(studentId);
			} else {
				willReigsteUser = new User(null, studentId, encPassword, UserType.NORMAL_USER, name, telephone, clazz, null, null);
				willReigsteUsers.add(willReigsteUser);
			}
		}
		if(!haveReigstedUserList.isEmpty()) {
			//存在已经注册的用户
			StringBuilder builder = new StringBuilder();
			for (String string : haveReigstedUserList) {
				builder.append("用户名为" + string + "的用户已注册过").append('\n');
			}
			builder.append("存在已注册用户的情况下无法完成批量注册");
			return new JSONResult(JSONCodeType.INVALID_PARAMS, builder.toString(), null);
		}
		for (User willReigsteUser : willReigsteUsers) {
			try {
				userDao.insertUser(willReigsteUser);
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
		}
		return new JSONResult(JSONCodeType.SUCCESS, "批量注册成功，初始密码为" + passwordBuilder.toString() + "，请牢记", null);
	}


//	用户列表
@Override
public JSONResult list(int page, User user) {
	if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
		return JSONResult.ACCESS_DENIED;
	}
	List<User> users = null;
	int userCount = 0;

	try {
			users = userDao.selectUsersLimit((page - 1) * USER_COUNT_PER_PAGE, USER_COUNT_PER_PAGE);//查询所有用户
		 	userCount = userDao.selectUserCount();
	} catch (Exception e) {
		logger.warn(e);
		return JSONResult.SERVER_ERROR;
	}
//	POI模块
	int pageCount = userCount % USER_COUNT_PER_PAGE == 0 ? userCount / USER_COUNT_PER_PAGE : userCount / USER_COUNT_PER_PAGE + 1;
	int start = page - 5 > 1 ? page - 5 : 1;
	int end = page + 5 > pageCount ? pageCount : page + 5;
	List<Integer> pageList = new ArrayList<Integer>();
	for (int i = start; i <= end; i++) {
		pageList.add(i);
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("users", users);
	map.put("pageList", pageList);
	map.put("pageCount", pageCount);
	return new JSONResult(JSONCodeType.SUCCESS, null, map);
}

	@Override
	public JSONResult list( User user) {
		if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		List<User> users = null;
		try {
			users = userDao.selectUsers();//查询所有用户
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", users);
		return new JSONResult(JSONCodeType.SUCCESS, null, map);
	}


//	改变用户类型,若需要降级
	@Override
	public JSONResult changeType(Integer changeUserId, UserType changeType, User user) {
		if(user == null || user.getType().getCode() > UserType.COLLAGE_ADMIN.getCode()) {//修改过的
			return JSONResult.ACCESS_DENIED;
		}
//		需要改变得到用户的id为空和改变的用户类型为空
		if(changeUserId == null || changeType == null) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, null, null);
		}
		if(user.getId().equals(changeUserId)) {//和自己的id相同
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "不能修改自己的权限", null);
		}
//通过id查询到自己,创建changer对象
		User changeUser = null;
		try {
			changeUser = userDao.selectUserById(changeUserId);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
//如果查询为空,用户不存在
		if(changeUser == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "用户不存在", null);
		}

		User changedUser = new User(changeUser.getId(), null, null, changeType, null, null, null, null, null);//null的不改变,不为null的更新
		try {
				userDao.updateUserById(changedUser);

		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}

		return new JSONResult(JSONCodeType.SUCCESS, null, null);
	}

}
