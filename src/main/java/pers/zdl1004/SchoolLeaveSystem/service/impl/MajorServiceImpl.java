package pers.zdl1004.SchoolLeaveSystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.zdl1004.SchoolLeaveSystem.dao.CollageDao;
import pers.zdl1004.SchoolLeaveSystem.dao.MajorDao;
import pers.zdl1004.SchoolLeaveSystem.dao.PermissionCollageDao;
import pers.zdl1004.SchoolLeaveSystem.pojo.Collage;
import pers.zdl1004.SchoolLeaveSystem.pojo.Major;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionCollage;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.MajorService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

@Service
public class MajorServiceImpl implements MajorService {

	@Autowired
	private MajorDao majorDao;
	@Autowired
	private CollageDao collageDao;
	@Autowired
	private PermissionCollageDao permissionCollageDao;

	private Logger logger = LogManager.getLogger(MajorServiceImpl.class);

	@Override
	public JSONResult list(User user) {
		if (user == null || user.getType().getCode() > UserType.COLLAGE_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		// 每个学院和专业的映射
		Map<Collage, List<Major>> map = new HashMap<Collage, List<Major>>();
		// 记录用户管理的学院
		List<Collage> collages = null;
		if (user.getType() == UserType.SUPER_ADMIN) {
			// 是超级管理员，选取所有的学院
			try {
				collages = collageDao.selectCollages();
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
		}
		if (user.getType() == UserType.COLLAGE_ADMIN) {
			// 院级管理员 根据权限选取学院
			collages = new ArrayList<Collage>();
			List<PermissionCollage> permissionCollages = null;
			try {
				permissionCollages = permissionCollageDao.selectPermissionCollagesByUserId(user.getId());
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			for (PermissionCollage permissionCollage : permissionCollages) {
				// 将所有拥有权限的学院添加到list里
				collages.add(permissionCollage.getCollage());
			}
		}
		for (Collage collage : collages) {
			// 对于每个学院查询所拥有的专业，形成映射
			List<Major> majors = null;
			try {
				majors = majorDao.selectMajorsByCollageId(collage.getId());
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			map.put(collage, majors);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("map", map);
		data.put("collages", collages);
		return new JSONResult(JSONCodeType.SUCCESS, null, data);
	}

	@Override
	public JSONResult add(Integer collageId, String name, User user) {
		if (user == null || user.getType().getCode() > UserType.COLLAGE_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		if (collageId == null || name == null) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, null, null);
		}
		if (user.getType() == UserType.COLLAGE_ADMIN) {
			// 验证权限
			PermissionCollage permissionCollage = null;
			try {
				permissionCollage = permissionCollageDao.selectPermissionCollageByUserIdAndCollageId(user.getId(),
						collageId);
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			if (permissionCollage == null) {
				return JSONResult.ACCESS_DENIED;
			}
		}
		Major major = new Major(null, name, new Collage(collageId, null));
		try {
			majorDao.insertMajor(major);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, null, null);
	}

	@Override
	public JSONResult change(Integer id, String changeName, Integer changeCollageId, User user) {
		if (user == null || user.getType().getCode() > UserType.COLLAGE_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		if (id == null || changeName == null) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, null, null);
		}
		// 验证专业存在
		Major major = null;
		try {
			major = majorDao.selectMajorById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if (major == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "专业不存在", null);
		}
		// 验证学院存在
		Collage collage = null;
		try {
			collage = collageDao.selectCollageById(changeCollageId);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if (collage == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "学院不存在", null);
		}
		if (user.getType() == UserType.COLLAGE_ADMIN) {
			// 验证权限
			Integer collageId = major.getCollage().getId();
			PermissionCollage permissionCollage = null;
			try {
				permissionCollage = permissionCollageDao.selectPermissionCollageByUserIdAndCollageId(user.getId(),
						collageId);
			} catch (Exception e) {
				logger.warn(e);
				return JSONResult.SERVER_ERROR;
			}
			if (permissionCollage == null) {
				return JSONResult.ACCESS_DENIED;
			}
		}
		major.setName(changeName);
		major.setCollage(new Collage(changeCollageId, null));
		try {
			majorDao.updateMajorById(major);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, null, null);
	}

	@Override
	public JSONResult delete(Integer id, User user) {
		if (user == null || user.getType().getCode() > UserType.COLLAGE_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		if (id == null) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, null, null);
		}
		// 验证专业存在
		Major major = null;
		try {
			major = majorDao.selectMajorById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if (major == null) {
			return new JSONResult(JSONCodeType.DATA_NOT_FOUND, "专业不存在", null);
		}
		try {
			majorDao.deleteMajorById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, null, null);
	}
}
