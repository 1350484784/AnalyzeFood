package com.cs.analyzefood.control.manage;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.vo.manage.LayuiTableVo;
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
import java.util.Date;
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
    @ResponseBody
    public ResponseEntity allFood(int page, int limit){
        System.out.println(page+","+limit);
        List<Food> foods = manageService.getAllFood();
        List<Food> pageFood = manageService.getPageFood(page,limit);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(foods.size());
        vo.setMsg("");
        vo.setData(pageFood);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/addFood")
    @ResponseBody
    public ResponseEntity addFood(Food food){
        int result = manageService.addNewFood(food);
        if(result <= 0){
            return new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/delOneFood")
    @ResponseBody
    public ResponseEntity addFood(Integer foodId){
        if(foodId == null){
            return new ResponseEntity(false, HttpStatus.OK);
        }
        boolean flag =  manageService.delOneFood(foodId);
        if(!flag){
            return new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }
}
