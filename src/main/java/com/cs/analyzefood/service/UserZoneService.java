package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.UserZone;

public interface UserZoneService {

    UserZone selectUserZone(int roleId);

    String uploadUserBgImg(int roleId, String bgImgName);
}
