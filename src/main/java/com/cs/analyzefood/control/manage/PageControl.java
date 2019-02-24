package com.cs.analyzefood.control.manage;

import com.cs.analyzefood.entity.Admin;
import com.cs.analyzefood.entity.vo.manage.SystemInfoVo;
import com.cs.analyzefood.exception.SystemFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage")
public class PageControl {


    @RequestMapping("/systemInfo")
    @ResponseBody
    public ResponseEntity systemInfo(HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null){
            throw new SystemFailedException("admin do not login");
        }
        SystemInfoVo systemInfoVo = new SystemInfoVo(admin.getAdminAccount(),admin.getAuthor(),admin.getProjectName(),admin.getVersion(),admin.getDescription(),admin.getHomePage());

        return new ResponseEntity(systemInfoVo, HttpStatus.OK);
    }
}
