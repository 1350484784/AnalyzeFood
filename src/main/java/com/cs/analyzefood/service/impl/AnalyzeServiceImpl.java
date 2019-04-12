package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.MealMade;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.entity.vo.analyze.ResultEachFoodVo;
import com.cs.analyzefood.entity.vo.analyze.ResultMicroelementVo;
import com.cs.analyzefood.service.AnalyzeService;
import com.cs.analyzefood.util.NumberUtil;
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

    @Override
    public ResultEachFoodVo countEachFood(List<MealMade> mealMades) {
        double gu = 0,dou = 0,shu = 0,guo = 0,rou = 0,nai = 0,dan = 0,yu = 0,you = 0,other = 0;
        for (MealMade mealMade : mealMades) {
            if(mealMade.getFood().getTypeId() == 11){
                gu += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 21){
                dou += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 31){
                shu += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 41){
                guo += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 51){
                rou += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 53){
                nai += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 54){
                dan += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 61){
                yu += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 81){
                you += mealMade.getNum();
            }
            if(mealMade.getFood().getTypeId() == 82 || mealMade.getFood().getTypeId() == 83){
                other += mealMade.getNum();
            }
        }
        return new ResultEachFoodVo(gu, dou, shu, guo, rou, nai, dan, yu, you, other);
    }

    @Override
    public ResultMicroelementVo countMicroelement(List<MealMade> mealMades) {
        double day_fiber=0,day_va=0,day_vb1=0, day_vb2=0,day_niacin=0,day_ve=0,day_na=0,day_ca=0,day_fe=0,day_vc=0,day_cholesterol=0;
        for (MealMade mealMade : mealMades) {
            day_fiber += mealMade.getFood().getFiber();
            day_va += mealMade.getFood().getVa();
            day_vb1 += mealMade.getFood().getVb1();
            day_vb2 += mealMade.getFood().getVb2();
            day_niacin += mealMade.getFood().getNiacin();
            day_ve += mealMade.getFood().getVe();
            day_na += mealMade.getFood().getNa();
            day_ca += mealMade.getFood().getCa();
            day_fe += mealMade.getFood().getFe();
            day_vc += mealMade.getFood().getVc();
            day_cholesterol += mealMade.getFood().getCholesterol();
        }
        return new ResultMicroelementVo(NumberUtil.formatDouble(day_fiber),NumberUtil.formatDouble(day_va),
                NumberUtil.formatDouble(day_vb1), NumberUtil.formatDouble(day_vb2),NumberUtil.formatDouble(day_niacin),
                NumberUtil.formatDouble(day_ve),NumberUtil.formatDouble(day_na),NumberUtil.formatDouble(day_ca),
                NumberUtil.formatDouble(day_fe),NumberUtil.formatDouble(day_vc),NumberUtil.formatDouble(day_cholesterol));
    }


}
