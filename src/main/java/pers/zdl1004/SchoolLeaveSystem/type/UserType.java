package pers.zdl1004.SchoolLeaveSystem.type;

import com.fasterxml.jackson.annotation.JsonValue;

//枚举类型
public enum UserType {
	SUPER_ADMIN(0, "超级管理员"),
	COLLAGE_ADMIN(1, "学院管理员"),
	CLAZZ_ADMIN(2, "班级管理员"),
	NORMAL_USER(3, "普通用户");
	
	private int code;
	private String name;
	
	private UserType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 
	 * @return 用户类型对应代码，数值越小权限越高
	 */
	@JsonValue
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
