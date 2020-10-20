package pers.zdl1004.SchoolLeaveSystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.adapter.ModelAdapter;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.service.CollageService;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;

@Controller
@RequestMapping("/collage")
public class CollageController {
	
	@Autowired
	private CollageService collageService;
	
	@GetMapping("/list")
	public String list(HttpSession session, Model model) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = collageService.list(user);
		if(result.getCode() == JSONCodeType.SUCCESS) {
			model.addAttribute("collages", result.getData().get("collages"));
			return "collage/list";
		}
		ModelAdapter modelAdapter = new ModelAdapter(model);
		modelAdapter.setErrorResult(result);
		return "error";
	}
//	@GetMapping("/info")
//	public String info(@RequestParam Integer id, HttpSession session, Model model) {
//		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
//		User user = sessionAdapter.getUser();
//		JSONResult result = collageService.info(id, user);
//		if(result.getCode() == JSONCodeType.SUCCESS) {
//			model.addAttribute("users", result.getData().get("users"));
//			model.addAttribute("collageId", id);
//			return "collage/info";
//		}
//		ModelAdapter modelAdapter = new ModelAdapter(model);
//		modelAdapter.setErrorResult(result);
//		return "error";
//	}
}
