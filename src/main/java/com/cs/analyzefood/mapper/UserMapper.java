package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.Meal;
import com.cs.analyzefood.entity.MealMade;
import com.cs.analyzefood.entity.User;

import java.util.List;

public interface UserMapper {
    User selectUserByPhone(String phone);

    int insertUser(User user);

    User selectUserById(int roleId);

    User selectUserByPhoneAndPwd(String phone,String password);

    boolean updateUserOnlineFlag(int roleId,byte onlineFlag);

    boolean updateUserPwd(String phone, String password);

    boolean updateUserById(User user);

    boolean updateUserHeadImgById(String imgName, int roleId);

    List<Food> selectAllFoods();

    int selectFoodNum(int[] foodIds, String foodName,Double min, Double max);

    List<Food> selectFoodPage(int begin, int count, int[] foodIds, String foodName,Double min, Double max);

    Food selectFoodById(int foodId);

    int insertMeal(Meal meal);

    void insertMealMade(MealMade mealMade);

//    int selectFoodNumByFoodIds(int[] foodIds);
}
