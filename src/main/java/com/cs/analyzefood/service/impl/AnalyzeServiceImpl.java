package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.analyze.ResultEachFoodVo;
import com.cs.analyzefood.entity.vo.analyze.ResultMicroelementVo;
import com.cs.analyzefood.entity.vo.analyze.WeekAnalyzeVo;
import com.cs.analyzefood.mapper.UserMapper;
import com.cs.analyzefood.service.AnalyzeService;
import com.cs.analyzefood.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public WeekAnalyzeVo WeekAnalyze(User user) {
        WeekAnalyzeVo weekAnalyzeVo = null;
        Date[] dates = new Date[7];
        double[] weekEnergy = new double[7];
        double[] weekCHO = new double[7];
        double[] weekProtein = new double[7];
        double[] weekFat = new double[7];
        List<Meal> weekMeal = userMapper.getWeekMeal(user.getRoleId());
        int i = 0;
        int isReach = 0;
        if(CollectionUtils.isNotEmpty(weekMeal)){
            for (Meal meal : weekMeal) {
                dates[i] = meal.getCreateTime();
                Meal thisMeal = userMapper.selectMealById(meal.getMealId());
                List<MealMade> mealMades = userMapper.selectMealMadeByMealId(thisMeal.getMealId());
                for (MealMade mealMade : mealMades) {
                    Food food = userMapper.selectFoodById(mealMade.getFoodId());
                    mealMade.setFood(food);
                }
                thisMeal.setMealMades(mealMades);
                double recommendEnergy = countRecommendEnergy(user);
                double practicalEnergy = countPracticalEnergy(thisMeal.getDayCHO(), thisMeal.getDayProtein(),thisMeal.getDayFat());
                if(practicalEnergy > recommendEnergy){
                    isReach ++;
                }
                weekEnergy[i] = NumberUtil.formatDouble(recommendEnergy - practicalEnergy);
                weekCHO[i] = NumberUtil.formatDouble(recommendEnergy * thisMeal.getPer_carbohydrate()/100 - thisMeal.getDayCHO());
                weekProtein[i] = NumberUtil.formatDouble(recommendEnergy * thisMeal.getPer_protein()/100 - thisMeal.getDayProtein());
                weekFat[i] = NumberUtil.formatDouble(recommendEnergy * thisMeal.getPer_fat()/100 - thisMeal.getDayFat());
                i++;
            }
            weekAnalyzeVo = new WeekAnalyzeVo(weekEnergy, weekCHO, weekProtein, weekFat, isReach > 3);
            weekAnalyzeVo.setCreateTime(dates);
        }
        return weekAnalyzeVo;
    }

    @Override
    public void insertStatistics(int mealId, int roleId, Date createTime) {
        double gu = 0,dou = 0,shu = 0,guo = 0,rou = 0,nai = 0,dan = 0,yu = 0,you = 0;
        List<MealMade> mealMades = userMapper.selectMealMadeByMealId(mealId);
        for (MealMade mealMade : mealMades) {
            Food food = userMapper.selectFoodById(mealMade.getFoodId());
            if(food.getTypeId() == 11){
                gu += mealMade.getNum();
            }
            if(food.getTypeId() == 21){
                dou += mealMade.getNum();
            }
            if(food.getTypeId() == 31){
                shu += mealMade.getNum();
            }
            if(food.getTypeId() == 41){
                guo += mealMade.getNum();
            }
            if(food.getTypeId() == 51){
                rou += mealMade.getNum();
            }
            if(food.getTypeId() == 53){
                nai += mealMade.getNum();
            }
            if(food.getTypeId() == 54){
                dan += mealMade.getNum();
            }
            if(food.getTypeId() == 61){
                yu += mealMade.getNum();
            }
            if(food.getTypeId() == 81){
                you += mealMade.getNum();
            }
        }
        Statistics statistics = userMapper.selectStatisticsByTime(createTime);
        if(statistics == null){
            Statistics createStat = new Statistics(roleId, NumberUtil.formatDouble(250 - gu), NumberUtil.formatDouble(25 - dou), NumberUtil.formatDouble(300 - shu), NumberUtil.formatDouble(200 - guo), NumberUtil.formatDouble(40 - rou), NumberUtil.formatDouble(250 - nai), NumberUtil.formatDouble(25 - dan), NumberUtil.formatDouble(50 - yu),NumberUtil.formatDouble(25 - you),createTime);
            userMapper.insertStatistics(createStat);
        }else{
            Statistics createStat = new Statistics(statistics.getId() ,roleId, NumberUtil.formatDouble(250 - gu), NumberUtil.formatDouble(25 - dou), NumberUtil.formatDouble(300 - shu), NumberUtil.formatDouble(200 - guo), NumberUtil.formatDouble(40 - rou), NumberUtil.formatDouble(250 - nai), NumberUtil.formatDouble(25 - dan), NumberUtil.formatDouble(50 - yu),NumberUtil.formatDouble(25 - you),createTime);
            userMapper.updateStatistics(createStat);
        }
    }

    @Override
    public void insertFoodJob(int mealId, int roleId, Date createTime) {
        List<MealMade> mealMades = userMapper.selectMealMadeByMealId(mealId);
        Map<Integer, Integer> foodMap = new HashMap<>();
        for (MealMade mealMade : mealMades) {
            Integer foodIdNum = foodMap.get(mealMade.getFoodId());
            foodIdNum = foodIdNum == null ? 1 : foodIdNum + 1;
            foodMap.put(mealMade.getFoodId(), foodIdNum);
        }

        for (Map.Entry<Integer, Integer> entry : foodMap.entrySet()) {
            
        }
    }


}
