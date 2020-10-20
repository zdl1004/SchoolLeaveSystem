package pers.zdl1004.SchoolLeaveSystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao;
import pers.zdl1004.SchoolLeaveSystem.dao.LeaveDao;
import pers.zdl1004.SchoolLeaveSystem.dao.LeaveImageDao;
import pers.zdl1004.SchoolLeaveSystem.dao.MajorDao;
import pers.zdl1004.SchoolLeaveSystem.dao.PermissionClazzDao;
import pers.zdl1004.SchoolLeaveSystem.dao.PermissionCollageDao;
import pers.zdl1004.SchoolLeaveSystem.pojo.Leave;
import pers.zdl1004.SchoolLeaveSystem.pojo.LeaveImage;
import pers.zdl1004.SchoolLeaveSystem.pojo.Major;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionClazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionCollage;
import pers.zdl1004.SchoolLeaveSystem.pojo.Clazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.LeaveService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.LeaveType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;
import pers.zdl1004.SchoolLeaveSystem.util.SHA256Util;

/**
 * 请假模块的逻辑实现
 * 
 * @author dzj0821
 *
 */
@Service
public class LeaveServiceImpl implements LeaveService {
	private Logger logger = LogManager.getLogger(LeaveServiceImpl.class);
	private static final int LEAVE_COUNT_PER_PAGE = 10;

	@Autowired
	private LeaveDao leaveDao;
	@Autowired
	private LeaveImageDao leaveImageDao;
	@Autowired
	private PermissionClazzDao permissionClazzDao;
	@Autowired
	private PermissionCollageDao permissionCollageDao;
	@Autowired
	private MajorDao majorDao;
	@Autowired
	private ClazzDao clazzDao;

//	创建申请
	@Override
	public JSONResult create(User user, int startYear, int startMonth, int startDay, int startLesson, int endYear,
                             int endMonth, int endDay, int endLesson, String reason, CommonsMultipartFile[] images, String systemPath) {
		// 不是普通用户不能请假
		if (user.getType() != UserType.NORMAL_USER) {
			return JSONResult.ACCESS_DENIED;
		}
		// 必须加入班级才能请假
		if (user.getClazz() == null) {
			return new JSONResult(JSONCodeType.ACCESS_DENIED, "必须加入班级才能请假", null);
		}
		// 获取日历对象，通过日历对象获取当前日期
		Calendar currentCalendar = Calendar.getInstance();
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		int currentDay = currentCalendar.get(Calendar.DATE);
		// 这个月的最后一天
		int lastDay = currentCalendar.getActualMaximum(Calendar.DATE);
		// 验证日期
		// 因为Calendar的月份从0开始，所以startMonth和endMonth减1
		// TODO 优化判断流程
		if (startYear < currentYear || startYear >= currentYear + 3 || startMonth - 1 < currentMonth || startMonth > 12
				|| startDay < currentDay || startDay > lastDay || endYear < currentYear || endYear >= currentYear + 3
				|| endMonth - 1 < currentMonth || endMonth > 12 || endDay < currentDay || endDay > lastDay) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "日期不符合要求", null);
		}
		// 只有年月日用于比较的Calendar
		Calendar currentCompareCalendar = Calendar.getInstance();
		currentCompareCalendar.clear();
		// 设置为当前日期
		currentCompareCalendar.set(currentYear, currentMonth, currentDay);
		// 请假开始日期的Calendar
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.clear();
		startCalendar.set(startYear, startMonth - 1, startDay);
		// 请假结束日期的Calendar
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.clear();
		endCalendar.set(endYear, endMonth - 1, endDay);
		// 请假开始日期和截止日期都必须在申请日期之后，且截止日期不能在开始日期之前
		if (currentCompareCalendar.compareTo(startCalendar) > 0 || currentCompareCalendar.compareTo(endCalendar) > 0) {
			// TODO 外部化字符串
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "请假开始和截止日期不能小于今天", null);
		}
		// 如果截止日期在开始日期前 或 截止日期的第X节课小于开始日期的第Y节课
		if (endCalendar.compareTo(startCalendar) < 0
				|| (endCalendar.compareTo(startCalendar) == 0 && endLesson <= startLesson)) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "截止日期必须大于开始日期", null);
		}
		if (images != null) {
			for (int i = 0; i < images.length; i++) {
				// 文件是否为空
				if (images[i].isEmpty()) {
					return new JSONResult(JSONCodeType.INVALID_PARAMS, "上传文件为空", null);
				}
				// 文件后缀必须为jpg或png
				String originalFilename = images[i].getOriginalFilename();
				if (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png")) {
					return new JSONResult(JSONCodeType.INVALID_PARAMS, "文件类型必须为jpg或png格式", null);
				}
				// 限制上传大小10M
				if (images[i].getSize() > 10 * 1024 * 1024) {
					return new JSONResult(JSONCodeType.INVALID_PARAMS, "单个图片大小不能超过10M", null);
				}

			}
		}
		// 验证部分结束
		// 转换日期格式
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Formatter startDateFormatter = new Formatter();
		String startDateString = startDateFormatter.format("%04d-%02d-%02d", startYear, startMonth, startDay)
				.toString();
		startDateFormatter.close();
		// Formatter不能重复使用
		Formatter endDateFormatter = new Formatter();
		String endDateString = endDateFormatter.format("%04d-%02d-%02d", endYear, endMonth, endDay).toString();
		endDateFormatter.close();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse(startDateString);
			endDate = dateFormat.parse(endDateString);
		} catch (ParseException e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}

		// 记录请假申请
		Leave leave = new Leave(0, user, user.getClazz(), user.getTelephone(), startDate, startLesson, endDate,
				endLesson, reason, null, LeaveType.WAIT, null, null);//插入数据库
		try {
			leaveDao.insertLeave(leave);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		// 保存图片
		if (images != null) {
			for (int i = 0; i < images.length; i++) {
				// 根据请假的id和图片序号改变文件名
				String imageName = SHA256Util
						.encrypt(SHA256Util.encrypt(Integer.toString(leave.getId())) + Integer.toString(i));
				// 保存在upload目录下
				String path = "upload/";
				String originalFilename = images[i].getOriginalFilename();
				if (originalFilename.endsWith(".jpg")) {
					path += imageName + ".jpg";
				} else {
					path = imageName + ".png";
				}
				File file = new File(systemPath, path);
				// 如果目录不存在且创建目录失败
				if (!file.exists() && !file.mkdirs()) {
					logger.warn("创建目录失败");
					return JSONResult.SERVER_ERROR;
				}
				try {
					// 保存文件
					images[i].transferTo(file);
				} catch (IllegalStateException | IOException e) {
					logger.warn(e);
					return JSONResult.SERVER_ERROR;
				}
				LeaveImage leaveImage = new LeaveImage(0, path, leave);
				// 插入数据库
				try {
					leaveImageDao.insertLeaveImage(leaveImage);
				} catch (Exception e) {
					logger.warn(e);
					return JSONResult.SERVER_ERROR;
				}
			}
		}
		return new JSONResult(JSONCodeType.SUCCESS, "申请成功，等待管理员审核", null);
	}


