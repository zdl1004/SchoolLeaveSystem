package pers.zdl1004.SchoolLeaveSystem.controller;

import java.io.IOException;
import java.security.KeyPair;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pers.zdl1004.SchoolLeaveSystem.Messages;
import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.adapter.ModelAdapter;
import pers.zdl1004.SchoolLeaveSystem.annotation.UserTypeRequired;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.pojo.view.UserInfoView;
import pers.zdl1004.SchoolLeaveSystem.service.UserService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

/**
 * 用户模块页面的Controller
 * 
 * @author zdl1004
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;//创建服务层对象
	/**
	 * 注册页面
	 */
	@GetMapping("/register")
	public String register() {
		return "user/register";//跳转到register.jsp页面
	}

	@PostMapping("/register")
	public String registerRequest(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String telephone, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
		// 从session中获取用于解密密码的RSA密钥对
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		KeyPair keyPair = sessionAdapter.getRsaKeyPair();

		JSONResult result = userService.register(username, password, name, telephone, keyPair.getPrivate());
		ModelAdapter modelAdapter = new ModelAdapter(model);

		if (result.getCode() == JSONCodeType.SUCCESS) {
			try {
				response.sendRedirect("login");//跳转到登录界面
			} catch (IOException e) {
				modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
				return "error";
			}
			return null;
		}
		modelAdapter.setErrorResult(result);
		return "error";
	}


	/**
	 * 批量注册注册页面
	 */
	@GetMapping("/batchRegister")
	public String batchRegister(HttpSession session, Model model) {

		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();

		JSONResult result = userService.getManageClazzes(user);
		if(result.getCode() == JSONCodeType.SUCCESS) {
			model.addAttribute("clazzes", result.getData().get("clazzes"));
			return "user/batchRegister";
		}
		ModelAdapter modelAdapter = new ModelAdapter(model);
		modelAdapter.setErrorResult(result);
		return "error";
	}


	/**
	 * 登录页面
	 */
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@PostMapping("/login")
	public String loginRequest(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletResponse response, HttpServletRequest request, Model model) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		KeyPair keyPair = sessionAdapter.getRsaKeyPair();
		JSONResult result = userService.login(username, password, keyPair.getPrivate());
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if (result.getCode() == JSONCodeType.SUCCESS) {

			// 如果登录成功，把User对象放入session中
			User loginedUser = (User) result.get("user");
			sessionAdapter.setUser(loginedUser);
			try {
				response.sendRedirect(request.getContextPath() + "/");
			} catch (IOException e) {
				modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
				return "error";
			}
			return null;
		}
		modelAdapter.setErrorResult(result);
		return "error";
	}

	/**
	 * 修改个人信息页面
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/modify")
	@UserTypeRequired(UserType.NORMAL_USER)//一般用户权限
	public String modify() {
		return "user/modify"; //$NON-NLS-1$
	}
	
	@PostMapping("/modify")
	public String modifyRequest(@RequestParam String oldPassword, @RequestParam String newPassword,
			@RequestParam String name, @RequestParam String telephone, HttpSession session, HttpServletResponse response, Model model) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		KeyPair keyPair = sessionAdapter.getRsaKeyPair(); 
		User user = sessionAdapter.getUser(); 
		JSONResult result = userService.modify(user, oldPassword, newPassword, name, telephone, keyPair.getPrivate());
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if(result.getCode() == JSONCodeType.SUCCESS) {
			User loginedUser = (User) result.get("user");
			sessionAdapter.setUser(loginedUser);
			try {
				response.sendRedirect("info?id=" + user.getId());
			} catch (IOException e) {
				modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
				return "error";
			}
			return null;
		}
		modelAdapter.setErrorResult(result);
		return "error";
	}

	/**
	 * 个人信息页面
	 * 
	 * @param id      查看用户信息的id
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */

	@GetMapping("/info")
	public String info(@RequestParam Integer id, Model model, HttpServletRequest request, HttpSession session) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		ModelAdapter modelAdapter = new ModelAdapter(model);
		JSONResult result = userService.getUserInfo(id, sessionAdapter.getUser());
		if (result.getCode() == JSONCodeType.SUCCESS) {
			// 如果查询成功，显示个人信息页面
			UserInfoView view = (UserInfoView) result.getData().get(Messages.getString("UserInfoDataUserName")); //$NON-NLS-1$
			modelAdapter.setUserInfoView(view);
			return Messages.getString("UserInfoPage"); //$NON-NLS-1$
		}
		// 失败显示错误页面
		modelAdapter.setErrorResult(result);
		return Messages.getString("ErrorPage"); //$NON-NLS-1$
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model model, HttpServletResponse response, HttpServletRequest request) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = userService.logout(user);
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if (result.getCode() == JSONCodeType.SUCCESS) {
			sessionAdapter.setUser(null);
			try {
				response.sendRedirect(request.getContextPath() + "/");
			} catch (IOException e) {
				modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
				return "error";
			}
			return null;
		}
		modelAdapter.setErrorResult(result);
		return "error";
	}

//	查询所有用户
	@GetMapping("/list")
	public String list(Integer page, HttpSession session, Model model)
	{
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		if (page == null || page < 1) {
			page = 1;
		}
		JSONResult result = userService.list(page, user);

		if(result.getCode() == JSONCodeType.SUCCESS) {
			model.addAttribute("users", result.getData().get("users"));
			model.addAttribute("pageList", result.getData().get("pageList"));
			model.addAttribute("page", page);
			model.addAttribute("pageCount", result.getData().get("pageCount"));
//			PageInfo pageInfo = new PageInfo(result);
			return "user/list";
		}
		ModelAdapter modelAdapter = new ModelAdapter(model);
		modelAdapter.setErrorResult(result);
		return "error";
	}

	@GetMapping("/export")
	public ResponseEntity<byte[]> export(HttpSession session) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = userService.list(user);
		if (result.getCode() != JSONCodeType.SUCCESS) {
			return null;
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet");
		List<User> users = (List<User>) result.getData().get("users");
		for (int i = 0; i < users.size(); i++) {
			User element = users.get(i);
			HSSFRow row = sheet.createRow(i);
			//学号
			row.createCell(0).setCellValue(element.getId());
			//用户类型
			row.createCell(1).setCellValue(element.getType().getName());
			//姓名
			row.createCell(2).setCellValue(element.getName());
			//手机
			if (element.getTelephone() != null) {
				row.createCell(3).setCellValue(element.getTelephone());
			}
			//所属班级
			if (element.getClazz() == null) {
				row.createCell(4).setCellValue("/");
			} else {
				row.createCell(4).setCellValue(element.getClazz().getFullName());
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=sheet.xls");
		return new ResponseEntity<byte[]>(workbook.getBytes(), headers, HttpStatus.OK);
	}
}
