package pers.zdl1004.SchoolLeaveSystem.controller.api;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.service.GradeService;

@RequestMapping("/api/grade")
@Controller
public class ApiGradeController {
	@Autowired
	private GradeService gradeService;
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(@RequestParam Integer year, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return gradeService.add(year, user);
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam Integer id, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return gradeService.delete(id, user);
	}
}
