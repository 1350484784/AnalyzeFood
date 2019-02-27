package com.cs.analyzefood.control.manage;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.vo.manage.SystemInfoVo;
import com.cs.analyzefood.exception.SystemFailedException;
import com.cs.analyzefood.service.ManageService;
import com.cs.analyzefood.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManageControl {

    @Autowired
    private ManageService manageService;


    @RequestMapping("/systemInfo")
    @ResponseBody
    public ResponseEntity systemInfo(HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null){
            throw new SystemFailedException("admin do not login");
        }
        SystemInfoVo systemInfoVo = new SystemInfoVo(admin.getAdminAccount(),admin.getAuthor(),admin.getProjectName(),admin.getVersion(),admin.getDescription(),admin.getHomePage());

        return new ResponseEntity(systemInfoVo, HttpStatus.OK);
    }

    @RequestMapping("/allFood")
    public ResponseEntity allFood(){
        List<Food> foods = manageService.getAllFood();
        System.out.println(JsonUtil.toJson(foods));
        return new ResponseEntity(foods, HttpStatus.OK);
    }
}