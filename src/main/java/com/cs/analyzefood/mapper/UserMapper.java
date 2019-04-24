package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.download.DownLoadFoodVo;

import java.util.Date;
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

    int selectMealNumByUser(int roleId);

    List<Meal> selectPageMeal(int roleId);

    List<MealMade> selectMealMadeByMealId(int mealId);

//    Food selectFoodByMeal(int foodId);

    Meal selectMealIsToday(int roleId);

    Meal selectMealById(int mealId);

    void updateMealById(Meal meal);

    MealMade selectMealMadeByMealIdAndFoodId(int mealId, int foodId, int mealType);

    void updateMealMade(MealMade mealMade);

    List<MealMade> selectOldMealMade(int mealId, int mealType);

    void deleteMealMade(int id);

    void insertFood(DownLoadFoodVo downLoadFoodVo);

    Food selectFoodByName(String name);

    int selectArticleSum(int roleId);

    List<Meal> getWeekMeal(int roleId);

    List<Meal> selectMealInMonth();

    Statistics selectStatisticsByTime(Date createTime);

    void insertStatistics(Statistics createStat);

    void updateStatistics(Statistics createStat);

    void insertFoodLog(FoodLog foodLog);

    FoodLog findLogByIdsInDay(int roleId, int foodId, int type, Date date);

    void updateFoodLogNum(FoodLog foodLog);

    List<FoodLog> selectLogsInMonth();

    Double selectFJobWeightMinInMonth(int roleId);

    Double selectFJobWeightMaxInMonth(int roleId);

    int selectFJobOneNumByUser(int foodId, int type, int userId);

    int selectFJobAllNumByUser(int userId);

    int selectFJobAllNum();

    int selectFJob(int foodId, int type);

    void updateFoodLogWeight(int id, double weight);

    List<Statistics> selectStatisticByUserInMonth(int roleId);

    Admin selectOnlineAdmin();

    List<Food> selectLosByUserType(int roleId, int type);

    List<Food> selectLosByType(int type);


//    int selectFoodNumByFoodIds(int[] foodIds);
}
