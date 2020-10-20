package pers.zdl1004.SchoolLeaveSystem.controller.api;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.annotation.RSATimestampCheck;
import pers.zdl1004.SchoolLeaveSystem.annotation.UserTypeRequired;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.UserService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

/**
 * 用户模块的api接口
 * @author dzj0821
 *
 */
@RequestMapping("/api/user")
@Controller
public class ApiUserController {
	private Logger logger = LogManager.getLogger(ApiUserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 注册功能
	 * @param username 用户名
	 * @param password 密码
	 * @param name 姓名
	 * @param telephone 电话
	 * @param session 框架传入的会话session
	 * @return JSON结果
	 */
	@PostMapping("/register")
	@ResponseBody
	@RSATimestampCheck
	public Map<String, Object> register(@RequestParam String username, @RequestParam String password,
			@RequestParam String name, @RequestParam String telephone, HttpSession session) {
		//从session中获取用于解密密码的RSA密钥对
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		KeyPair keyPair = sessionAdapter.getRsaKeyPair();
		return userService.register(username, password, name, telephone, keyPair.getPrivate());
	}

	/**
	 * 登录功能
	 * @param username 用户名
	 * @param password 密码
	 * @param session 框架传入的会话session
	 * @return JSON结果
	 */
	@PostMapping("/login")
	/* 返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；*/
	@ResponseBody
	@RSATimestampCheck
	public Map<String, Object> login(@RequestParam String username, @RequestParam String password,
			HttpSession session) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		KeyPair keyPair = sessionAdapter.getRsaKeyPair();
		JSONResult result = userService.login(username, password, keyPair.getPrivate());
		if(result.getCode() == JSONCodeType.SUCCESS) {
			//如果登录成功，把User对象放入session中
			User loginedUser = (User) result.get("user");
			sessionAdapter.setUser(loginedUser);
		}
		return result;
	}

	/**
	 * 修改个人资料功能
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param name 姓名
	 * @param telephone 电话
	 * @param session 框架传入的会话session
	 * @return JSON结果
	 */
	@PostMapping("/modify")
	@ResponseBody
	@UserTypeRequired(UserType.NORMAL_USER)
	@RSATimestampCheck
	public Map<String, Object> modify(@RequestParam String oldPassword, @RequestParam String newPassword,
			@RequestParam String name, @RequestParam String telephone, HttpSession session) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		KeyPair keyPair = sessionAdapter.getRsaKeyPair(); 
		User user = sessionAdapter.getUser(); 
		JSONResult result = userService.modify(user, oldPassword, newPassword, name, telephone, keyPair.getPrivate());
		if(result.getCode() == JSONCodeType.SUCCESS) {
			User loginedUser = (User) result.get("user");
			sessionAdapter.setUser(loginedUser);
		}
		return result;
	}

	/**
	 * 获取指定id的用户信息
	 * @param id 需要获取信息的id
	 * @param session 框架传入的会话session
	 * @param response 框架传入的本次请求的回应
	 * @return JSON结果
	 */
	@PostMapping("/info")
	@ResponseBody
	@UserTypeRequired(UserType.NORMAL_USER)
	public Map<String, Object> info(@RequestParam Integer id, HttpSession session, HttpServletResponse response) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser(); //$NON-NLS-1$
		// TODO 目前权限验证写在service层，目标是controller层拦截权限不足的返回值并转为response.sendError(403)
		JSONResult result = userService.getUserInfo(id, user);
		if (result.getCode() == JSONCodeType.ACCESS_DENIED) {
			//如果返回权限不足，转为返回403错误码
			try {
				response.sendError(403);
			} catch (IOException e) {
				logger.warn(e);
			}
			return null;
		}
		return result;
	}
	
	/**
	 * 批量注册功能
	 * @param text 每行依次为 用户名，姓名，电话号码，用tab隔开
	 * @param session
	 * @return
	 */
	@PostMapping("/batchRegister")
	@ResponseBody
	public Map<String, Object> batchRegister(@RequestParam String text, @RequestParam Integer clazzId , HttpSession session) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return userService.batchRegister(text, clazzId, user);
	}
	
	/**
	 * 用户登出
	 * @param session
	 * @return
	 */
	@PostMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = userService.logout(user);
		if(result.getCode() == JSONCodeType.SUCCESS) {
			sessionAdapter.setUser(null);
		}
		return result;
	}
	
	@PostMapping("/changeType")
	@ResponseBody
	public Map<String, Object> changeType(@RequestParam Integer id, @RequestParam UserType type, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return userService.changeType(id, type, user);
	}
}
