package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.UserZone;

public interface UserZoneMapper {
    void insertUserZone(int roleId, String bgImg);

    UserZone selectZone(int roleId);

    void updateUserZoneBgImgById(String s, int roleId);
}
