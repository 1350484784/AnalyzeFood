package com.cs.analyzefood.service;

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
}
