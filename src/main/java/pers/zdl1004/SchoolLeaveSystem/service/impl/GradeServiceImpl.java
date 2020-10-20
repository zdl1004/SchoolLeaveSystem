package pers.zdl1004.SchoolLeaveSystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.zdl1004.SchoolLeaveSystem.dao.GradeDao;
import pers.zdl1004.SchoolLeaveSystem.pojo.Grade;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.GradeService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeDao gradeDao;

	private Logger logger = LogManager.getLogger(GradeServiceImpl.class);

	@Override
	public JSONResult list(User user) {
		// 如果未登录 或 权限低于超级管理员
		if (user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		List<Grade> grades = null;
		try {
			grades = gradeDao.selectGrades();
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grades", grades);
		return new JSONResult(JSONCodeType.SUCCESS, null, map);
	}

	@Override
	public JSONResult add(Integer year, User user) {
		// 如果未登录 或 权限低于超级管理员
		if (user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		if (year == null || year < 1000 || year > 10000) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "参数错误", null);
		}
		Grade grade = null;
		try {
			grade = gradeDao.selectGradeByYear(year);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if(grade != null) {
			//年级已存在
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "年级已存在", null);
		}
		try {
			gradeDao.insertGrade(year);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, "添加成功", null);
	}

	@Override
	public JSONResult delete(Integer id, User user) {
		// 如果未登录 或 权限低于超级管理员
		if (user == null || user.getType().getCode() > UserType.SUPER_ADMIN.getCode()) {
			return JSONResult.ACCESS_DENIED;
		}
		if(id == null) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "参数错误", null);
		}
		Grade grade = null;
		try {
			grade = gradeDao.selectGradeById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		if(grade == null) {
			return new JSONResult(JSONCodeType.INVALID_PARAMS, "所选年级不存在", null);
		}
		try {
			gradeDao.deleteGradeById(id);
		} catch (Exception e) {
			logger.warn(e);
			return JSONResult.SERVER_ERROR;
		}
		return new JSONResult(JSONCodeType.SUCCESS, "删除成功", null);
	}
}
