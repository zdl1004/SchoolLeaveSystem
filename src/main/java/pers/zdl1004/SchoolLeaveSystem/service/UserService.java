package pers.zdl1004.SchoolLeaveSystem.service;

import java.security.PrivateKey;

import com.github.pagehelper.PageHelper;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

public interface UserService {
	/**
	 * 注册逻辑
	 * @param username 用户名
	 * @param base64RSAPassword 经过RSA加密后再Base64的密码
	 * @param name 姓名
	 * @param telephone 电话
	 * @param privateKey 解密密码用的私钥
	 * @return
	 * REGISTER_USERNAME_ALREADY_EXIST 用户名已存在<br>
	 */
	public JSONResult register(String username, String base64RSAPassword, String name, String telephone, PrivateKey privateKey);
	
	/**
	 * 登录逻辑
	 * @param username 用户名
	 * @param base64RSApassword 经过RSA加密后再Base64的密码
	 * @param privateKey 解密密码用的私钥
	 * @return
	 * USER_NOT_FOUND 用户不存在<br>
	 * USERNAME_OR_PASSWORD_ERROR 用户名或密码错误
	 */
	public JSONResult login(String username, String base64RSApassword, PrivateKey privateKey);
	
	/**
	 * 修改用户资料逻辑
	 * @param user 发起请求的用户
	 * @param base64RSAOldPassword 旧密码
	 * @param base64RSANewPassword 新密码
	 * @param name 姓名
	 * @param telephone 电话
	 * @param privateKey 解密密码用的私钥
	 * @return
	 * OLD_PASSWORD_ERROR 旧密码错误
	 */
	public JSONResult modify(User user, String base64RSAOldPassword, String base64RSANewPassword, String name, String telephone, PrivateKey privateKey);
	
	/**
	 * 登出逻辑
	 * @param user 需要登出的用户
	 * @return
	 * ACCESS_DENIED 用户未登录
	 */
	public JSONResult logout(User user);
	/**
	 * 某用户请求根据id获取另一用户信息
	 * @param willGetUserId 请求获取的用户id
	 * @param fromUser 发起请求的用户
	 * @return
	 * ACCESS_DENIED 发起请求的用户权限不足<br>
	 * USER_NOT_FOUND 目标用户不存在
	 */
	public JSONResult getUserInfo(int willGetUserId, User fromUser);
	
	public JSONResult getManageClazzes(User user);
	
	public JSONResult batchRegister(String text, Integer clazzId, User user);


	public JSONResult list(int page, User user);

	public JSONResult list(User user);
	
	public JSONResult changeType(Integer changeUserId, UserType changeType, User user);




}
