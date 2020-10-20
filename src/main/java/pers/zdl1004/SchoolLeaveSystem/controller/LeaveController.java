package pers.zdl1004.SchoolLeaveSystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import pers.zdl1004.SchoolLeaveSystem.Messages;
import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.adapter.ModelAdapter;
import pers.zdl1004.SchoolLeaveSystem.annotation.UserTypeRequired;

import pers.zdl1004.SchoolLeaveSystem.pojo.Leave;
import pers.zdl1004.SchoolLeaveSystem.pojo.LeaveImage;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;

import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.pojo.view.LeaveInfoView;
import pers.zdl1004.SchoolLeaveSystem.service.LeaveService;

import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;
import pers.zdl1004.SchoolLeaveSystem.pojo.view.LeaveListView;

import pers.zdl1004.SchoolLeaveSystem.type.LeaveType;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;
//TODO 使用自定义EL函数在JSP中取值
/**
 * 请假模块页面的Controller
 * @author dzj0821
 *
 */
@Controller
@RequestMapping("/leave")//处理leave请求

public class LeaveController {
	@Autowired
	private LeaveService leaveService;
//	创建leaveService的对象,创建，取消，以及信息列表方法。通过实现类来实现
	private Logger logger = LogManager.getLogger(LeaveController.class);
	
	/**
	 * 创建假条页面
	 * @param model
	 * @return
	 */
	@GetMapping("/create")//创建create初始界面
	@UserTypeRequired(UserType.NORMAL_USER)//请求为NORMAL_USER发出来的

	public String create(HttpSession session, Model model) {
		if(new HttpSessionAdapter(session).getUser().getType() != UserType.NORMAL_USER) {
			ModelAdapter modelAdapter = new ModelAdapter(model);
			modelAdapter.setErrorResult(JSONResult.ACCESS_DENIED);
			return "error";
		}//用户类型不为普通用户，报错，返回error.jsp

		Calendar calendar = Calendar.getInstance();
		int[] years = new int[3];
		//获取当前年份
		int currentYear = calendar.get(Calendar.YEAR);
		//提供当前年份到3年后（如2019~2021）的时间
		for(int i = 0; i < years.length; i++) {
			years[i] = currentYear++;
		}
		//月份从0开始
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentDay = calendar.get(Calendar.DATE);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("years", years);//当前年份
		model.addAttribute("currentMonth", currentMonth);//当前月份
		model.addAttribute("currentDay", currentDay);//当前天
		model.addAttribute("lastDay", lastDay);//当前年份
		return Messages.getString("CreateLeavePage");//CreateLeavePage=leave/create
	}
	
	@PostMapping("/create")//处理post请求，传入参数
	public String createRequest(@RequestParam int startYear,
								@RequestParam int startMonth,
								@RequestParam int startDay,
								@RequestParam int startLesson,
								@RequestParam int endYear,
								@RequestParam int endMonth,
								@RequestParam int endDay,
								@RequestParam int endLesson,
								@RequestParam String reason,
								@RequestParam(value = "image", required = false) CommonsMultipartFile[] images, HttpSession session, Model model, HttpServletResponse response) {

		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = leaveService.create(user, startYear, startMonth, startDay, startLesson, endYear, endMonth, endDay, endLesson, reason, images, session.getServletContext().getRealPath("/"));
		//调用leaveservice方法创建对象，传入参数
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if(result.getCode() != JSONCodeType.SUCCESS) {
			modelAdapter.setErrorResult(result);
			return "error";
		}
		try {
			response.sendRedirect("list");//跳转到http://localhost:8080/leave/list
		} catch (IOException e) {
			modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
			return "error";
		}
		return null;
	}
	
	
	@GetMapping("/list")//跳转到list后更新list页面
	public String list(Integer page, Integer clazzId, Integer userId, LeaveType type, String startDate, String endDate, HttpSession session, Model model) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		if (page == null || page < 1) {
			page = 1;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null, end = null;
		try {
			start = format.parse(startDate);
			end = format.parse(endDate);
		} catch (ParseException | NullPointerException e) {

		}

		JSONResult result = null;
		try {
			result = leaveService.list(page, user, clazzId, userId, start, end, type);//调用leaveService中的list方法传入参数
		} catch (Exception e) {
			logger.warn(e);
			result = JSONResult.SERVER_ERROR;
		}

		ModelAdapter modelAdapter = new ModelAdapter(model);
		if(result.getCode() != JSONCodeType.SUCCESS) {
			modelAdapter.setErrorResult(result);
			return "error";
		}
		@SuppressWarnings("unchecked")
		List<Leave> leaves  = (List<Leave>) result.getData().get("leaves");
		List<LeaveListView> leaveListViews = new ArrayList< >(leaves.size());

		for (Leave leave : leaves) {
			leaveListViews.add(new LeaveListView(leave));//遍历且添加
		}
		modelAdapter.setLeaveList(leaveListViews);
		model.addAttribute("pageList", result.getData().get("pageList"));
		model.addAttribute("page", page);
		model.addAttribute("pageCount", result.getData().get("pageCount"));
		model.addAttribute("enableClazzes", result.getData().get("enableClazzes"));
		return "leave/list";
	}
	
