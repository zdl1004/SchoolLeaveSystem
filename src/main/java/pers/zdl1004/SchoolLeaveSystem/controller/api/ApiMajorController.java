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
import pers.zdl1004.SchoolLeaveSystem.service.MajorService;

@Controller
@RequestMapping("/api/major")
public class ApiMajorController {
	@Autowired
	private MajorService majorService;
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(@RequestParam Integer collageId, @RequestParam String name, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return majorService.add(collageId, name, user);
	}
	
	@PostMapping("/change")
	@ResponseBody
	public Map<String, Object> change(@RequestParam Integer id, @RequestParam String name, @RequestParam Integer collageId, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return majorService.change(id, name, collageId, user);
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam Integer id, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		return majorService.delete(id, user);
	}
}
