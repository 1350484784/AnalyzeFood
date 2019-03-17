package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.UserZone;
import com.cs.analyzefood.mapper.UserZoneMapper;
import com.cs.analyzefood.service.UserZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserZoneServiceImpl implements UserZoneService {

    @Value("${user.bgImg.path}")
    private String bgImg_path;

    @Autowired
    private UserZoneMapper userZoneMapper;

    @Override
    public UserZone selectUserZone(int roleId) {
        return userZoneMapper.selectZone(roleId);
    }

    @Override
    public String uploadUserBgImg(int roleId, String bgImgName) {
        userZoneMapper.updateUserZoneBgImgById(bgImg_path + bgImgName,roleId);
        return bgImg_path + bgImgName;
    }
}
