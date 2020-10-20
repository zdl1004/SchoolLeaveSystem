package pers.zdl1004.SchoolLeaveSystem.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.type.LeaveType;

import java.util.Date;

public interface LeaveService {
	/**
	 * 创建请假申请
	 * 
	 * @param user        申请请假的用户，保证不为null
	 * @param startYear   请假开始年份
	 * @param startMonth  请假开始月份
	 * @param startDay    请假开始日期
	 * @param startLesson 请假开始第x节课
	 * @param endYear     请假结束年份
	 * @param endMonth    请假结束月份
	 * @param endDay      请假结束日期
	 * @param endLesson   请假结束第y节课
	 * @param reason      请假原因
	 * @param images      请假原因附件图片，可能为null
	 * @param systemPath  项目路径
	 * @return ACCESS_DENIED 用户不为普通用户
	 * 
	 */
	public JSONResult create(User user, int startYear, int startMonth, int startDay, int startLesson, int endYear, int endMonth, int endDay, int endLesson, String reason, CommonsMultipartFile[] images, String systemPath);

	/**
	 * 取消请假申请
	 * 
	 * @param user 发起请求的用户
	 * @param id   申请取消的请假id
	 * @return
	 */
	public JSONResult cancel(User user, int id);

	public JSONResult list(int page, User user, Integer clazzId, Integer userId, Date startDate, Date endDate, LeaveType type) throws Exception;

	public JSONResult list(User user) throws Exception;

	/**
	 * 请假信息查询
	 * 
	 * 
	 */
	public JSONResult info(User user, int id);

	public JSONResult review(User user, int id, boolean access);

    public JSONResult delete(User user, int id) throws Exception;
}
