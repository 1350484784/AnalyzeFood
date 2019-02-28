package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.mapper.ManageMapper;
import com.cs.analyzefood.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int addNewFood(Food food) {

        int result = 0;
        try {
            result = manageMapper.insertFood(food);
        }catch (Exception e){
            logger.debug("food name repeat");
        }
        if(result > 0){
            return food.getFoodId();
        }
        return -1;
    }


}
