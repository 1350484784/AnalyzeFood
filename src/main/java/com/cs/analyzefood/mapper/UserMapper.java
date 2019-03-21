package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.User;

import java.util.List;

public interface UserMapper {
    User selectUserByPhone(String phone);

    int insertUser(User user);

    User selectUserById(int roleId);

    User selectUserByPhoneAndPwd(String phone,String password);

    boolean updateUserOnlineFlag(int roleId,byte onlineFlag);

    boolean updateUserPwd(String phone, String password);

    boolean updateUserById(User user);

    boolean updateUserHeadImgById(String imgName, int roleId);

    List<Food> selectAllFoods();

    int selectFoodNum();

    List<Food> selectFoodPage(int begin, int count);

    Food selectFoodById(int foodId);

    int selectFoodNumByFoodIds(int[] foodIds);
}
