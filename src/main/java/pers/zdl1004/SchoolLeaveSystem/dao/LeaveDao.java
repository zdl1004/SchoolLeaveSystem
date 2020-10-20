package pers.zdl1004.SchoolLeaveSystem.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import pers.zdl1004.SchoolLeaveSystem.pojo.Clazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.Leave;
import pers.zdl1004.SchoolLeaveSystem.type.LeaveType;
//定义leavedao接口
public interface LeaveDao {
	@Insert("insert into `leave`(user_id, clazz_id, telephone, start_date, start_lesson, "
			+ "end_date, end_lesson, reason, create_time, type) values(#{user.id}, #{clazz.id}, "
			+ "#{user.telephone}, #{startDate}, #{startLesson}, #{endDate}, #{endLesson}, #{reason}, now(), #{type})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertLeave(Leave leave) throws Exception;

	@Select("select * from `leave` where id = #{id}")
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public Leave selectLeaveById(int id) throws Exception;

	@Select("delete from `leave` where id = #{id}")
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public void delete(int id) throws Exception;

	@UpdateProvider(type = LeaveDaoProvider.class, method = "updateLeaveById")
	public int updateLeaveById(Leave leave) throws Exception;

	@SelectProvider(type = LeaveDaoProvider.class, method = "selectLeaveByLeave")
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public List<Leave> selectLeaveByLeave(Leave leave) throws Exception;


	@SelectProvider(type = LeaveDaoProvider.class)
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public List<Leave> selectLeavesComplexLimit(@Param("orClazzes") List<Clazz> orClazzes, @Param("andUserId") Integer andUserId, @Param("andType") LeaveType andType, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("limit") int limit, @Param("count") int count) throws Exception;

	@SelectProvider(type = LeaveDaoProvider.class)
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public List<Leave> selectLeavesComplex(@Param("orClazzes") List<Clazz> orClazzes, @Param("andUserId") Integer andUserId, @Param("andType") LeaveType andType) throws Exception;
//普通用户查询自己的信息
	@SelectProvider(type = LeaveDaoProvider.class)
	public int selectLeaveCountComplex(@Param("orClazzes") List<Clazz> orClazzes, @Param("andUserId") Integer andUserId, @Param("andType") LeaveType andType, @Param("startDate") Date startDate, @Param("endDate") Date endDate) throws Exception;


	@Select("select * from `leave` order by id desc limit #{limit}, #{count}")
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public List<Leave> selectLeavesLimit(@Param("limit") int limit, @Param("count") int count) throws Exception;

	@Select("select * from `leave` order by id asc")
	@Results({ @Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
			@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
	public List<Leave> selectLeaves() throws Exception;

	@Select("select count(*) from `leave`")
	public int selectLeaveCount() throws Exception;

//	查询日期大于一个月
@Select("select * from `leave` where TimeStampDiff(DAY,start_date=#{startDate},end_date=#{endDate})>15")
@Results({ @Result(column = "id", property = "id", id = true),
		@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
		@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
		@Result(column = "reviewer_id", property = "reviewer", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")) })
public List<Leave> selectLeavesBeyondMonth() throws Exception;


//更新请假记录方法
	class LeaveDaoProvider implements ProviderMethodResolver {
		public String updateLeaveById(Leave leave) {
			return new SQL() {
				{
					UPDATE("`leave`");
					if (leave.getUser() != null) {
						SET("user_id = #{user.id}");
					}
					if (leave.getClazz() != null) {
						SET("clazz_id = #{clazz.id}");
					}
					if (leave.getTelephone() != null) {
						SET("telephone = #{telephone}");
					}
					if (leave.getStartDate() != null) {
						SET("start_date = #{startDate}");
					}
					if (leave.getStartLesson() != null) {
						SET("start_lesson = #{startLesson}");
					}
					if (leave.getEndDate() != null) {
						SET("end_date = #{endDate}");
					}
					if (leave.getEndLesson() != null) {
						SET("end_lesson = #{endLesson}");
					}
					if (leave.getReason() != null) {
						SET("reason = #{reason}");
					}
					if (leave.getCreateTime() != null) {
						SET("create_time = #{createTime}");
					}
					if (leave.getType() != null) {
						SET("type = #{type}");
					}
					if (leave.getReviewer() != null) {
						SET("reviewer_id = #{reviewer.id}");
					}
					if (leave.getReviewTime() != null) {
						SET("review_time = #{reviewTime}");
					}
					WHERE("id = #{id}");
				}
			}.toString();
		}

//		根据leave条件查询
		public String selectLeaveByLeave(Leave leave) {
			return new SQL() {
				{
					SELECT("*");
					FROM("`leave`");
					if (leave.getId() != null) {
						WHERE("id = #{id}");
					}
					if (leave.getUser() != null) {
						WHERE("user_id = #{user.id}");
					}
					if (leave.getClazz() != null) {
						WHERE("clazz_id = #{clazz.id}");
					}
					if (leave.getTelephone() != null) {
						WHERE("telephone = #{telephone}");
					}
					if (leave.getStartDate() != null) {
						WHERE("start_date = #{startDate}");
					}
					if (leave.getStartLesson() != null) {
						WHERE("start_lesson = #{startLesson}");
					}
					if (leave.getEndDate() != null) {
						WHERE("end_date = #{endDate}");
					}
					if (leave.getEndLesson() != null) {
						WHERE("end_lesson = #{endLesson}");
					}
					if (leave.getReason() != null) {
						WHERE("reason = #{reason}");
					}
					if (leave.getCreateTime() != null) {
						WHERE("create_time = #{createTime}");
					}
					if (leave.getType() != null) {
						WHERE("type = #{type}");
					}
					if (leave.getReviewer() != null) {
						WHERE("reviewer_id = #{reviewer.id}");
					}
					if (leave.getReviewTime() != null) {
						WHERE("review_time = #{reviewTime}");
					}
				}
			}.toString();
		}

		public String selectLeavesComplexLimit(@Param("orClazzes") List<Clazz> orClazzes, @Param("andUserId") Integer andUserId, @Param("andType") LeaveType andType, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("limit") int limit, @Param("count") int count) {
			return new SQL() {
				{
					SELECT("*");
					FROM("`leave`");
					if (orClazzes != null && !orClazzes.isEmpty()) {
						for (int i = 0; i < orClazzes.size(); i++) {
							OR();
							WHERE("clazz_id = #{orClazzes[" + i + "].id}");//根据班级id查询记录
						}
					}
					if (andUserId != null) {
						AND();
						WHERE("user_id = #{andUserId}");//用户id,查询记录
					}
					if (andType != null) {
						AND();
						WHERE("type = #{andType}");//根据类型,审核,未审核查询记录
					}
					if (startDate != null) {
						AND();
						WHERE("start_date >= #{startDate}");
					}
					if (endDate != null) {
						AND();
						WHERE("end_date <= #{endDate}");
					}
					ORDER_BY("id DESC");
					OFFSET(limit);
					LIMIT(count);
				}
			}.toString();
		}

	public String selectLeavesComplex(@Param("orClazzes") List<Clazz> orClazzes, @Param("andUserId") Integer andUserId, @Param("andType") LeaveType andType) {
		return new SQL() {
			{
				SELECT("*");
				FROM("`leave`");
				if (orClazzes != null && !orClazzes.isEmpty()) {
					for (int i = 0; i < orClazzes.size(); i++) {
						OR();
						WHERE("clazz_id = #{orClazzes[" + i + "].id}");//根据班级id查询记录
					}
				}
				if (andUserId != null) {
					AND();
					WHERE("user_id = #{andUserId}");//用户id,查询记录
				}
				if (andType != null) {
					AND();
					WHERE("type = #{andType}");//根据类型,审核,未审核查询记录
				}
				ORDER_BY("id ASC");
			}
		}.toString();
	}

	public String selectLeaveCountComplex(@Param("orClazzes") List<Clazz> orClazzes, @Param("andUserId") Integer andUserId, @Param("andType") LeaveType andType, @Param("startDate") Date startDate, @Param("endDate") Date endDate) {
		return new SQL() {
			{
				SELECT("COUNT(*)");
				FROM("`leave`");
				if (orClazzes != null && !orClazzes.isEmpty()) {
					for (int i = 0; i < orClazzes.size(); i++) {
						OR();
						WHERE("clazz_id = #{orClazzes[" + i + "].id}");//根据班级id查询记录
					}
				}
				if (andUserId != null) {
					AND();
					WHERE("user_id = #{andUserId}");//用户id,查询记录
				}
				if (andType != null) {
					AND();
					WHERE("type = #{andType}");//根据类型,审核,未审核查询记录
				}
				if (startDate != null) {
					AND();
					WHERE("start_date >= #{startDate}");
				}
				if (endDate != null) {
					AND();
					WHERE("end_date <= #{endDate}");
				}
			}
		}.toString();
	}
	}
}
