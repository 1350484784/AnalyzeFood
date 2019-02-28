package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Food;

import java.util.List;

public interface ManageService {
    List<Food> getAllFood();

    int addNewFood(Food food);
}
