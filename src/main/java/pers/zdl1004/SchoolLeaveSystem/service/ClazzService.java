package pers.zdl1004.SchoolLeaveSystem.service;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

public interface ClazzService {
	//用户列表
	public JSONResult list(User user) throws Exception;

//	用户信息
	public JSONResult info(Integer id, User user);

//	添加用户
	public JSONResult add(Integer no, Integer gradeId, Integer majorId, User user) throws Exception;

//	用户信息更改
	public JSONResult change(Integer id, Integer no, Integer gradeId, Integer majorId, User user) throws Exception;

//	删除用户
	public JSONResult delete(Integer id, User user) throws Exception;

//	添加用户
	public JSONResult addUser(Integer id, String username, User user) throws Exception;

//	移除用户
	public JSONResult removeUser(Integer id, Integer userId, User user) throws Exception;
//给与权限
	public JSONResult giveUser(Integer id, String username, User user) throws Exception;
	//班级权限转让
	public JSONResult changeUser(Integer id, String username,  User user) throws Exception;

}
