package pers.zdl1004.SchoolLeaveSystem.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class aboutController {

    @GetMapping("/about")
    public String About(){
        return "/about/about";
    }

    @GetMapping("/contactUs")
    public String contactUs(){
        return "/about/contactUs";
    }

    @GetMapping("/introduction")
    public String introduction(){
        return "/about/introduction";
    }
}
