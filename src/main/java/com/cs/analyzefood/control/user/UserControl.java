package com.cs.analyzefood.control.user;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.entity.UserZone;
import com.cs.analyzefood.entity.vo.diet.DietVo;
import com.cs.analyzefood.entity.vo.page.PageCondition;
import com.cs.analyzefood.entity.vo.page.PageFoodVo;
import com.cs.analyzefood.exception.SystemFailedException;
import com.cs.analyzefood.service.AdminService;
import com.cs.analyzefood.service.UserService;
import com.cs.analyzefood.service.UserZoneService;
import com.cs.analyzefood.util.JsonUtil;
import com.cs.analyzefood.util.SendMessageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/user")
@SessionAttributes({"user", "admin", "userZone"})
public class UserControl {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserZoneService userZoneService;

    @Value("${user.headImg.path}")
    private String headImg_path;

    @Value("${user.bgImg.path}")
    private String bgImg_path;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String login(String phone, Model model, String pwd) {

        Admin admin = adminService.findAdminByPwd(phone, pwd);
        if (admin != null) {
            /**
             * 待完善
             */
            model.addAttribute("admin", admin);
            //后台首页
            return "/html/manage/index";
        }
        String base64Pwd = Base64.getEncoder().encodeToString((pwd).getBytes());

        User user = userService.findUserByPhoneAndPwd(phone, base64Pwd);
        if (user != null) {
            //修改 在线标记
            userService.updateUserOnlineFlag(user.getRoleId(), (byte) 1);
            UserZone userZone = userZoneService.selectUserZone(user.getRoleId());
            /**
             * 用户首页
             */
            model.addAttribute("userZone", userZone);
            model.addAttribute("user", user);
            return "/html/index";
        }
        logger.debug(phone + " login failed");
        model.addAttribute("error", "error");
        return "/html/login";
    }


    @RequestMapping("/register")
    public String register(String phone, String pwd, Model model) {
        int roleId = userService.addNewUser(phone, pwd);
        if (roleId > 0) {
            User currentUser = userService.findUserById(roleId);
            if (currentUser != null) {
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
    public String getCheckWord(HttpServletRequest request, String phoneNum) {
        String checkWord = String.valueOf(Math.random()).substring(2, 8);
        SendMessageUtil.execute(checkWord, phoneNum);
        logger.debug(phoneNum + " get verification code is " + checkWord);
        return checkWord;
    }

    @RequestMapping("/lostPwd")
    public String updatePwdForLost(Model model, String phone, String pwd) {
        boolean flag = userService.updateUserPwd(phone, pwd);
        if (flag) {
            return "/html/login";
        }
        logger.debug(phone + " password change failed");
        model.addAttribute("error", "error");
        return "/html/LostPwd";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userService.updateUserOnlineFlag(user.getRoleId(), (byte) 0);
            sessionStatus.setComplete();//将session移除
        }
        return "/html/login";
    }

    @RequestMapping("/editUser")
    @ResponseBody
    public ResponseEntity editUser(User user) {
        boolean flag = userService.updateUserSelf(user);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @RequestMapping("/uploadHeadImg")
    @ResponseBody
    public ResponseEntity uploadHeadImg(@RequestParam("file") MultipartFile file, HttpSession session) {
        String filename = file.getOriginalFilename();
        String imgName = UUID.randomUUID().toString().replace("-", "") + "_" + filename;

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }

        File newFile = new File(headImg_path + imgName);
        if (!newFile.exists()) {
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        String url = userService.uploadUserHeadImg(user.getRoleId(), imgName);
        return new ResponseEntity(url, HttpStatus.OK);
    }

    @RequestMapping("/uploadBgImg")
    @ResponseBody
    public ResponseEntity uploadBgImg(@RequestParam("file") MultipartFile file, HttpSession session) {
        String filename = file.getOriginalFilename();
        String bgImgName = UUID.randomUUID().toString().replace("-", "") + "_" + filename;

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }

        File newFile = new File(bgImg_path + bgImgName);
        if (!newFile.exists()) {
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        String url = userZoneService.uploadUserBgImg(user.getRoleId(), bgImgName);
        return new ResponseEntity(url, HttpStatus.OK);
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public ResponseEntity updatePwd(HttpSession session, String oldPwd, String newPwd) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        try {
            if (!new String(Base64.getDecoder().decode(user.getPassword()), "utf-8").equals(oldPwd)) {
                return new ResponseEntity(false, HttpStatus.OK);
            }
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
        }

        boolean flag = userService.updateUserPwd(user.getPhone(), newPwd);
        return new ResponseEntity(flag, HttpStatus.OK);
    }


//    @RequestMapping("/userAddMeal")
//    public String userAddMeal(@RequestParam(name = "currentPage",defaultValue = "1") int currentPage, Model model){
//        PageInfo<Food> pageInfo = userService.getAllfood(currentPage);
//        model.addAttribute("foods",pageInfo);
//        System.out.println(JsonUtil.toJson(pageInfo));
//        return "/html/user/addDietDetail";
//    }


    @RequestMapping("/foodPage")
    public ResponseEntity userAddMeal(@RequestBody PageCondition pageCondition) {
        if(pageCondition == null){
            pageCondition = new PageCondition(1);
        }
        if(pageCondition.getFoodType() != null && pageCondition.getFoodType().length == 0){
            pageCondition.setFoodType(null);
        }

        int pageSize = 8;
        int totalCount = userService.getFoodsCount(pageCondition);

        List<Food> foods = userService.getPageFood((pageCondition.getCurrentPage() - 1) * pageSize, pageSize, pageCondition);

        PageFoodVo foodVo = new PageFoodVo(foods, pageCondition.getCurrentPage(), totalCount, pageSize);

        return new ResponseEntity(foodVo, HttpStatus.OK);
    }


    @RequestMapping("/getFoodForPushFood")
    public ResponseEntity getFoodForPushFood(int foodId) {
        Food food = userService.findFoodById(foodId);
        return new ResponseEntity(food, HttpStatus.OK);
    }

    @RequestMapping("/addDiet")
    public ResponseEntity addDiet(@RequestBody DietVo dietVo){
        System.out.println(JsonUtil.toJson(dietVo));
        return new ResponseEntity(true, HttpStatus.OK);
    }

}
