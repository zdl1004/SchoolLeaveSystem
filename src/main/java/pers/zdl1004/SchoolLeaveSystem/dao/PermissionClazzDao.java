package pers.zdl1004.SchoolLeaveSystem.dao;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.ibatis.annotations.*;

import org.apache.ibatis.jdbc.SQL;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionClazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;

public interface PermissionClazzDao {


	@Select("select * from permission_clazz where user_id = #{user_id} and clazz_id = #{clazz_id}")
	@Results({
			@Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById"))
	})
//	通过用户id和班级id查找班级管理员
	public PermissionClazz selectPermissionClazzByUserIdAndClazzId(@Param("user_id") int userId, @Param("clazz_id") int clazzId) throws Exception;

	@Select("select * from permission_clazz where clazz_id = #{clazz_id}")
	@Results({
			@Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById"))
	})
	public PermissionClazz selectPermissionClazzByClazzId(@Param("clazz_id") int clazzId) throws Exception;


	@Select("select * from permission_clazz where user_id = #{user_id}")
	@Results({
			@Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById"))
	})
//	通过用户ID查找管理的班级
	public List<PermissionClazz> selectPermissionClazzesByUserId(int userId) throws Exception;


	//	插入班级管理员
	@Insert("insert into permission_clazz(user_id,clazz_id) values(#{user.id},  #{clazz.id})")
	//用于在语句执行完毕后返回新插入的主键
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertPermisssionClazz(PermissionClazz permissionClazz) throws Exception;

//	@Delete("delete from permission_clazz where id = #{id}")
//	public void deletePermissionClazzById(Integer id) throws Exception;

	@UpdateProvider(type = PermissionClazzDaoProvider.class, method = "updatePermissionList")
	int updatePermissionList(PermissionClazz permissionClazz) throws Exception;

	//	删除学院管理员
	@Delete("delete from permission_clazz where id = #{id}")
	int deletePermissionCollageById(Integer id) throws Exception;

	class PermissionClazzDaoProvider {
		public String updatePermissionList(PermissionClazz permissionClazz) {
			return new SQL() {{
				UPDATE("permission_clazz");
//				if(permissionClazz.getId()!=null){
//					SET("id = #{id}");
//				}
				if (permissionClazz.getUser()!= null&&permissionClazz.getUser().getId()!= null) {
					SET("user_id = #{user.id}");
				}
				if (permissionClazz.getClazz()!= null&&permissionClazz.getClazz().getId() != null) {
					SET("clazz_id = #{clazz.id}");
				}
				WHERE("id = #{id}");
			}}.toString();
		}
	}

}

