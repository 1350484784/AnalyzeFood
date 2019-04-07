package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.vo.manage.SystemInfoVo;

public interface AdminService {

    Admin findAdminByPwd(String phone, String pwd);

    void updateSystem(SystemInfoVo systemInfoVo);

    SystemInfoVo findSystemInfo(String adminAccount, String password);

    void updateAdminOnline(int adminId, byte flag);

}
