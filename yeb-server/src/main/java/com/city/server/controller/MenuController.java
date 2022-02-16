package com.city.server.controller;

import com.city.server.service.MenuService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 给该请求配置权限为登录即访问，不然只有指定的角色可以拉取菜单
     * 原路径： /system/cfg/menu
     * @return
     */
    @ApiOperation("通过用户id查询菜单列表")
    @GetMapping("")
    public Result getMenusByAdminId(){
        return menuService.getMenusByAdminId();
    }

}
