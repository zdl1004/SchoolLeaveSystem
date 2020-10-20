package pers.zdl1004.SchoolLeaveSystem.service;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

public interface CollageService {
	public JSONResult list(User user);

//	public JSONResult info(Integer id, User user);
	
	public JSONResult add(String name, User user);

	public JSONResult addManager( String username,String name,  User user) throws Exception;//添加管理员
	
	public JSONResult change(Integer id, String name, User user);
	
	public JSONResult delete(Integer id, User user);
}
