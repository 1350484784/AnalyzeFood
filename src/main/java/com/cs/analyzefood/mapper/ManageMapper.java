package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.Food;

import java.util.List;

public interface ManageMapper {

    List<Food> selectFood();

    int insertFood(Food food);

    boolean updateFoodFlag(int foodId);

    List<Food> selectPageFood(int start, int pageSize);
}
