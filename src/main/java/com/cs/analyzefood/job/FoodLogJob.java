package com.cs.analyzefood.job;

import com.cs.analyzefood.entity.FoodLog;
import com.cs.analyzefood.entity.Meal;
import com.cs.analyzefood.service.AnalyzeService;
import com.cs.analyzefood.service.UserService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DisallowConcurrentExecution
public class FoodLogJob implements Job {
    @Autowired
    private UserService userService;

    @Autowired
    private AnalyzeService analyzeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("FoodLogJob");
        List<Meal> meals = userService.getAllMealsInMonth();
        for (Meal meal : meals) {
            analyzeService.insertFoodJob(meal.getMealId(), meal.getRoleId(), meal.getCreateTime());
        }

        List<FoodLog> foodLogs = userService.getAllLogsInMonth();

        for (FoodLog foodLog : foodLogs) {
            analyzeService.updateFoodJobWeight(foodLog);
        }
        
    }
}
