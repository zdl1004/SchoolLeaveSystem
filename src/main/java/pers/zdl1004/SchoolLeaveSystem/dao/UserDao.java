package pers.zdl1004.SchoolLeaveSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

public interface UserDao {
	@Select("select * from user where username = #{username}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
	})
	User selectUserByUsername(String username) throws Exception;

//通过id查询 所有用户,超级管理员层面
	@Select("select * from user where id = #{id}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
	})
	User selectUserById(int id) throws Exception;



	@Select("select * from user order by id desc limit #{limit}, #{count}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
	})
	List<User> selectUsersLimit(@Param("limit") int limit, @Param("count") int count) throws Exception;

	@Select("select count(*) from user")
	int selectUserCount() throws Exception;

	@Select("select * from user order by id asc")
	@Results({
			@Result(column = "id", property = "id", id = true),
			@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
	})
	List<User> selectUsers() throws Exception;



	@Select("select * from user where clazz_id = #{clazzId}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "clazz_id", property = "clazz", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.ClazzDao.selectClazzById")),
	})
	List<User> selectUsersByClazzId(int clazzId) throws Exception;

	@Insert("insert into user(username, password, type, name, telephone, clazz_id) values(#{username}, #{password}, #{type}, #{name}, #{telephone}, #{clazz.id})")
	//用于在语句执行完毕后返回新插入的主键
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertUser(User user) throws Exception;


	@UpdateProvider(type = UserDaoProvider.class, method = "updateUserById")
	//默认返回受影响的行数
	int updateUserById(User user) throws Exception;


	@Update("update user set clazz_id = #{clazz_id} where id = #{id}")
	int updateUserSetClazzById(@Param("id") int id, @Param("clazz_id") Integer clazzId) throws Exception;


	class UserDaoProvider {
		public String updateUserById(User user) {
			return new SQL() {{
					UPDATE("user");
					if(user.getUsername() != null) {
						SET("username = #{username}");
					}
					if(user.getPassword() != null) {
						SET("password = #{password}");
					}
					if(user.getName() != null) {
						SET("name = #{name}");
					}
					if(user.getTelephone() != null) {
						SET("telephone = #{telephone}");
					}
					if(user.getType() != null) {
						SET("type = #{type}");
					}
					if(user.getClazz() != null) {
						SET("clazz_id = #{clazz.id}");
					}
					if(user.getClientId() != null) {
						SET("client_id = #{clientId}");
					}
					if(user.getClientToken() != null) {
						SET("client_token = #{clientToken}");
					}

					WHERE("id = #{id}");
			}}.toString();
		}
	}
}