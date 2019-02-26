package com.cs.analyzefood.control;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/to")
public class ToIndex {
    @RequestMapping("/login")
    public String login(){
        return "/html/login";
    }

    @RequestMapping("/register")
    public String register(){
        return "/html/register";
    }

    @RequestMapping("/lostPwd")
    public String lt(){
        return "/html/lostPwd";
    }

    @RequestMapping("/CMSIndex")
    public String toAdminIndex(){
        return "/html/manage/index";
    }

    @RequestMapping("/CMSMain")
    public String toAdminMain(){
        return "/html/manage/main";
    }

    @RequestMapping("/CMSAllUsers")
    public String toAllUsers(){
        return "/html/manage/allUsers";
    }

    @RequestMapping("/CMSAllFood")
    public String toAllFood(){
        return "";
    }
}
