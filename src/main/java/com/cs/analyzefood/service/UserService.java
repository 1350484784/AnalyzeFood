package com.cs.analyzefood.service;

import com.cs.analyzefood.pojo.User;

public interface UserService {
    User findUserByPhone(String phone);

    int addNewUser(String phone,String password);

    User findUserById(int roleId);

    User findUserByPhoneAndPwd(String phone,String password);

    boolean updateUserOnlineFlag(int roleId,byte onlineFlag);

    boolean updateUserPwd(String phone,String password);
}
