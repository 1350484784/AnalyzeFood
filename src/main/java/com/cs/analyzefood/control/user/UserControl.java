package com.cs.analyzefood.control.user;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.analyze.ResultEachFoodVo;
import com.cs.analyzefood.entity.vo.analyze.ResultMicroelementVo;
import com.cs.analyzefood.entity.vo.analyze.ResultVo;
import com.cs.analyzefood.entity.vo.analyze.WeekAnalyzeVo;
import com.cs.analyzefood.entity.vo.diet.DietVo;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;
import com.cs.analyzefood.entity.vo.pageFood.PageCondition;
import com.cs.analyzefood.entity.vo.pageFood.PageFoodVo;
import com.cs.analyzefood.exception.SystemFailedException;
import com.cs.analyzefood.service.*;
import com.cs.analyzefood.util.InformUtil;
import com.cs.analyzefood.util.JsonUtil;
import com.cs.analyzefood.util.NumberUtil;
import com.cs.analyzefood.util.SendMessageUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private InformService informService;

    @Autowired
    private AnalyzeService analyzeService;

    @Value("${user.headImg.path}")
    private String headImg_path;

    @Value("${user.bgImg.path}")
    private String bgImg_path;

    @Value("${article.path}")
    private String article_path;

    @Value("${article.moren}")
    private String article_moren;

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

            //转发
            return "forward:/to/userIndex";
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
                logger.debug(phone + " register success");
