package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.FoodType;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.util.JsonUtil;
import com.cs.analyzefood.util.ReadExcel;
import com.cs.analyzefood.mapper.ManageMapper;
import com.cs.analyzefood.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ManageMapper manageMapper;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Food> getAllFood() {
        return manageMapper.selectFood();
    }

    @Override
    public List<Food> getPageFood(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return manageMapper.selectPageFood(start, pageSize);
    }

    @Override
    public int addNewFood(Food food) {

        int result = 0;
        try {
            result = manageMapper.insertFood(food);
        } catch (Exception e) {
            logger.debug("food name repeat");
        }
        if (result > 0) {
            return food.getFoodId();
        }
        return -1;
    }

    @Override
    public boolean delOneFood(int foodId) {
        return manageMapper.updateFoodFlag(foodId);
    }

    @Override
    public List<FoodType> getAllFoodType() {
        return manageMapper.selectFoodType();
    }

    @Override
    public Food getFoodById(int foodId) {
        return manageMapper.selectFoodByFoodId(foodId);
    }

    @Override
    public boolean updateFoodById(Food food) {
        return manageMapper.updateFoodById(food);
    }

    @Override
    public List<Food> searchFood(String searchData) {
        return manageMapper.selectFoodForSearch(searchData);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean readExcelFile(MultipartFile file) {
        DecimalFormat df = new DecimalFormat("0.00");
        ReadExcel readExcel = new ReadExcel();
        List<Map<String, Object>> foodList = readExcel.getExcelInfo(file);

        for (Map<String, Object> map : foodList) {
            Food food = new Food();
            food.setFoodName(map.get("foodName").toString());

            if(map.get("typeId") != null && !map.get("typeId").toString().equals("")){
                food.setTypeId(Integer.parseInt(map.get("typeId").toString()));
            }else {
                food.setTypeId(0);
            }
            if(map.get("eat_part") != null && !map.get("eat_part").toString().equals("")){
                food.setEat_part(Integer.parseInt(map.get("eat_part").toString()));
            }else{
                food.setEat_part(0);
            }
            if(map.get("energy") != null && !map.get("energy").toString().equals("")){
                food.setEnergy(Double.parseDouble(df.format(Double.parseDouble(map.get("energy").toString()))));
            }else{
                food.setEnergy(0);
            }
            if(map.get("moisture") !=null && !map.get("moisture").toString().equals("")){
                food.setMoisture(Double.parseDouble(df.format(Double.parseDouble(map.get("moisture").toString()))));
            }else{
                food.setMoisture(0);
            }
            if(map.get("protein") !=null && !map.get("protein").toString().equals("")){
                food.setProtein(Double.parseDouble(df.format(Double.parseDouble(map.get("protein").toString()))));
            }else{
                food.setProtein(0);
            }
            if(map.get("fat") !=null && !map.get("fat").toString().equals("")){
                food.setFat(Double.parseDouble(df.format(Double.parseDouble(map.get("fat").toString()))));
            }else {
                food.setFat(0);
            }
            if(map.get("fiber") !=null && !map.get("fiber").toString().equals("")){
                food.setFiber(Double.parseDouble(df.format(Double.parseDouble(map.get("fiber").toString()))));
            }else{
                food.setFiber(0);
            }
            if(map.get("carbohydrate") !=null && !map.get("carbohydrate").toString().equals("")){
                food.setCarbohydrate(Double.parseDouble(df.format(Double.parseDouble(map.get("carbohydrate").toString()))));
            }else{
                food.setCarbohydrate(0);
            }
            if(map.get("va") !=null && !map.get("va").toString().equals("")){
                food.setVa(Double.parseDouble(df.format(Double.parseDouble(map.get("va").toString()))));
            }else{
                food.setVa(0);
            }
            if(map.get("vb1") !=null && !map.get("vb1").toString().equals("")){
                food.setVb1(Double.parseDouble(df.format(Double.parseDouble(map.get("vb1").toString()))));
            }else {
                food.setVb1(0);
            }
            if(map.get("vb2") !=null && !map.get("vb2").toString().equals("")){
                food.setVb2(Double.parseDouble(df.format(Double.parseDouble(map.get("vb2").toString()))));
            }else{
                food.setVb2(0);
            }
            if(map.get("niacin") !=null && !map.get("niacin").toString().equals("")){
                food.setNiacin(Double.parseDouble(df.format(Double.parseDouble(map.get("niacin").toString()))));
            }else{
                food.setNiacin(0);
            }
            if(map.get("ve") !=null && !map.get("ve").toString().equals("")){
                food.setVe(Double.parseDouble(df.format(Double.parseDouble(map.get("ve").toString()))));
            }else{
                food.setVe(0);
            }
            if(map.get("na") !=null && !map.get("na").toString().equals("")){
                food.setNa(Double.parseDouble(df.format(Double.parseDouble(map.get("na").toString()))));
            }else{
                food.setNa(0);
            }
            if(map.get("ca") !=null && !map.get("ca").toString().equals("")){
                food.setCa(Double.parseDouble(df.format(Double.parseDouble(map.get("ca").toString()))));
            }else{
                food.setCa(0);
            }
            if(map.get("fe") !=null && !map.get("fe").toString().equals("")){
                food.setFe(Double.parseDouble(df.format(Double.parseDouble(map.get("fe").toString()))));
            }else {
                food.setFe(0);
            }
            if(map.get("vc") !=null && !map.get("vc").toString().equals("")){
                food.setVc(Double.parseDouble(df.format(Double.parseDouble(map.get("vc").toString()))));
            }else {
                food.setVc(0);
            }
            if(map.get("cholesterol") !=null && !map.get("cholesterol").toString().equals("")){
                food.setCholesterol(Double.parseDouble(df.format(Double.parseDouble(map.get("cholesterol").toString()))));
            }else {
                food.setCholesterol(0);
            }

            food.setDelFlag((byte) 1);

            // 名字重复则 覆盖
            Food oldFood = manageMapper.selectFoodByName(food.getFoodName());
            if(oldFood != null){
                Food newFood = food;
                newFood.setFoodId(oldFood.getFoodId());
                boolean flag = manageMapper.updateFoodById(newFood);
                if(!flag){
                    return false;
                }
            }else{
                //增加
                int result = manageMapper.insertFood(food);
                if(result <= 0){
                    return false;
                }
            }
            System.out.println(JsonUtil.toJson(food));

        }

        return true;
    }

    @Override
    public List<User> getAllUser() {
        return manageMapper.selectUser();
    }

    @Override
    public List<User> getPageUser(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return manageMapper.selectPageUser(start, pageSize);
    }

    @Override
    public List<User> searchUser(String searchData) {
        return manageMapper.selectUserForSearch(searchData);
    }


}