	/**
	 * 审核界面
	 * 
	 */
	@GetMapping("/info")
	public String info(@RequestParam int id, Model model, HttpSession session){
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = leaveService.info(user, id);
		ModelAdapter modelAdapter = new ModelAdapter(model);

		if(result.getCode() != JSONCodeType.SUCCESS) {
			modelAdapter.setErrorResult(result);
			return "error";
		}

//控制用户能查看到的请假范围
		Leave leave = (Leave) result.getData().get("leave");
		@SuppressWarnings("unchecked")
		List<LeaveImage> leaveImages = (List<LeaveImage>) result.getData().get("leaveImages");
		LeaveInfoView leaveInfoView = new LeaveInfoView(leave, leaveImages);//请假信息界面
		model.addAttribute("leaveInfoView", leaveInfoView);

		return "leave/info";
	}
	
	@PostMapping("/review")
	public String review(@RequestParam int id, @RequestParam boolean access, HttpSession session, Model model, HttpServletResponse response) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = leaveService.review(user, id, access);
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if(result.getCode() != JSONCodeType.SUCCESS) {
			modelAdapter.setErrorResult(result);
			return "error";
		}
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
			return "error";
		}
		return null;
	}
	
	@GetMapping("/cancel")
	public String cancel(@RequestParam int id, HttpSession session, Model model, HttpServletResponse response, HttpServletRequest request) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = leaveService.cancel(user, id);
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if(result.getCode() != JSONCodeType.SUCCESS) {
			modelAdapter.setErrorResult(result);
			return "error";
		}
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
			return "error";
		}
		return null;
		
	}

	@GetMapping("/delete")
	public String delete(@RequestParam int id, HttpSession session, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = leaveService.delete(user, id);
		ModelAdapter modelAdapter = new ModelAdapter(model);
		if(result.getCode() != JSONCodeType.SUCCESS) {
			modelAdapter.setErrorResult(result);
			return "error";
		}
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			modelAdapter.setErrorResult(JSONResult.SERVER_ERROR);
			return "error";
		}
		return null;

	}

	@GetMapping("/export")
	public ResponseEntity<byte[]> export(HttpSession session) {
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
		User user = sessionAdapter.getUser();
		JSONResult result = null;
		try {
			result = leaveService.list(user);
		} catch (Exception e) {
			return null;
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet");
		List<Leave> leaves = (List<Leave>) result.getData().get("leaves");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < leaves.size(); i++) {
			Leave element = leaves.get(i);
			HSSFRow row = sheet.createRow(i);
			//序号
			row.createCell(0).setCellValue(element.getId());
			//申请者
			row.createCell(1).setCellValue(element.getUser().getName());
			//班级
			if (element.getUser().getClazz() == null) {
				row.createCell(2).setCellValue("/");
			} else {
				row.createCell(2).setCellValue(element.getUser().getClazz().getFullName());
			}
			//时间
			row.createCell(3).setCellValue(format.format(element.getStartDate()) + "第" + element.getStartLesson() + "节课 至 " + format.format(element.getEndDate()) + "第" + element.getEndLesson() + "节课");
			//申请时间
			row.createCell(4).setCellValue(format.format(element.getCreateTime()));
			if (element.getType() == LeaveType.CANCEL) {
				row.createCell(5).setCellValue("已取消");
			}
			if (element.getType() == LeaveType.WAIT) {
				row.createCell(5).setCellValue("待审核");
			}
			if (element.getType() == LeaveType.PASS) {
				row.createCell(5).setCellValue("审核通过");
			}
			if (element.getType() == LeaveType.NOT_PASS) {
				row.createCell(5).setCellValue("审核未通过");
			}


		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=sheet.xls");
		return new ResponseEntity<byte[]>(workbook.getBytes(), headers, HttpStatus.OK);
	}
	
}
