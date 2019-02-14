package com.cs.analyzefood.control;

import com.cs.analyzefood.pojo.Admin;
import com.cs.analyzefood.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserControl {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String login(String phone, Model model, String pwd){

        Admin admin = adminService.findAdminByPwd(phone, pwd);
        if(admin != null){
            return "/html/index";
        }


        model.addAttribute("error","error");
        return "/html/login";
    }


    @RequestMapping("/register")
    public String register(){

        return "";
    }

    @RequestMapping("/findPhone")
    public String findPhone(){

        return "";
    }
}
