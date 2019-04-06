package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.vo.manage.SystemInfoVo;

public interface AdminMapper {

    Admin findAdminByPhonePwd(String phone, String pwd);

    void updateSystem(SystemInfoVo systemInfoVo);

    SystemInfoVo selectSystemInfo(String adminAccount, String password);
}
