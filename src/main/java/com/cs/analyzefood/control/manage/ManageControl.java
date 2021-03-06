package com.cs.analyzefood.control.manage;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.manage.*;
import com.cs.analyzefood.exception.SystemFailedException;
import com.cs.analyzefood.service.AdminService;
import com.cs.analyzefood.service.ArticleService;
import com.cs.analyzefood.service.InformService;
import com.cs.analyzefood.service.ManageService;
import com.cs.analyzefood.util.InformUtil;
import com.cs.analyzefood.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class ManageControl {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private InformService informService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            adminService.updateAdminOnline(admin.getAdminId(), (byte) 0);
            sessionStatus.setComplete();//将session移除
        }
        return "/html/login";
    }

    @RequestMapping("/systemInfo")
    @ResponseBody
    public ResponseEntity systemInfo(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            throw new SystemFailedException("admin do not login");
        }
        SystemInfoVo systemInfoVo = adminService.findSystemInfo(admin.getAdminAccount(), admin.getPassword());

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

    @RequestMapping("/allUser")
    @ResponseBody
    public ResponseEntity allUser(int page, int limit) {
        List<User> users = manageService.getAllUser();
        List<User> pageUser = manageService.getPageUser(page, limit);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(users.size());
        vo.setMsg("");
        vo.setData(pageUser);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/searchUser")
    @ResponseBody
    public ResponseEntity searchUser(String searchData){
        List<User> users = manageService.searchUser(searchData);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(users.size());
        vo.setMsg("");
        vo.setData(users);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/allArticle")
    @ResponseBody
    public ResponseEntity allArticle(int page, int limit) {
        List<Article> articles = manageService.getAllArticle();
        List<TableArticle> pageArticles = manageService.getPageArticle(page, limit);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(articles.size());
        vo.setMsg("");
        vo.setData(pageArticles);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/searchArticle")
    @ResponseBody
    public ResponseEntity searchArticle(String searchData){
        List<TableArticle> articles = manageService.searchArticle(searchData);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(articles.size());
        vo.setMsg("");
        vo.setData(articles);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/allReport")
    @ResponseBody
    public ResponseEntity allReport(int page, int limit){
        List<ArticleReport> reports = manageService.getAllReport();
        List<TableReport> pageReport = manageService.getPageReport(page, limit);
        LayuiTableVo vo = new LayuiTableVo();
        vo.setCode(0);
        vo.setCount(reports.size());
        vo.setMsg("");
        vo.setData(pageReport);
        return new ResponseEntity(vo, HttpStatus.OK);
    }

    @RequestMapping("/delOneReport")
    @ResponseBody
    @Transactional
    public ResponseEntity delOneReport(TableReport tableReport) {
        if (tableReport.getStatus() == 2){
            //审核通过，文章不显示
            String content = InformUtil.INFORM_TYPE_3_SUCCESS +"《"+ tableReport.getTitle() + "》该文章已处理";
            InformEvent informEvent = new InformEvent(3,content,tableReport.getRoleId());
            informService.addInform(informEvent);
            content = InformUtil.INFORM_TYPE_3_SUCCESS +"《"+ tableReport.getTitle() + "》该文章已处理,被举报！！！";
            informEvent = new InformEvent(3,content,tableReport.getAuthorId());
            informService.addInform(informEvent);
            articleService.delArticleById(tableReport.getArticleId());
        }
        if (tableReport.getStatus() == 0){
            //审核未通过，文章显示
            String content = InformUtil.INFORM_TYPE_3_FAIL +"《"+ tableReport.getTitle() + "》该文章符合要求，举报无效";
            InformEvent informEvent = new InformEvent(3,content,tableReport.getRoleId());
            informService.addInform(informEvent);
        }

        boolean flag = articleService.delOneReport(tableReport.getId());
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @RequestMapping("/delManyReport")
    @ResponseBody
    @Transactional
    public ResponseEntity delManyReport(@RequestBody RepoerFeedbackVo repoerFeedbackVo){
        if(repoerFeedbackVo.getId() != null && repoerFeedbackVo.getId().length > 0){
            for(int i = 0; i < repoerFeedbackVo.getId().length; i++){
                if(repoerFeedbackVo.getStatus()[i] == 2){
                    //审核通过，文章不显示
                    String content = InformUtil.INFORM_TYPE_3_SUCCESS +"《"+ repoerFeedbackVo.getTitle()[i] + "》该文章已处理";
                    InformEvent informEvent = new InformEvent(3, content, repoerFeedbackVo.getRoleId()[i]);
                    informService.addInform(informEvent);
                    content = InformUtil.INFORM_TYPE_3_SUCCESS +"《"+ repoerFeedbackVo.getTitle()[i] + "》该文章已处理,被举报！！！";
                    informEvent = new InformEvent(3, content, repoerFeedbackVo.getAuthorId()[i]);
                    informService.addInform(informEvent);
                    articleService.delArticleById(repoerFeedbackVo.getArticleId()[i]);
                }
                if(repoerFeedbackVo.getStatus()[i] == 0){
                    //审核未通过，文章显示
                    String content = InformUtil.INFORM_TYPE_3_FAIL +"《"+ repoerFeedbackVo.getTitle()[i] + "》该文章符合要求，举报无效";
                    InformEvent informEvent = new InformEvent(3, content, repoerFeedbackVo.getRoleId()[i]);
                    informService.addInform(informEvent);
                }
                articleService.delOneReport(repoerFeedbackVo.getId()[i]);
            }
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/changeSystemInfo")
    @ResponseBody
    public ResponseEntity changeSystemInfo(SystemInfoVo systemInfoVo){
        adminService.updateSystem(systemInfoVo);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/updateAdminOnline")
    @ResponseBody
    public ResponseEntity updateAdminOnline(HttpSession session,int flag){
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            throw new SystemFailedException("admin do not login");
        }
        adminService.updateAdminOnline(admin.getAdminId(), (byte) flag);
        return new ResponseEntity(true, HttpStatus.OK);
    }
}
