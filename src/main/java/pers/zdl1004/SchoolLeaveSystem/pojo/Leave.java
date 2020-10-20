package pers.zdl1004.SchoolLeaveSystem.pojo;

import java.util.Date;

import pers.zdl1004.SchoolLeaveSystem.type.LeaveType;

public class Leave {
	private Integer id;
	private User user;
	private Clazz clazz;
	private String telephone;
	private Date startDate;
	private Integer startLesson;
	private Date endDate;
	private Integer endLesson;
	private String reason;
	private Date createTime;
	private LeaveType type;
	private User reviewer;
	private Date reviewTime;

	public Leave() {
	}

	public Leave(Integer id, User user, Clazz clazz, String telephone, Date startDate, Integer startLesson, Date endDate,
			Integer endLesson, String reason, Date createTime,LeaveType type, User reviewer, Date reviewTime) {
		this.id = id;
		this.user = user;
		this.clazz = clazz;
		this.telephone = telephone;
		this.startDate = startDate;
		this.startLesson = startLesson;
		this.endDate = endDate;
		this.endLesson = endLesson;
		this.reason = reason;
		this.createTime = createTime;
		this.type = type;
		this.reviewer = reviewer;
		this.reviewTime = reviewTime;
	}

	@Override
	public String toString() {
		return "Leave [id=" + id + ", user=" + user + ", clazz=" + clazz + ", telephone=" + telephone + ", startDate="
				+ startDate + ", startLesson=" + startLesson + ", endDate=" + endDate + ", endLesson=" + endLesson
				+ ", reason=" + reason + ", createTime=" + createTime + ", type=" + type + ", reviewer=" + reviewer
				+ ", reviewTime=" + reviewTime + "]";
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

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getStartLesson() {
		return startLesson;
	}

	public void setStartLesson(Integer startLesson) {
		this.startLesson = startLesson;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEndLesson() {
		return endLesson;
	}

	public void setEndLesson(Integer endLesson) {
		this.endLesson = endLesson;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
}
