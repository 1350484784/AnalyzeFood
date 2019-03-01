package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Food;

import java.util.List;

public interface ManageService {
    List<Food> getAllFood();

    List<Food> getPageFood(int page, int pageSize);

    int addNewFood(Food food);

    boolean delOneFood(int foodId);
}