//	取消申请
	@Override
	public JSONResult cancel(User user, int id) {
		// 权限验证
		if (user == null || user.getType() != UserType.NORMAL_USER) {
			return JSONResult.ACCESS_DENIED;
		}
		// 获取请假记录
		Leave leave = null;
		try {
			leave = leaveDao.selectLeaveById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if (leave == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "记录不存在", null);
		}
		// 如果不是自己的申请
		if (!leave.getUser().getId().equals(user.getId())) {
			System.out.println(leave.getUser().getId());
			System.out.println(user.getId());
			return JSONResult.ACCESS_DENIED;
		}
		// 只有待审核状态才能取消
		if (leave.getType() != LeaveType.WAIT) {
			return new JSONResult(JSONCodeType.ACCESS_DENIED, "记录非待审核状态", null);
		}

		Leave updateLeave = new Leave();
		updateLeave.setId(leave.getId());
		updateLeave.setType(LeaveType.CANCEL);
		try {
			leaveDao.updateLeaveById(updateLeave);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, "取消成功", null);
	}

	//自助销假

	@Override
	public JSONResult delete(User user, int id) throws Exception {
		// 权限验证
		if (user == null || user.getType() != UserType.NORMAL_USER) {
			return JSONResult.ACCESS_DENIED;
		}
		// 获取请假记录
		Leave leave = null;
		try {
			leave = leaveDao.selectLeaveById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if (leave == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "记录不存在", null);
		}
		// 如果不是自己的申请
		if (!leave.getUser().getId().equals(user.getId())) {
			System.out.println(leave.getUser().getId());
			System.out.println(user.getId());
			return JSONResult.ACCESS_DENIED;
		}
		// 请假成功则无法取消
		if (leave.getType() == LeaveType.PASS) {
			return new JSONResult(JSONCodeType.ACCESS_DENIED, "请假已成功，无法进行销假", null);
		}

		leaveDao.delete(id);
		return new JSONResult(JSONCodeType.SUCCESS, "销假成功", null);
	}



	//审核信息
	@Override
	public JSONResult info(User user, int id) {
		//未登录
		if(user == null) {
			return JSONResult.ACCESS_DENIED;
		}
		//查询
		Leave leave = null;
		try {
			leave = leaveDao.selectLeaveById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		//记录不存在
		if(leave == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "请假记录不存在", null);
		}
		//普通用户查看非自己的记录
		if(user.getType() == UserType.NORMAL_USER && !leave.getUser().getId().equals(user.getId())) {//请假用户的id和自己的id不一致
			return JSONResult.ACCESS_DENIED;
		}

		//如果是班级管理员
		if(user.getType() == UserType.CLAZZ_ADMIN) {
			//获取班级权限
			List<PermissionClazz> permissionClazzs = null;
			try {
				permissionClazzs = permissionClazzDao.selectPermissionClazzesByUserId(user.getId());
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			boolean access = false;
			for (PermissionClazz permissionClazz : permissionClazzs) {
				if(permissionClazz.getClazz().getId().equals(leave.getClazz().getId())&&(leave.getEndDate().getTime()-leave.getStartDate().getTime())/(24*60*60*1000)<3) {//请假记录小于三天
//					如果管理员的管理的班级id和请假着的班级的id一至，拥有审核权力
					access = true;
					break;
				}
			}

			//如果请假者所在班级不在管理的班级
			if(!access) {//如果为false
//				return JSONResult.ACCESS_DENIED;
				return new JSONResult(JSONCodeType.ACCESS_DENIED, "请假时间大于三天请等待院级管理员审核", null);
			}
		}

		if(user.getType() == UserType.COLLAGE_ADMIN) {
			//获取院级权限
			List<PermissionCollage> permissionCollages = null;
			try {
				permissionCollages = permissionCollageDao.selectPermissionCollagesByUserId(user.getId());//找到院级管理员
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			boolean access = false;
			for (PermissionCollage permissionCollage : permissionCollages) {
				if(permissionCollage.getCollage().getId().equals(leave.getClazz().getMajor().getCollage().getId())&&(leave.getEndDate().getTime()-leave.getStartDate().getTime())/(24*60*60*1000)>3) {//如果管理员的collage_id和请假着的班级-专业-学院-学院id一致，获得所有权
					access = true;
					break;
				}
			}
			//如果请假者所在班级不在管理的院级
			if(!access) {
				return JSONResult.ACCESS_DENIED;
			}
		}
		List<LeaveImage> leaveImages = null;
		try {
			leaveImages = leaveImageDao.selectLeaveImagesByLeaveId(leave.getId());
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("leave", leave);
		map.put("leaveImages", leaveImages);
		JSONResult result = new JSONResult(JSONCodeType.SUCCESS, "查询成功", map);
		return result;
	}

	@Override
	public JSONResult review(User user, int id, boolean access) {
		//TODO 权限验证
		Leave leave = new Leave(id, null, null, null, null, null, null, null, null, null, access ? LeaveType.PASS : LeaveType.NOT_PASS, null, null);
		try {
			leaveDao.updateLeaveById(leave);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, "审核成功", null);
	}


	@Override
	public JSONResult list(int page, User user, Integer clazzId, Integer userId, Date startDate, Date endDate, LeaveType type) throws Exception {
		// 如果用户未登录
		if (user == null) {
			return JSONResult.ACCESS_DENIED;
		}//权限不足
		List<Clazz> enableClazzes = clazzDao.selectClazzes();
		List<Clazz> clazzs = new ArrayList<Clazz>();
		Clazz fliterClazz = null;
		if (clazzId != null) {
			fliterClazz = clazzDao.selectClazzById(clazzId);
		}
		List<Leave> leaves = null;
		int leaveCount = 0;
		int limit = (page - 1) * LEAVE_COUNT_PER_PAGE;
		// 普通用户只能查询自己的记录，如果查询其他记录拒绝查询

		if (user.getType() == UserType.NORMAL_USER) {//普通用户
			if (fliterClazz != null) {
				clazzs.add(fliterClazz);
			}
			leaves = leaveDao.selectLeavesComplexLimit(clazzs, user.getId(), type, startDate, endDate, limit, LEAVE_COUNT_PER_PAGE);
			leaveCount = leaveDao.selectLeaveCountComplex(clazzs, user.getId(), type, startDate, endDate);
		}

		if(user.getType() == UserType.SUPER_ADMIN) {//超级管理员
			if (fliterClazz != null) {
				clazzs.add(fliterClazz);
			}
			leaves = leaveDao.selectLeavesComplexLimit(clazzs, userId, type, startDate, endDate, limit, LEAVE_COUNT_PER_PAGE);
			leaveCount = leaveDao.selectLeaveCountComplex(clazzs, userId, type, startDate, endDate);
		}
		if(user.getType() == UserType.COLLAGE_ADMIN) {//院级管理员
			List<PermissionCollage> permissionCollages = permissionCollageDao.selectPermissionCollagesByUserId(user.getId());//通过用户id找到管理的所有学院
			for (PermissionCollage permissionCollage : permissionCollages) {
				List<Major> majors = majorDao.selectMajorsByCollageId(permissionCollage.getCollage().getId());//找到所有专业
				for (Major major : majors) {
					List<Clazz> clazzes = clazzDao.selectClazzesByMajorId(major.getId());//找到一个专业的所有班级
					clazzs.addAll(clazzes);//传入list
				}
			}
		}

		if(user.getType() == UserType.COLLAGE_ADMIN || user.getType() == UserType.CLAZZ_ADMIN) {
			List<PermissionClazz> permissionClazzs = permissionClazzDao.selectPermissionClazzesByUserId(user.getId());
			for (PermissionClazz permissionClazz : permissionClazzs) {
				clazzs.add(permissionClazz.getClazz());
			}
		}

		if(leaves == null) {
			boolean find = true;
			if (fliterClazz != null) {
				find = false;
				for (Clazz clazz : clazzs) {
					if (clazz.getId().equals(fliterClazz.getId())) {
						clazzs.clear();
						clazzs.add(clazz);
						find = true;
						break;
					}
				}
			}
			if (find) {
				leaves = leaveDao.selectLeavesComplexLimit(clazzs, userId, type, startDate, endDate, limit, LEAVE_COUNT_PER_PAGE);//通请假时间超过3天
				leaveCount = leaveDao.selectLeaveCountComplex(clazzs, userId, type, startDate, endDate);
			} else {
				leaves = new ArrayList<>();
				leaveCount = 0;
			}

		}
		int pageCount = leaveCount % LEAVE_COUNT_PER_PAGE == 0 ? leaveCount / LEAVE_COUNT_PER_PAGE : leaveCount / LEAVE_COUNT_PER_PAGE + 1;
		int start = page - 5 > 1 ? page - 5 : 1;
		int end = page + 5 > pageCount ? pageCount : page + 5;
		List<Integer> pageList = new ArrayList<Integer>();
		for (int i = start; i <= end; i++) {
			pageList.add(i);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("leaves", leaves);
		map.put("pageList", pageList);
		map.put("pageCount", pageCount);
		map.put("enableClazzes", enableClazzes);
		JSONResult result = new JSONResult(JSONCodeType.SUCCESS, "查询成功", map);
		return result;
	}

	@Override
	public JSONResult list(User user) throws Exception {
		// 如果用户未登录
		if (user == null) {
			return JSONResult.ACCESS_DENIED;
		}//权限不足

		List<Leave> leaves = null;
		// 普通用户只能查询自己的记录，如果查询其他记录拒绝查询

		if (user.getType() == UserType.NORMAL_USER) {//普通用户
			leaves = leaveDao.selectLeavesComplex(null, user.getId(), null);
		}

		if(user.getType() == UserType.SUPER_ADMIN) {//超级管理员
			leaves = leaveDao.selectLeaves();//请假时间超过1个月
		}

		List<Clazz> clazzs = new ArrayList<Clazz>();

		if(user.getType() == UserType.COLLAGE_ADMIN) {//院级管理员
			List<PermissionCollage> permissionCollages = permissionCollageDao.selectPermissionCollagesByUserId(user.getId());//通过用户id找到管理的所有学院
			for (PermissionCollage permissionCollage : permissionCollages) {
				List<Major> majors = majorDao.selectMajorsByCollageId(permissionCollage.getCollage().getId());//找到所有专业
				for (Major major : majors) {
					List<Clazz> clazzes = clazzDao.selectClazzesByMajorId(major.getId());//找到一个专业的所有班级
					clazzs.addAll(clazzes);//传入list
				}
			}
		}

		if(user.getType() == UserType.COLLAGE_ADMIN || user.getType() == UserType.CLAZZ_ADMIN) {
			List<PermissionClazz> permissionClazzs = permissionClazzDao.selectPermissionClazzesByUserId(user.getId());
			for (PermissionClazz permissionClazz : permissionClazzs) {
				clazzs.add(permissionClazz.getClazz());
			}
		}

		if(leaves == null) {
			leaves = leaveDao.selectLeavesComplex(clazzs, null, null);//通请假时间超过3天
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("leaves", leaves);
		JSONResult result = new JSONResult(JSONCodeType.SUCCESS, "查询成功", map);
		return result;
	}
}
