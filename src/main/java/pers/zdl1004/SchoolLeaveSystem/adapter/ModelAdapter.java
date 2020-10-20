package pers.zdl1004.SchoolLeaveSystem.adapter;

import java.util.List;
import org.springframework.ui.Model;

import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;

import pers.zdl1004.SchoolLeaveSystem.pojo.view.LeaveListView;
import pers.zdl1004.SchoolLeaveSystem.pojo.view.UserInfoView;

public class ModelAdapter {
	private Model model;
	public ModelAdapter(Model model) {
		this.model = model;
	}
	public void setUserInfoView(UserInfoView view) {
		model.addAttribute("userInfoView", view);
	}
	public void setErrorResult(JSONResult result) {
		model.addAttribute("result", result);
	}
	
	public void setLeaveList(List<LeaveListView> leaveListViews) {
		model.addAttribute("leaveListViews", leaveListViews);
	}
}
