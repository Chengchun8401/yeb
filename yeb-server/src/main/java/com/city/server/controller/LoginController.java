package com.city.server.controller;


import com.city.server.dao.pojo.Admin;
import com.city.server.service.AdminService;
import com.city.server.service.LoginService;
import com.city.server.vo.Result;
import com.city.server.vo.param.LoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    @ApiOperation("登录之后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Result getAdminInfo(Principal principal){  // 在loginService中，用户信息放入全局
        if(principal == null){
            return null;
        }

        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        // 设置用户角色信息
        admin.setRoles(adminService.getRoles(admin.getId()));
        return Result.success(null,admin);
    }

}
