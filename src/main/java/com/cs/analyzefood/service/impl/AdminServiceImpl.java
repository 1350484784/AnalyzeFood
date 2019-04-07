package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.vo.manage.SystemInfoVo;
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

    @Override
    public void updateSystem(SystemInfoVo systemInfoVo) {
        mapper.updateSystem(systemInfoVo);
    }

    @Override
    public SystemInfoVo findSystemInfo(String adminAccount, String password) {
        return mapper.selectSystemInfo(adminAccount, password);
    }

    @Override
    public void updateAdminOnline(int adminId, byte flag) {
        mapper.updateAdminOnline(adminId, flag);
    }
}
