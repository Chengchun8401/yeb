package com.city.server.controller;

import com.city.server.service.AdminService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("获取所有操作员")
    @GetMapping("/admin")
    public Result getAllAdmin(String keywords){
        return adminService.getAllAdmins(keywords);
    }

}
