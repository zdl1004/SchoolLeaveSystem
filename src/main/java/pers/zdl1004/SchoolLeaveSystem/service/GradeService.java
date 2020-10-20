package pers.zdl1004.SchoolLeaveSystem.service;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;

public interface GradeService {
	public JSONResult list(User user);
	
	public JSONResult add(Integer grade, User user);
	
	public JSONResult delete(Integer id, User user);
}
