package com.cs.analyzefood.control;


import com.cs.analyzefood.entity.Meal;
import com.cs.analyzefood.entity.MealMade;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.exception.SystemFailedException;
import com.cs.analyzefood.service.UserService;
import com.cs.analyzefood.util.JsonUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/to")
public class ToPage {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "/html/login";
    }

    @RequestMapping("/userIndex")
    public String index(@RequestParam(name = "currentPage",defaultValue = "1") int currentPage, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        int sum = userService.getMealSum(user.getRoleId());
        PageInfo<Meal> pageInfo = userService.getPageMeal(user.getRoleId(),currentPage);
        model.addAttribute("topIndex", 0);
        model.addAttribute("meals", pageInfo);
        model.addAttribute("sum", sum);
        return "/html/index";
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
        return "/html/manage/allFood";
    }

    @RequestMapping("/CMSAddFood")
    public String toAddFood(){
        return "/html/manage/addFood";
    }

    @RequestMapping("/userUpdatePwd")
    public String toUserUpdatePwd(){
        return "/html/user/userUpdatePwd";
    }

    @RequestMapping("/userAddMeal")
    public String userAddMeal(){
        return "/html/user/addDietDetail";
    }

    @RequestMapping("/dietList")
    public String toDietList(Model model){
        model.addAttribute("topIndex", 2);
        return "/html/user/dietList";
    }

    @RequestMapping("/articleIndex")
    public String articleIndex(Model model, HttpSession session){
        // 查询
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }


        model.addAttribute("topIndex", 1);
        return "/html/user/articleIndex";
    }

    @RequestMapping("/addArticle")
    public String addArticle(Model model){
        model.addAttribute("topIndex", 1);
        return "/html/user/addArticle";
    }


}
