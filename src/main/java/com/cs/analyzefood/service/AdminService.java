package com.cs.analyzefood.service;

import com.cs.analyzefood.pojo.Admin;

public interface AdminService {

    Admin findAdminByPwd(String phone, String pwd);
}
