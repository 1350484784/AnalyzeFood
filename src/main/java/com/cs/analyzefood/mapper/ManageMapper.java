package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.*;

import java.util.List;

public interface ManageMapper {

    List<Food> selectFood();

    int insertFood(Food food);

    boolean updateFoodFlag(int foodId);

    List<Food> selectPageFood(int start, int pageSize);

    List<FoodType> selectFoodType();

    Food selectFoodByFoodId(int foodId);

    boolean updateFoodById(Food food);

    List<Food> selectFoodForSearch(String searchData);

    Food selectFoodByName(String foodName);

    List<User> selectUser();

    List<User> selectPageUser(int start, int pageSize);

    List<User> selectUserForSearch(String searchData);

    List<Article> selectArticle();

    List<Article> selectPageArticle(int start, int pageSize);

    String selectUserAccountById(int roleId);

    List<Article> selectArticleForSearch(String searchData);

    List<ArticleReport> selectAllReport();

    List<ArticleReport> selectPageReport(int start, int pageSize);
}
