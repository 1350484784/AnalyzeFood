package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.mapper.AdminMapper;
import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper mapper;

    @Override
    public Admin findAdminByPwd(String phone, String pwd) {
        return mapper.findAdminByPhonePwd(phone, pwd);
    }
}
