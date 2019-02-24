package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Admin;

public interface AdminService {

    Admin findAdminByPwd(String phone, String pwd);
}
