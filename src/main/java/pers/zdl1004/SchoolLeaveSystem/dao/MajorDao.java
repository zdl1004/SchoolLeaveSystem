package pers.zdl1004.SchoolLeaveSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import pers.zdl1004.SchoolLeaveSystem.pojo.Major;

public interface MajorDao {
//	根据id查找专业
	@Select("select * from major where id = #{id}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
	})
	public Major selectMajorById(int id) throws Exception;

//	根据collage_id查找专业
	@Select("select * from major where collage_id = #{id}")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
	})
	public List<Major> selectMajorsByCollageId(int collageId) throws Exception;

//查找所有专业，集合显示
	@Select("select * from major")
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "collage_id", property = "collage", one = @One(select = "pers.zdl1004.SchoolLeaveSystem.dao.CollageDao.selectCollageById"))
	})
	public List<Major> selectMajors() throws Exception;

//插入专业名称和学院id
	@Insert("insert into major(name, collage_id) values(#{name}, #{collage.id})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertMajor(Major major) throws Exception;

//MajorDaoProvider方法更新专业
	@UpdateProvider(type = MajorDaoProvider.class, method = "updateMajorById")
	public void updateMajorById(Major major) throws Exception;

//根据id删除专业
	@Delete("delete from major where id = #{id}")
	public void deleteMajorById(int id) throws Exception;

//	更新majorDao
	class MajorDaoProvider {
		public String updateMajorById(Major major) {
			return new SQL() {{
				UPDATE("major");
				if(major.getName() != null) {
					SET("name = #{name}");
				}
				if(major.getCollage() != null && major.getCollage().getId() != null) {
					SET("collage_id = #{collage.id}");
				}
				WHERE("id = #{id}");
			}}.toString();
		}
	}
}
