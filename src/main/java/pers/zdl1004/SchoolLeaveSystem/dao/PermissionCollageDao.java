package pers.zdl1004.SchoolLeaveSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import org.apache.ibatis.jdbc.SQL;
import pers.zdl1004.SchoolLeaveSystem.pojo.Collage;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionClazz;
import pers.zdl1004.SchoolLeaveSystem.pojo.PermissionCollage;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;

public interface PermissionCollageDao {
	@Select("select * from permission_collage where user_id = #{user_id}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
		@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
	})
	public List<PermissionCollage> selectPermissionCollagesByUserId(int userId) throws Exception;
	
	@Select("select * from permission_collage where user_id = #{user_id} and collage_id = #{collage_id}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
		@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
	})
	public PermissionCollage selectPermissionCollageByUserIdAndCollageId(@Param("user_id") int userId, @Param("collage_id") int collageId) throws Exception;


//	查询所有管理员
@Select("select * from permission_collage")
@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
		@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
})
List<User> selectPermissionCollages() throws Exception;

	@Select("select * from permission_collage where collage_id = #{collage_id}")//通过学院名查找学院管理员
	@Results({
			@Result(column = "id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.UserDao.selectUserById")),
			@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
	})
	public PermissionCollage selectPermissionCollageByCollageId(@Param("collage_id") int collageId) throws Exception;


	//	插入学院管理员
	@Insert("insert into permission_collage(user_id,collage_id) values(#{user.id},  #{collage.id})")
	//用于在语句执行完毕后返回新插入的主键
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertPermisssionCollage(PermissionCollage permissionCollage) throws Exception;


//	删除学院管理员
@Delete("delete from permission_collage where id = #{id}")
int deletePermissionCollageById(Integer id) throws Exception;



	@UpdateProvider(type = PermissionCollageDao.PermissionCollageDaoProvider.class, method = "updatePermissionList")
	int updatePermissionList(PermissionCollage permissionCollage) throws Exception;


	class PermissionCollageDaoProvider {
		public String updatePermissionList(PermissionCollage permissionCollage) {
			return new SQL() {{
				UPDATE("permission_collage");
//				if(permissionClazz.getId()!=null){
//					SET("id = #{id}");
//				}
				if (permissionCollage.getUser()!= null&&permissionCollage.getUser().getId()!= null) {
					SET("user_id = #{user.id}");
				}
				if (permissionCollage.getCollage()!= null&&permissionCollage.getCollage().getId() != null) {
					SET("collage_id = #{collage.id}");
				}
				WHERE("id = #{id}");
			}}.toString();
		}
	}

}
