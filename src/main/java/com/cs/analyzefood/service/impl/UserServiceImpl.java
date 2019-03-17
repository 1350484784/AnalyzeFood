package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.mapper.UserMapper;
import com.cs.analyzefood.entity.User;
import com.cs.analyzefood.mapper.UserZoneMapper;
import com.cs.analyzefood.service.UserService;
import com.cs.analyzefood.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserZoneMapper userZoneMapper;

    @Value("${user.headImg.moren}")
    private String headImg;

    @Value("${user.headImg.path}")
    private String headImg_path;

    @Value("${user.bgImg.moren}")
    private String bgImg;

    @Value("${user.bgImg.path}")
    private String bgImg_path;

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public int addNewUser(String phone, String password) {
        Date date = new Date();
        UUID id=UUID.randomUUID();
        String[] uuid=id.toString().split("-");
        String userAccount = uuid[0] + DateUtil.getSDate(date, "yyyyMMddhhmmss");

        String base64Pwd = Base64.getEncoder().encodeToString((password).getBytes());

        User newUser = new User(userAccount,base64Pwd,phone,headImg,date);
        int result = userMapper.insertUser(newUser);
        if(result > 0){
            userZoneMapper.insertUserZone(newUser.getRoleId(), bgImg);
            return newUser.getRoleId();
        }
        return -1;
    }

    @Override
    public User findUserById(int roleId) {
        return userMapper.selectUserById(roleId);
    }

    @Override
    public User findUserByPhoneAndPwd(String phone, String password) {
        return userMapper.selectUserByPhoneAndPwd(phone, password);
    }

    @Override
    public boolean updateUserOnlineFlag(int roleId,byte onlineFlag) {
        return userMapper.updateUserOnlineFlag(roleId,onlineFlag);
    }

    @Override
    public boolean updateUserPwd(String phone, String password) {
        String base64Pwd = Base64.getEncoder().encodeToString((password).getBytes());
        return userMapper.updateUserPwd(phone, base64Pwd);
    }

    @Override
    public boolean updateUserSelf(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public String uploadUserHeadImg(int roleId, String imgName) {
        userMapper.updateUserHeadImgById(headImg_path+imgName,roleId);
        return headImg_path+imgName;
    }


}
