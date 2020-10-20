package pers.zdl1004.SchoolLeaveSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pers.zdl1004.SchoolLeaveSystem.pojo.Collage;

/*持久层*/
public interface CollageDao {
//	id查找学院
	@Select("select * from collage where id = #{id}")
	public Collage selectCollageById(int id) throws Exception;

	//name查找学院
	@Select("select * from collage where name= #{name}")
	public Collage selectCollageByName(String name) throws Exception;
//	集合
	@Select("select * from collage")
	public List<Collage> selectCollages() throws Exception;

//	插入学院
	@Insert("insert into collage(name) values(#{name})")
	public int insertCollage(String name) throws Exception;

//	根据id更新学院
	@Update("update collage set name = #{name} where id = #{id}")
	public void updateCollageById(Collage collage) throws Exception;

//	根据id删除学院
	@Delete("delete from collage where id = #{id}")
	public void deleteCollageById(int id) throws Exception;
}
