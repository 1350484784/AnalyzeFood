package com.cs.analyzefood.mapper;

import com.cs.analyzefood.pojo.Admin;

public interface AdminMapper {

    Admin findAdminByPhonePwd(String phone, String pwd);

}
