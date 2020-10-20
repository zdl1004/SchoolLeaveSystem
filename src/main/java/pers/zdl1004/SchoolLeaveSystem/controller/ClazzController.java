package pers.zdl1004.SchoolLeaveSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.adapter.ModelAdapter;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;

import pers.zdl1004.SchoolLeaveSystem.service.ClazzService;

import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/clazz")
public class ClazzController {
	@Autowired
	private ClazzService clazzService;
	
	private Logger logger = LogManager.getLogger(ClazzController.class);
	
	@GetMapping("/list")
	public String list(HttpSession session, Model model) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = null;
		try {
			result = clazzService.list(user);
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}
		if(result.getCode() == JSONCodeType.SUCCESS) {
			model.addAttribute("map", result.getData().get("map"));
			model.addAttribute("majors", result.getData().get("majors"));
			model.addAttribute("grades", result.getData().get("grades"));
			return "clazz/list";
		}
		ModelAdapter modelAdapter = new ModelAdapter(model);
		modelAdapter.setErrorResult(result);
		return "error";
	}
	
	@GetMapping("/info")
	public String info(@RequestParam Integer id, HttpSession session, Model model) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = clazzService.info(id, user);

		if(result.getCode() == JSONCodeType.SUCCESS) {
			model.addAttribute("users", result.getData().get("users"));
			model.addAttribute("clazzId", id);
			return "clazz/info";
		}
		ModelAdapter modelAdapter = new ModelAdapter(model);
		modelAdapter.setErrorResult(result);
		return "error";
	}

}
