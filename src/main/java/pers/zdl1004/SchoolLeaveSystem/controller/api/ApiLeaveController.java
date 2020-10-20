package pers.zdl1004.SchoolLeaveSystem.controller.api;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.annotation.UserTypeRequired;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.service.LeaveService;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

/**
 * 请假模块的api接口
 *
 * @author dzj0821
 */
@RequestMapping("/api/leave")
@Controller
public class ApiLeaveController {

    @Autowired
    private LeaveService leaveService;

    /**
     * 创建假条
     *
     * @param startYear   请假起始年份
     * @param startMonth  请假起始月份
     * @param startDay    请假起始日期
     * @param startLesson 请假开始第X节课
     * @param endYear     请假结束年份
     * @param endMonth    请假结束月份
     * @param endDay      请假结束日期
     * @param endLesson   请假开始第Y节课
     * @param reason      请假原因
     * @param images      请假附件图片数组
     * @param session
     * @return
     */
    @PostMapping("/create")
    @UserTypeRequired(UserType.NORMAL_USER)
    @ResponseBody
    public Map<String, Object> create(@RequestParam int startYear, @RequestParam int startMonth,
                                      @RequestParam int startDay, @RequestParam int startLesson, @RequestParam int endYear,
                                      @RequestParam int endMonth, @RequestParam int endDay, @RequestParam int endLesson,
                                      @RequestParam String reason, @RequestParam(value = "image", required = false) CommonsMultipartFile[] images,
                                      HttpSession session) {
        HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
        User user = sessionAdapter.getUser();
        return leaveService.create(user, startYear, startMonth, startDay, startLesson, endYear, endMonth, endDay, endLesson,
                reason, images, session.getServletContext().getRealPath("/"));
    }

    /**
     * 取消请假申请
     *
     * @param id      需要取消的请假id
     * @param session
     * @return
     */
    @PostMapping("/cancel")
    @ResponseBody
    public Map<String, Object> cancel(@RequestParam int id, HttpSession session) {
        HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
        User user = sessionAdapter.getUser();
        return leaveService.cancel(user, id);
    }

    /**
     * 学生自助销假
     * @param id
     * @param session
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam int id, HttpSession session) throws Exception {
        HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(session);
        User user = sessionAdapter.getUser();
        return leaveService.delete(user, id);
    }
}
