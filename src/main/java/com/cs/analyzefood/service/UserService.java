package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.diet.DietVo;
import com.cs.analyzefood.entity.vo.download.DownLoadFoodVo;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;
import com.cs.analyzefood.entity.vo.pageFood.PageCondition;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface UserService {
    User findUserByPhone(String phone);

    int addNewUser(String phone,String password);

    User findUserById(int roleId);

    User findUserByPhoneAndPwd(String phone,String password);

    boolean updateUserOnlineFlag(int roleId,byte onlineFlag);

    boolean updateUserPwd(String phone,String password);

    boolean updateUserSelf(User user);

    String uploadUserHeadImg(int roleId, String imgName);

//    PageInfo<Food> getAllfood(int currentPage);

    int getFoodsCount(PageCondition pageCondition);

//    List<Food> getPageFood(int begin,int count);

    List<Food> getPageFood(int begin,int count,PageCondition pageCondition);


    Food findFoodById(int foodId);

    int addNewMeal(Meal meal);

    void addMealMade(List<MealMade> mealMades);

    PageInfo<Meal> getPageMeal(int roleId, int currentPage);

    int getMealSum(int roleId);

    Meal findTodayDiet(int roleId);

    Meal findMealById(int mealId);

    void updateMealById(Meal meal);

    MealMade findMealMade(int mealId, int foodId, int mealType);

    void updateMeal(DietVo dietVo,int roleId);

    void insertDownloadFood(DownLoadFoodVo downLoadFoodVo);

    Food findFoodByName(String name);

    int getArticleSum(int roleId);

    List<Meal> getAllMealsInMonth();

    List<FoodLog> getAllLogsInMonth();

}
