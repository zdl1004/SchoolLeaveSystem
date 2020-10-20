package pers.zdl1004.SchoolLeaveSystem.service;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;

public interface MajorService {
	public JSONResult list(User user);
	
	public JSONResult add(Integer collageId, String name, User user);
	
	public JSONResult change(Integer id, String changeName, Integer changeCollageId, User user);
	
	public JSONResult delete(Integer id, User user);
}
