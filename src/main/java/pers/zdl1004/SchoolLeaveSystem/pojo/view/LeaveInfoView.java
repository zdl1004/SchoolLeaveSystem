package pers.zdl1004.SchoolLeaveSystem.pojo.view;

import pers.zdl1004.SchoolLeaveSystem.type.LeaveType;

import java.text.DateFormat;
import java.util.List;
import pers.zdl1004.SchoolLeaveSystem.util.DateUtil;
import pers.zdl1004.SchoolLeaveSystem.pojo.Leave;
import pers.zdl1004.SchoolLeaveSystem.pojo.LeaveImage;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;

public class LeaveInfoView {
	private Integer id;//序号
	private User user;//用户
	private String clazzFullName;//班级全名
	private String telephone;//电话
	private String leaveTime;//请假时间
	private String reason;//原因
	private List<LeaveImage> images;//照片
	private LeaveType type;//审核类型
	private String createTime;//创建时间
	
	public LeaveInfoView() {}

	public LeaveInfoView(Integer id, User user, String clazzFullName, String telephone, String leaveTime, String reason,
			List<LeaveImage> images, LeaveType type, String createTime) {
		super();
		this.id = id;
		this.user = user;
		this.clazzFullName = clazzFullName;
		this.telephone = telephone;
		this.leaveTime = leaveTime;
		this.reason = reason;
		this.images = images;
		this.type = type;
		this.createTime = createTime;
	}

	public LeaveInfoView(Leave leave, List<LeaveImage> images) {
		this.id = leave.getId();
		this.user = leave.getUser();
		this.telephone = leave.getTelephone();
		this.clazzFullName = leave.getClazz().getFullName();
		DateFormat format = DateUtil.getDateFormatter();
		this.leaveTime = format.format(leave.getStartDate()) + "第" + leave.getStartLesson() + "节课 至 "
				+ format.format(leave.getEndDate()) + "第" + leave.getEndLesson() + "节课";
		this.createTime = format.format(leave.getCreateTime());
		this.type = leave.getType();
		this.reason = leave.getReason();
		this.images = images;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getClazzFullName() {
		return clazzFullName;
	}

	public void setClazzFullName(String clazzFullName) {
		this.clazzFullName = clazzFullName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}

	public List<LeaveImage> getImages() {
		return images;
	}

	public void setImages(List<LeaveImage> images) {
		this.images = images;
	}

}
