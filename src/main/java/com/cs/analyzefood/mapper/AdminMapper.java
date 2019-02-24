package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.Admin;

public interface AdminMapper {

    Admin findAdminByPhonePwd(String phone, String pwd);

}
