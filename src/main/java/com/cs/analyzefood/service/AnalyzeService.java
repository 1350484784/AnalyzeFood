package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.FoodLog;
import com.cs.analyzefood.entity.MealMade;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.entity.vo.analyze.ResultEachFoodVo;
import com.cs.analyzefood.entity.vo.analyze.ResultMicroelementVo;
import com.cs.analyzefood.entity.vo.analyze.WeekAnalyzeVo;

import java.util.Date;
import java.util.List;

public interface AnalyzeService {

    double countRecommendEnergy(User user);

    double countPracticalEnergy(double dayCHO, double dayProtein, double dayFat);

    double countDayEnergy(List<MealMade> mealMades, int mealType);

    ResultEachFoodVo countEachFood(List<MealMade> mealMades);

    ResultMicroelementVo countMicroelement(List<MealMade> mealMades);

    WeekAnalyzeVo WeekAnalyze(User user);

    void insertStatistics(int mealId, int roleId, Date createTime);

    void insertFoodJob(int mealId, int roleId, Date createTime);

    void insertFoodLog(FoodLog foodLog);

    FoodLog findLogByIdsInDay(int roleId, int foodId, int type, Date date);

    void updateFoodLogNum(FoodLog foodLog);

    void updateFoodJobWeight(FoodLog foodLog);

    boolean estimate(double[] x, double[] dou, int i);

    List<Food> recommendFood(int roleId, int type);

    List<Food> recommendFoodByUserAll(int type);
}
