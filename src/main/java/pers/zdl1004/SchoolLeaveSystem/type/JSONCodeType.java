package pers.zdl1004.SchoolLeaveSystem.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JSONCodeType {
	/**
	 * 成功
	 */
	SUCCESS(100),
	/**
	 * 参数不合法
	 */
	INVALID_PARAMS(200),
	/**
	 * 服务器错误
	 */
	SERVER_ERROR(300),
	/**
	 * 用户名已存在
	 */
	REGISTER_USERNAME_ALREADY_EXIST(400),
	/**
	 * 会话超时
	 */
	SESSION_TIMEOUT(401),
	/**
	 * 数据不存在
	 */
	DATA_NOT_FOUND(402),
	/**
	 * 用户名或密码错误
	 */
	USERNAME_OR_PASSWORD_ERROR(403),
	/**
	 * 拒绝访问
	 */
	ACCESS_DENIED(404),
	/**
	 * 旧密码错误
	 */
	OLD_PASSWORD_ERROR(405),
	;
	
	private int code;
	private JSONCodeType(int code) {
		this.code = code;
	}
	@JsonValue
	public int getCode() {
		return code;
	}
}
