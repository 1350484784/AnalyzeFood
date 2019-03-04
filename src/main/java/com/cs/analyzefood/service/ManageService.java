package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.FoodType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManageService {
    List<Food> getAllFood();

    List<Food> getPageFood(int page, int pageSize);

    int addNewFood(Food food);

    boolean delOneFood(int foodId);

    List<FoodType> getAllFoodType();

    Food getFoodById(int foodId);

    boolean updateFoodById(Food food);

    List<Food> searchFood(String searchData);

    boolean readExcelFile(MultipartFile file);
}
