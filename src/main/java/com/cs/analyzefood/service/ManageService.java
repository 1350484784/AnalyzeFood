package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Article;
import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.FoodType;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.entity.vo.manage.TableArticle;
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

    List<User> getAllUser();

    List<User> getPageUser(int page, int limit);


    List<User> searchUser(String searchData);

    List<Article> getAllArticle();

    List<TableArticle> getPageArticle(int page, int limit);
}
