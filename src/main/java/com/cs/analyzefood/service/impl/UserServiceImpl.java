package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.diet.DietVo;
import com.cs.analyzefood.entity.vo.download.DownLoadFoodVo;
import com.cs.analyzefood.entity.vo.pageFood.PageCondition;
import com.cs.analyzefood.mapper.UserMapper;
import com.cs.analyzefood.mapper.UserZoneMapper;
import com.cs.analyzefood.service.UserService;
import com.cs.analyzefood.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserZoneMapper userZoneMapper;

    @Value("${user.headImg.moren}")
    private String headImg;

    @Value("${user.headImg.path}")
    private String headImg_path;

    @Value("${user.bgImg.moren}")
    private String bgImg;

    @Value("${user.bgImg.path}")
    private String bgImg_path;

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public int addNewUser(String phone, String password) {
        Date date = new Date();
        UUID id = UUID.randomUUID();
        String[] uuid = id.toString().split("-");
        String userAccount = uuid[0] + DateUtil.getSDate(date, "yyyyMMddhhmmss");

        String base64Pwd = Base64.getEncoder().encodeToString((password).getBytes());

        User newUser = new User(userAccount, base64Pwd, phone, headImg, date);
        int result = userMapper.insertUser(newUser);
        if (result > 0) {
            userZoneMapper.insertUserZone(newUser.getRoleId(), bgImg);
            return newUser.getRoleId();
        }
        return -1;
    }

    @Override
    public User findUserById(int roleId) {
        return userMapper.selectUserById(roleId);
    }

    @Override
    public User findUserByPhoneAndPwd(String phone, String password) {
        return userMapper.selectUserByPhoneAndPwd(phone, password);
    }

    @Override
    public boolean updateUserOnlineFlag(int roleId, byte onlineFlag) {
        return userMapper.updateUserOnlineFlag(roleId, onlineFlag);
    }

    @Override
    public boolean updateUserPwd(String phone, String password) {
        String base64Pwd = Base64.getEncoder().encodeToString((password).getBytes());
        return userMapper.updateUserPwd(phone, base64Pwd);
    }

    @Override
    public boolean updateUserSelf(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public String uploadUserHeadImg(int roleId, String imgName) {
        userMapper.updateUserHeadImgById(headImg_path + imgName, roleId);
        return headImg_path + imgName;
    }

    @Override
    public int getFoodsCount(PageCondition pageCondition) {
        return userMapper.selectFoodNum(pageCondition.getFoodType(),pageCondition.getFoodName(),pageCondition.getMin(),pageCondition.getMax());
    }

    @Override
    public List<Food> getPageFood(int begin,int count,PageCondition pageCondition) {
        return userMapper.selectFoodPage(begin, count, pageCondition.getFoodType(), pageCondition.getFoodName(), pageCondition.getMin(), pageCondition.getMax());
    }


    //    @Override
//    public PageInfo<Food> getAllfood(int currentPage) {
//        int count = userMapper.selectFoodNum();
//        PageHelper.startPage(currentPage,8);
//        List<Food> foods = userMapper.selectAllFoods();
//        PageInfo<Food> pageInfo = new PageInfo<>(foods);
//        //当前页
//        pageInfo.setPageNum(currentPage);
//        System.out.println(pageInfo.getPageNum());
//        //每页显示的条数
//        pageInfo.setPageSize(8);
//        //总条数
//        pageInfo.setTotal(count);
//        return pageInfo;
//    }


    @Override
    public Food findFoodById(int foodId) {
        return userMapper.selectFoodById(foodId);
    }

    @Override
    public int addNewMeal(Meal meal) {
        int result = userMapper.insertMeal(meal);
        if(result > 0){
            return meal.getMealId();
        }
        return -1;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void addMealMade(List<MealMade> mealMades) {
        for (MealMade mealMade : mealMades){
            userMapper.insertMealMade(mealMade);
        }
    }

    @Override
    public int getMealSum(int roleId) {
        return userMapper.selectMealNumByUser(roleId);
    }


    @Override
    public PageInfo<Meal> getPageMeal(int roleId, int currentPage) {
        int count = userMapper.selectMealNumByUser(roleId);
        PageHelper.startPage(currentPage,3);
        List<Meal> meals = userMapper.selectPageMeal(roleId);
        for(Meal meal : meals){
            List<MealMade> mealMades = userMapper.selectMealMadeByMealId(meal.getMealId());
            for (MealMade mealMade : mealMades) {
                Food food = userMapper.selectFoodById(mealMade.getFoodId());
                mealMade.setFood(food);
            }
            meal.setMealMades(mealMades);
        }
        PageInfo<Meal> pageInfo = new PageInfo<>(meals);
        //当前页
        pageInfo.setPageNum(currentPage);
        //每页显示的条数
        pageInfo.setPageSize(3);
        //总条数
        pageInfo.setTotal(count);
        return pageInfo;
    }


    @Override
    public Meal findTodayDiet(int roleId) {
        return userMapper.selectMealIsToday(roleId);
    }

    @Override
    public Meal findMealById(int mealId) {
        Meal meal = userMapper.selectMealById(mealId);
        List<MealMade> mealMades = userMapper.selectMealMadeByMealId(meal.getMealId());
        for (MealMade mealMade : mealMades) {
            Food food = userMapper.selectFoodById(mealMade.getFoodId());
            mealMade.setFood(food);
        }
        meal.setMealMades(mealMades);
        return meal;
    }

    @Override
    public void updateMealById(Meal meal) {
        userMapper.updateMealById(meal);
    }

    @Override
    public MealMade findMealMade(int mealId, int foodId,int mealType) {
        return userMapper.selectMealMadeByMealIdAndFoodId(mealId,foodId,mealType);
    }


    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void updateMeal(DietVo dietVo,int roleId) {
        Meal meal = new Meal(dietVo.getMealId(),roleId,dietVo.getDietTitle(),dietVo.getTargetEnergy(),dietVo.getIntroduce(),new Date(),dietVo.getPer_carbohydrate(),dietVo.getPer_protein(),dietVo.getPer_fat(),dietVo.getPer_zao(),dietVo.getPer_zhong(),dietVo.getPer_wan(),dietVo.getDayEnergy(),dietVo.getDayCHO(),dietVo.getDayProtein(),dietVo.getDayFat());
        updateMealById(meal);
        List<MealMade> zao = userMapper.selectOldMealMade(dietVo.getMealId(),0);
        for(MealMade oldMealMade : zao){
            boolean isdelete = false;
            if(dietVo.getFoodId0() != null && dietVo.getFoodId0().length != 0){
                for(int i = 0; i < dietVo.getFoodId0().length; i++){
                    if(dietVo.getFoodId0()[i] == oldMealMade.getFoodId()){
                        isdelete = true;
                    }
                }
            }
            if(!isdelete){
                userMapper.deleteMealMade(oldMealMade.getId());
            }
        }
        if(dietVo.getFoodId0() != null && dietVo.getFoodId0().length != 0){
            for(int i = 0; i < dietVo.getFoodId0().length; i++){
                MealMade mealMade = findMealMade(dietVo.getMealId(),dietVo.getFoodId0()[i],0);
                if(mealMade == null){
                    MealMade newMealMade = new MealMade(dietVo.getMealId(),dietVo.getFoodId0()[i],dietVo.getFoodNum0()[i],0);
                    userMapper.insertMealMade(newMealMade);
                }else{
                    MealMade newMealMade = new MealMade(dietVo.getMealId(),dietVo.getFoodId0()[i],dietVo.getFoodNum0()[i],0);
                    userMapper.updateMealMade(newMealMade);
                }
            }
        }

        List<MealMade> zhong = userMapper.selectOldMealMade(dietVo.getMealId(),1);
        for(MealMade oldMealMade : zhong){
            boolean isdelete = false;
            if(dietVo.getFoodId1() != null && dietVo.getFoodId1().length != 0){
                for(int i = 0; i < dietVo.getFoodId1().length; i++){
                    if(dietVo.getFoodId1()[i] == oldMealMade.getFoodId()){
                        isdelete = true;
                    }
                }
            }
            if(!isdelete){
                userMapper.deleteMealMade(oldMealMade.getId());
            }
        }
        if(dietVo.getFoodId1() != null && dietVo.getFoodId1().length != 0){
            for(int i = 0; i < dietVo.getFoodId1().length; i++){
                MealMade mealMade = findMealMade(dietVo.getMealId(),dietVo.getFoodId1()[i],1);
                if(mealMade == null){
                    MealMade newMealMade = new MealMade(dietVo.getMealId(),dietVo.getFoodId1()[i],dietVo.getFoodNum1()[i],1);
                    userMapper.insertMealMade(newMealMade);
                }else{
                    MealMade newMealMade = new MealMade(dietVo.getMealId(),dietVo.getFoodId1()[i],dietVo.getFoodNum1()[i],1);
                    userMapper.updateMealMade(newMealMade);
                }
            }
        }

        List<MealMade> wan = userMapper.selectOldMealMade(dietVo.getMealId(),2);
        for(MealMade oldMealMade : wan){
            boolean isdelete = false;
            if(dietVo.getFoodId2() != null && dietVo.getFoodId2().length != 0){
                for(int i = 0; i < dietVo.getFoodId2().length; i++){
                    if(dietVo.getFoodId2()[i] == oldMealMade.getFoodId()){
                        isdelete = true;
                    }
                }
            }
            if(!isdelete){
                userMapper.deleteMealMade(oldMealMade.getId());
            }
        }
        if(dietVo.getFoodId2() != null && dietVo.getFoodId2().length != 0){
            for(int i = 0; i < dietVo.getFoodId2().length; i++){
                MealMade mealMade = findMealMade(dietVo.getMealId(),dietVo.getFoodId2()[i],2);
                if(mealMade == null){
                    MealMade newMealMade = new MealMade(dietVo.getMealId(),dietVo.getFoodId2()[i],dietVo.getFoodNum2()[i],2);
                    userMapper.insertMealMade(newMealMade);
                }else{
                    MealMade newMealMade = new MealMade(dietVo.getMealId(),dietVo.getFoodId2()[i],dietVo.getFoodNum2()[i],2);
                    userMapper.updateMealMade(newMealMade);
                }
            }
        }

    }

    @Override
    public void insertDownloadFood(DownLoadFoodVo downLoadFoodVo) {
        userMapper.insertFood(downLoadFoodVo);
    }

    @Override
    public Food findFoodByName(String name) {
        return userMapper.selectFoodByName(name);
    }

    @Override
    public int getArticleSum(int roleId) {
        return userMapper.selectArticleSum(roleId);
    }

    @Override
    public List<Meal> getAllMealsInMonth() {
        return userMapper.selectMealInMonth();
    }

    @Override
    public List<FoodLog> getAllLogsInMonth() {
        return userMapper.selectLogsInMonth();
    }


}
