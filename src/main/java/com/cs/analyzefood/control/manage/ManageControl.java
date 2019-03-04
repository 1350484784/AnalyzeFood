package com.cs.analyzefood.control.manage;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.FoodType;
import com.cs.analyzefood.entity.vo.manage.LayuiTableVo;
import com.cs.analyzefood.entity.vo.manage.SystemInfoVo;
import com.cs.analyzefood.exception.SystemFailedException;
import com.cs.analyzefood.service.ManageService;
import com.cs.analyzefood.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class ManageControl {

    @Autowired
    private ManageService manageService;


    @RequestMapping("/systemInfo")
    @ResponseBody
    public ResponseEntity systemInfo(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            throw new SystemFailedException("admin do not login");
        }
        SystemInfoVo systemInfoVo = new SystemInfoVo(admin.getAdminAccount(), admin.getAuthor(), admin.getProjectName(), admin.getVersion(), admin.getDescription(), admin.getHomePage());

        return new ResponseEntity(systemInfoVo, HttpStatus.OK);
    }


    @RequestMapping("/getFoodType")
    @ResponseBody
    public ResponseEntity getFoodType() {
        List<FoodType> foodTypes = manageService.getAllFoodType();
        if (CollectionUtils.isEmpty(foodTypes)) {
            new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(foodTypes, HttpStatus.OK);
    }

    @RequestMapping("/allFood")
    @ResponseBody
    public ResponseEntity allFood(int page, int limit) {
        List<Food> foods = manageService.getAllFood();
        List<Food> pageFood = manageService.getPageFood(page, limit);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(foods.size());
        vo.setMsg("");
        vo.setData(pageFood);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/addFood")
    @ResponseBody
    public ResponseEntity addFood(Food food) {
        int result = manageService.addNewFood(food);
        if (result <= 0) {
            return new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/editFood")
    public String editFood(int foodId, Model model) {
        Food food = manageService.getFoodById(foodId);
        model.addAttribute("food", food);
        Map<Integer, String> map = new HashMap<Integer, String>() {
            {
                put(11, "谷类");
                put(21, "豆类");
                put(31, "蔬类");
                put(41, "果类");
                put(51, "肉类");
                put(53, "乳类");
                put(54, "蛋类");
                put(61, "鱼类");
                put(81, "油类");
                put(82, "另类");
            }
        };
        model.addAttribute("categoryMap", map);
        return "/html/manage/editFood";
    }

    @RequestMapping("/editFoodById")
    @ResponseBody
    public ResponseEntity editFoodById(Food food){
        boolean flag = manageService.updateFoodById(food);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @RequestMapping("/delOneFood")
    @ResponseBody
    public ResponseEntity deleteOneFood(Integer foodId) {
        if (foodId == null) {
            return new ResponseEntity(false, HttpStatus.OK);
        }
        boolean flag = manageService.delOneFood(foodId);
        return new ResponseEntity(flag, HttpStatus.OK);
    }


    @RequestMapping("/delManyFood")
    @ResponseBody
    public ResponseEntity deleteManyFood(int[] delFoodList) {
        boolean flag = false;
        for (int i = 0; i < delFoodList.length; i++) {
            flag = manageService.delOneFood(delFoodList[i]);
            if (!flag) {
                return new ResponseEntity(false, HttpStatus.OK);
            }
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/exportOutFood")
    @ResponseBody
    public ResponseEntity exportOutFood() {
        List<Food> foods = manageService.getAllFood();
        if (CollectionUtils.isEmpty(foods)) {
            new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(foods, HttpStatus.OK);
    }

    @RequestMapping("/exportInFile")
    @ResponseBody
    public ResponseEntity exportInFile(MultipartFile impFile) {
        boolean flag = manageService.readExcelFile(impFile);
        return new ResponseEntity(flag, HttpStatus.OK);
    }


    @RequestMapping("/searchFood")
    @ResponseBody
    public ResponseEntity searchFood(String searchData){
        List<Food> foods = manageService.searchFood(searchData);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(foods.size());
        vo.setMsg("");
        vo.setData(foods);
        return new ResponseEntity(vo, HttpStatus.OK);
    }
}
