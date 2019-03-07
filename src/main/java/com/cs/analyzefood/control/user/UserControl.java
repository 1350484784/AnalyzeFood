package com.cs.analyzefood.control.user;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.service.AdminService;
import com.cs.analyzefood.service.UserService;
import com.cs.analyzefood.util.SendMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;


@Controller
@RequestMapping("/user")
@SessionAttributes({"userId","user","admin"})
public class UserControl {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String login(String phone, Model model, String pwd) {

        Admin admin = adminService.findAdminByPwd(phone, pwd);
        if (admin != null) {
            /**
             * 待完善
             */
            model.addAttribute("admin",admin);
            //后台首页
            return "/html/manage/index";
        }
        String base64Pwd = Base64.getEncoder().encodeToString((pwd).getBytes());

        User user = userService.findUserByPhoneAndPwd(phone, base64Pwd);
        if(user != null){
            //修改 在线标记
            userService.updateUserOnlineFlag(user.getRoleId(), (byte) 1);

            /**
             * 用户首页
             */
            model.addAttribute("userId", user.getRoleId());
            model.addAttribute("user", user);
            return "/html/index";
        }
        logger.debug(phone + " login failed");
        model.addAttribute("error", "error");
        return "/html/login";
    }


    @RequestMapping("/register")
    public String register(String phone, String pwd,Model model) {
        int roleId = userService.addNewUser(phone, pwd);
        if (roleId > 0) {
            User currentUser = userService.findUserById(roleId);
            if(currentUser!= null){
                model.addAttribute("userId", roleId);
                model.addAttribute("user", currentUser);
                logger.debug(phone + " register success");
                //首页
                return "/html/index";
            }
        }
        logger.debug(phone + " register failed");
        return "/html/login";
    }

    @RequestMapping("/findPhone")
    @ResponseBody
    public boolean findPhone(String phone) {
        User user = userService.findUserByPhone(phone);
        if (user != null) {
            return true;
        }
        return false;
    }

    @RequestMapping("/getCheckWord")
    @ResponseBody
    public String getCheckWord(HttpServletRequest request, String phoneNum){
        String checkWord = String.valueOf(Math.random()).substring(2, 8);
        SendMessageUtil.execute(checkWord,phoneNum);
        logger.debug(phoneNum + " get verification code is " + checkWord);
        return checkWord;
    }

    @RequestMapping("/lostPwd")
    public String updatePwdForLost(Model model,String phone,String pwd){
        boolean flag = userService.updateUserPwd(phone, pwd);
        if(flag){
            return "/html/login";
        }
        logger.debug(phone + " password change failed");
        model.addAttribute("error","error");
        return "/html/LostPwd";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            userService.updateUserOnlineFlag(user.getRoleId(), (byte) 0);
            sessionStatus.setComplete();//将session移除
        }
        return "/html/login";
    }
}
