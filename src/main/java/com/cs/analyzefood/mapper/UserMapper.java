package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.User;

public interface UserMapper {
    User selectUserByPhone(String phone);

    int insertUser(User user);

    User selectUserById(int roleId);

    User selectUserByPhoneAndPwd(String phone,String password);

    boolean updateUserOnlineFlag(int roleId,byte onlineFlag);

    boolean updateUserPwd(String phone, String password);

    boolean updateUserById(User user);
}
