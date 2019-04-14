package com.cs.analyzefood.job;

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
public class StatisticsJob implements Job {

    @Autowired
    private UserService userService;

    @Autowired
    private AnalyzeService analyzeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Meal> meals = userService.getAllMealsInMonth();
        for (Meal meal : meals) {
           analyzeService.insertStatistics(meal.getMealId(), meal.getRoleId(), meal.getCreateTime());
        }
    }
}