//                model.addAttribute("userId", roleId);
                UserZone userZone = userZoneService.selectUserZone(currentUser.getRoleId());
                /**
                 * 用户首页
                 */
                model.addAttribute("user", currentUser);
                model.addAttribute("userZone", userZone);

                //转发
                return "forward:/to/userIndex";
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
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity getFoodForPushFood(int foodId) {
        Food food = userService.findFoodById(foodId);
        return new ResponseEntity(food, HttpStatus.OK);
    }

    @RequestMapping("/addDiet")
    @ResponseBody
    public ResponseEntity addDiet(@RequestBody DietVo dietVo,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        Meal meal = new Meal(user.getRoleId(),dietVo.getDietTitle(),dietVo.getTargetEnergy(),dietVo.getIntroduce(),new Date(),dietVo.getPer_carbohydrate(),dietVo.getPer_protein(),dietVo.getPer_fat(),dietVo.getPer_zao(),dietVo.getPer_zhong(),dietVo.getPer_wan(),dietVo.getDayEnergy(),dietVo.getDayCHO(),dietVo.getDayProtein(),dietVo.getDayFat());
        int mealId = userService.addNewMeal(meal);
        if(mealId <= 0){
            throw new SystemFailedException("add meal failed");
        }
        List<MealMade> mealMades = new ArrayList<>();
        if(dietVo.getFoodId0() != null && dietVo.getFoodId0().length != 0){
            for(int i = 0; i < dietVo.getFoodId0().length; i++){
                MealMade mealMade = new MealMade(mealId,dietVo.getFoodId0()[i],dietVo.getFoodNum0()[i],0);
                mealMades.add(mealMade);
            }
        }
        if(dietVo.getFoodId1() != null && dietVo.getFoodId1().length != 0){
            for(int i = 0; i < dietVo.getFoodId1().length; i++){
                MealMade mealMade = new MealMade(mealId,dietVo.getFoodId1()[i],dietVo.getFoodNum1()[i],1);
                mealMades.add(mealMade);
            }
        }
        if(dietVo.getFoodId2() != null && dietVo.getFoodId2().length != 0){
            for(int i = 0; i < dietVo.getFoodId2().length; i++){
                MealMade mealMade = new MealMade(mealId,dietVo.getFoodId2()[i],dietVo.getFoodNum2()[i],2);
                mealMades.add(mealMade);
            }
        }
        if(mealMades.size() == 0){
            throw new SystemFailedException("add meal failed");
        }
        userService.addMealMade(mealMades);
        return new ResponseEntity(true, HttpStatus.OK);
    }


    @RequestMapping("/findTodayDiet")
    @ResponseBody
    public ResponseEntity findTodayDiet(HttpSession session){
        User user = (User) session.getAttribute("user");
        Meal meal = userService.findTodayDiet(user.getRoleId());
        if(meal != null){
            return new ResponseEntity(true, HttpStatus.OK);
        }
        return new ResponseEntity(false, HttpStatus.OK);
    }

    @RequestMapping("/toEditDiet")
    public String toEditDiet(int mealId, Model model){
        Meal meal = userService.findMealById(mealId);
        model.addAttribute("meal", meal);
        return "/html/user/editDietDetail";
    }

    @RequestMapping("/editDiet")
    @ResponseBody
    public ResponseEntity editDiet(@RequestBody DietVo dietVo,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        if(dietVo.getMealId() <= 0){
            throw new SystemFailedException("meal do not exist");
        }
        userService.updateMeal(dietVo,user.getRoleId());

        return new ResponseEntity(true, HttpStatus.OK);
    }


    /**
     * 分析页面
     * @param mealId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/toShowDiet")
    public String toShowDiet(int mealId, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        Meal meal = userService.findMealById(mealId);
        int sum = userService.getMealSum(user.getRoleId());
        int articleSum = userService.getArticleSum(user.getRoleId());
        model.addAttribute("meal", meal);
        model.addAttribute("sum", sum);
        model.addAttribute("articleSum", articleSum);

        double recommendEnergy = analyzeService.countRecommendEnergy(user);
        double practicalEnergy = analyzeService.countPracticalEnergy(meal.getDayCHO(), meal.getDayProtein(),meal.getDayFat());
        double dayZao = analyzeService.countDayEnergy(meal.getMealMades(), 0);
        double dayZhong = analyzeService.countDayEnergy(meal.getMealMades(), 1);
        double dayWan = analyzeService.countDayEnergy(meal.getMealMades(), 2);
        double dayCHOPer = NumberUtil.formatDouble(meal.getDayCHO()*1.0*4 / practicalEnergy);
        double dayProteinPer = NumberUtil.formatDouble(meal.getDayProtein()*1.0*4 / practicalEnergy);
        double dayFatPer = NumberUtil.formatDouble(meal.getDayFat()*1.0*9 / practicalEnergy);
        ResultEachFoodVo eachFoodVo = analyzeService.countEachFood(meal.getMealMades());
        ResultMicroelementVo microelementVo = analyzeService.countMicroelement(meal.getMealMades());
        ResultVo resultVo = new ResultVo(recommendEnergy, practicalEnergy, dayZao, dayZhong, dayWan, dayCHOPer, dayProteinPer, dayFatPer);
        resultVo.setEachFoodVo(eachFoodVo);
        resultVo.setMicroelementVo(microelementVo);
        model.addAttribute("resultVo", resultVo);

        return "/html/user/showDietDetail";
    }

    @RequestMapping("/foodListPage")
    @ResponseBody
    public ResponseEntity foodListPage(@RequestBody PageCondition pageCondition){
        if(pageCondition == null){
            pageCondition = new PageCondition(1);
        }
        if(pageCondition.getFoodType() != null && pageCondition.getFoodType().length == 0){
            pageCondition.setFoodType(null);
        }
        int pageSize = 20;
        int totalCount = userService.getFoodsCount(pageCondition);

        List<Food> foods = userService.getPageFood((pageCondition.getCurrentPage() - 1) * pageSize, pageSize, pageCondition);

        PageFoodVo foodVo = new PageFoodVo(foods, pageCondition.getCurrentPage(), totalCount, pageSize);

        return new ResponseEntity(foodVo, HttpStatus.OK);
    }

    @RequestMapping("/foodListGetFood")
    @ResponseBody
    public ResponseEntity foodListGetFood(int foodId){
        Food food = userService.findFoodById(foodId);
        return new ResponseEntity(food, HttpStatus.OK);
    }

    @RequestMapping("/addArticleByUser")
    public String addArticleByUser(@RequestParam("pic_file") MultipartFile file,Article article, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }

        String picName;
        if(file != null && file.getOriginalFilename().equals("")){
            picName =  UUID.randomUUID().toString().replace("-", "") + "_" + article_moren;
        }else{
            String filename = file.getOriginalFilename();
            picName = UUID.randomUUID().toString().replace("-", "") + "_" + filename;
            File newFile = new File(article_path + picName);
            if (!newFile.exists()) {
                try {
                    file.transferTo(newFile);
                } catch (IOException e) {
                    logger.debug(e.getMessage());
                }
            }
        }

        Article newArticle = new Article(user.getRoleId(),article.getTitle(),article.getContent(),article.getTypeId(),article_path+picName);
        int articleId = articleService.addNewArticle(newArticle);
        if(articleId <= 0){
            throw new SystemFailedException("add article failed");
        }

        return "forward:/to/articleIndex";
    }

    @RequestMapping("/articlePage")
    @ResponseBody
    public ResponseEntity articlePage(@RequestBody PageArticleCondition pageArticleCondition){
        if(pageArticleCondition == null){
            pageArticleCondition = new PageArticleCondition(1);
        }
        PageInfo<Article> pageInfo = articleService.getPageArticle(pageArticleCondition);
        return new ResponseEntity(pageInfo, HttpStatus.OK);
    }


    @RequestMapping("/commentArticle")
    @Transactional
    @ResponseBody
    public ResponseEntity commentArticle(ArticleEvaluate articleEvaluate){
        articleService.addComment(articleEvaluate);
        User commentUser = userService.findUserById(articleEvaluate.getRoleId());
        User author = articleService.selectUserByArticleId(articleEvaluate.getArticleId());
        String content = InformUtil.INFORM_TYPE_4_EVALUATE + "您的文章被" +commentUser.getRoleAccount()+ "评论，评论的内容为:" + articleEvaluate.getContent();
        InformEvent informEvent = new InformEvent(4,content,author.getRoleId());
        informService.addInform(informEvent);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/commentSearch")
    @ResponseBody
    public ResponseEntity commentSearch(int articleId){
        List<ArticleEvaluate> articleEvaluates = articleService.findArticleEvaluate(articleId);
        for (ArticleEvaluate articleEvaluate : articleEvaluates) {
            User userEvaluate = userService.findUserById(articleEvaluate.getRoleId());
            articleEvaluate.setUser(userEvaluate);

            List<ArticleReply> articleReplies = articleEvaluate.getArticleReplies();
            for (ArticleReply articleReply : articleReplies) {
                User fromUser = userService.findUserById(articleReply.getFromRoleId());
                User toUser = userService.findUserById(articleReply.getToRoleId());
                articleReply.setFromUser(fromUser);
                articleReply.setToUser(toUser);
            }
        }
        return new ResponseEntity(articleEvaluates, HttpStatus.OK);
    }

    @RequestMapping("/reply_evaluate")
    @Transactional
    @ResponseBody
    public ResponseEntity reply_evaluate(ArticleReply articleReply){
        articleService.addReply(articleReply);
        User evaluateUser = userService.findUserById(articleReply.getFromRoleId());
        User toUser = userService.findUserById(articleReply.getToRoleId());
        String content = InformUtil.INFORM_TYPE_4_REPLY + "您的评论被" + evaluateUser.getRoleAccount()+ "回复，回复内容为:" + articleReply.getContent();
        InformEvent informEvent = new InformEvent(4,content,toUser.getRoleId());
        informService.addInform(informEvent);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping("/reportArticle")
    @Transactional
    @ResponseBody
    public ResponseEntity reportArticle(ArticleReport articleReport){
        User reportUser = userService.findUserById(articleReport.getRoleId());
        Article article = articleService.findArticleById(articleReport.getArticleId());
        String content = InformUtil.INFORM_TYPE_2_REPORT + "《"+ article.getTitle() + "》该文章正在审核";
        InformEvent informEvent = new InformEvent(2,content,reportUser.getRoleId());
        informService.addInform(informEvent);
        articleService.addReport(articleReport);
        return new ResponseEntity(true, HttpStatus.OK);
    }


    @RequestMapping("/isAdminOnline")
    @ResponseBody
    public ResponseEntity isAdminOnline(HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return new ResponseEntity(false, HttpStatus.OK);
        }
        if(admin.getOnlineFlag() == (byte) 0){
            return new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(admin.getAdminAccount(), HttpStatus.OK);
    }

    @RequestMapping("/weekAnalyze")
    @ResponseBody
    public ResponseEntity weekAnalyze(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        WeekAnalyzeVo weekAnalyzeVo = analyzeService.WeekAnalyze(user);
        return new ResponseEntity(weekAnalyzeVo, HttpStatus.OK);
    }

    @RequestMapping("/getUnReadMsg")
    @ResponseBody
    public ResponseEntity getUnReadMsg(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new SystemFailedException("user do not login");
        }
        List<InformEvent> events = informService.getInformEvent(user.getRoleId());
        return new ResponseEntity(events, HttpStatus.OK);
    }

    @RequestMapping("/updateInformStatus")
    @ResponseBody
    public ResponseEntity updateInformStatus(int id){
        informService.updateInformStatus(id);
        return new ResponseEntity(true, HttpStatus.OK);
    }
}
