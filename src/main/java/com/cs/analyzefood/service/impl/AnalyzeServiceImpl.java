package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.MealMade;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.service.AnalyzeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {


    @Override
    public double countRecommendEnergy(User user) {
        String sex = "男";
        if(user.getSex() != null){
            sex = user.getSex();
        }

        int weight = 60;
        if(user.getWeight() > 0 && user.getWeight() < 500){
            weight = user.getWeight();
        }

        int height = 170;
        if(user.getHeight() > 0 && user.getHeight() < 300){
            height = user.getHeight();
        }

        int age = 30;
        if(user.getAge() > 0 && user.getAge() < 300){
            age = user.getAge();
        }

        return sex.equals("男") ? countRecommendEnergyForMan(age, weight, height): countRecommendEnergyForWoMan(age, weight, height);
    }

    private double countRecommendEnergyForMan(int age, int weight, int height){
        return 66 + 13.7 * weight + 5 * height - 6.8 * age;
    }

    private double countRecommendEnergyForWoMan(int age, int weight, int height){
        return 65 + 9.6 * weight + 1.7 * height - 4.7 * age;
    }

    @Override
    public double countPracticalEnergy(double dayCHO, double dayProtein, double dayFat) {
        return dayProtein * 4 + dayCHO * 4 + dayFat * 9;
    }

    @Override
    public double countDayEnergy(List<MealMade> mealMades, int mealType) {
        double dayEnergy = 0;
        for (MealMade mealMade : mealMades) {
            if(mealMade.getMealType() == mealType){
                dayEnergy += mealMade.getFood().getEnergy();
            }
        }
        return dayEnergy;
    }


}
