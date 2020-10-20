package pers.zdl1004.SchoolLeaveSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import pers.zdl1004.SchoolLeaveSystem.pojo.Grade;

public interface GradeDao {
//	根据id选择grade
	@Select("select * from grade where id = #{id}")
	public Grade selectGradeById(int id) throws Exception;
//	根据年份选择grade
	@Select("select * from grade where year = #{year}")
	public Grade selectGradeByYear(int year) throws Exception;
//	grade集合
	@Select("select * from grade")
	public List<Grade> selectGrades() throws Exception;
//	插入grade
	@Insert("insert into grade(year) values(#{grade})")
	public int insertGrade(int year) throws Exception;
//	根据id删除grade
	@Delete("delete from grade where id = #{id}")
	public void deleteGradeById(int id) throws Exception;
	
}
