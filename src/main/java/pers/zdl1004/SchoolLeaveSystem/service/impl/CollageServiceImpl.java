package pers.zdl1004.SchoolLeaveSystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.zdl1004.SchoolLeaveSystem.dao.CollageDao;
import pers.zdl1004.SchoolLeaveSystem.dao.PermissionCollageDao;
import pers.zdl1004.SchoolLeaveSystem.dao.UserDao;
import pers.zdl1004.SchoolLeaveSystem.pojo.*;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.CollageService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

@Service
public class CollageServiceImpl implements CollageService {
	
	@Autowired
	private CollageDao collageDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PermissionCollageDao permissionCollageDao;
	private Logger logger = LogManager.getLogger(CollageServiceImpl.class);

	@Override
	public JSONResult list(User user) {
		if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		List<Collage> collages = null;
		try {
			collages = collageDao.selectCollages();
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collages", collages);
		return new JSONResult(JSONCodeType.SUCCESS, null, map);
	}



	@Override
	public JSONResult add(String name, User user) {//
		if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}

		try {
			collageDao.insertCollage(name);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, "添加成功", null);
	}





	@Override
	public JSONResult addManager(String username,String name,User user) throws Exception {//传入用户名,还有学院名称
		if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}

		Collage collage = collageDao.selectCollageByName(name);//找到学院
		if (collage == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "学院不存在", null);
		}
		User willGiveUser = userDao.selectUserByUsername(username);//获取到输入的用户

		if (willGiveUser== null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "用户不存在", null);
		}

		PermissionCollage permissionCollage = permissionCollageDao.selectPermissionCollageByCollageId(collage.getId());//找到学院管理员
		if(permissionCollage !=null){
			User user5 = userDao.selectUserById(permissionCollage.getUser().getId());//找到以前的院级管理员
			if(user5==null){
				return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "用户不存在", null);
			}
			UserType type = user5.getType();//获取管理员用户的用户类型
			type=UserType.NORMAL_USER;

			UserType newtype = user5.getType();//获取院级管理权的院级管理员权限
			PermissionCollage permissionCollage1 = new PermissionCollage(permissionCollage.getId(),willGiveUser,collage);
			permissionCollageDao.updatePermissionList(permissionCollage1);//更新学院管理员表

			User typechangeUser = new User(willGiveUser.getId(), null, null,newtype,   null, null, null, null, null);
			userDao.updateUserById(typechangeUser);

			User user6 = new User(user5.getId(),null, null,UserType.NORMAL_USER,   null, null, null, null, null);
			userDao.updateUserById(user6);//原有管理员账户降级

		}

		if(permissionCollage == null){
			User user4 = new User(willGiveUser.getId(),null, null,UserType.COLLAGE_ADMIN,   null, null, null, null, null);//赋予院级管理员权限
			userDao.updateUserById(user4);//赋予班级管理员信息更迭,正确
			PermissionCollage permissionCollage3 = new PermissionCollage(null,willGiveUser,collage);
			permissionCollageDao.insertPermisssionCollage(permissionCollage3);//插入班级新管理员记录
		}

		return new JSONResult(JSONCodeType.SUCCESS, "", null);
	}






	@Override
	public JSONResult change(Integer id, String name, User user) {
		if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		Collage collage = null;
		try {
			collage = collageDao.selectCollageById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if(collage == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "学院不存在", null);
		}
		collage.setName(name);
		try {
			collageDao.updateCollageById(collage);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, null, null);
	}
	
	@Override
	public JSONResult delete(Integer id, User user) {
		if(user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		Collage collage = null;
		try {
			collage = collageDao.selectCollageById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if(collage == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "学院不存在", null);
		}
		try {
			collageDao.deleteCollageById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, null, null);
	}
}
