package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    User findUserByPhone(String phone);

    int addNewUser(String phone,String password);

    User findUserById(int roleId);

    User findUserByPhoneAndPwd(String phone,String password);

    boolean updateUserOnlineFlag(int roleId,byte onlineFlag);

    boolean updateUserPwd(String phone,String password);

    boolean updateUserSelf(User user);

    String uploadUserHeadImg(int roleId, String imgName);

//    PageInfo<Food> getAllfood(int currentPage);

    int getFoodsCount();

    List<Food> getPageFood(int begin,int count);

    Food findFoodById(int foodId);
}
