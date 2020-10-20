package pers.zdl1004.SchoolLeaveSystem.controller.api;

import java.util.Map;

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
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.ClazzService;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

@Controller
@RequestMapping("/api/clazz")
public class ApiClazzController {
	@Autowired
	private ClazzService clazzService;
	
	private Logger logger = LogManager.getLogger(ApiClazzController.class);
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(@RequestParam Integer no, @RequestParam Integer majorId, @RequestParam Integer gradeId, HttpSession session){
		JSONResult result = null;
		try {
			result = clazzService.add(no, gradeId, majorId, new HttpSessionAdapter(session).getUser());
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}
		return result;
	}
	
	@PostMapping("/change")
	@ResponseBody
	public Map<String, Object> change(@RequestParam Integer id, @RequestParam Integer no, @RequestParam Integer majorId, @RequestParam Integer gradeId, HttpSession session){
		JSONResult result = null;
		try {
			result = clazzService.change(id, no, gradeId, majorId, new HttpSessionAdapter(session).getUser());
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}
		return result;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam Integer id, HttpSession session){
		JSONResult result = null;
		try {
			result = clazzService.delete(id, new HttpSessionAdapter(session).getUser());
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}
		return result;
	}
	
	@PostMapping("/addUser")
	@ResponseBody
	public Map<String, Object> addUser(@RequestParam Integer id, @RequestParam String username, HttpSession session){
		JSONResult result = null;
		try {
			result = clazzService.addUser(id, username, new HttpSessionAdapter(session).getUser());
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}
		return result;
	}


//	修改用户类型
	@PostMapping("/changeUserType")
	@ResponseBody
//	public  Map<String, Object>  changeUser(@RequestParam Integer id, @RequestParam String username,HttpSession session) throws Exception {
//		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
//		User user = sessionAdapter.getUser();
//		return clazzService.changeUser(id,username,user);
//	}
	public Map<String, Object> changeUser(@RequestParam Integer id, @RequestParam String username, HttpSession session) throws Exception {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return clazzService.changeUser(id,username,user);
		}

//	@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML
//　　数据，需要注意的呢，在使用此注解之后不会再走试图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
//@RequestMapping("/login")
//　　@ResponseBody
//　　public User login(User user){
//　　　　return user;
//　　}
//　　User字段：userName pwd
//　　那么在前台接收到的数据为：'{"userName":"xxx","pwd":"xxx"}'

	@PostMapping("/giveUserType")
	@ResponseBody
	public  Map<String, Object>  giveUser(@RequestParam Integer id, @RequestParam String username,HttpSession session) throws Exception {
		//@RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return clazzService.giveUser(id,username,user);
	}



	@PostMapping("/removeUser")
	@ResponseBody
	public Map<String, Object> removeUser(@RequestParam Integer id, @RequestParam Integer userId, HttpSession session){
		JSONResult result = null;
		try {
			result = clazzService.removeUser(id, userId, new HttpSessionAdapter(session).getUser());
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}
		return result;
	}

}
