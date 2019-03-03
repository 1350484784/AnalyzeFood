package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.FoodType;
import com.cs.analyzefood.util.ReadExcel;
import com.cs.analyzefood.mapper.ManageMapper;
import com.cs.analyzefood.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public String readExcelFile(MultipartFile file) {
        String result = "";
        ReadExcel readExcel = new ReadExcel();
        List<Map<String, Object>> foodList = readExcel.getExcelInfo(file);

        for (Map<String, Object> map : foodList) {
//            int ret = userDao.insertUser(user.get("name").toString(), user.get("sex").toString(), Integer.parseInt(user.get("age").toString()));
//            if (ret == 0) {
//                result = "插入数据库失败";
//            }
            map.forEach((k,v)->
                System.out.println(k+","+v)
            );
        }
        if (foodList != null && !foodList.isEmpty()) {
            result = "上传成功";
        } else {
            result = "上传失败";
        }
        return result;
    }


}
