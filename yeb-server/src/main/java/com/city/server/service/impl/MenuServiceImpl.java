package com.city.server.service.impl;

import com.city.server.common.cache.Cache;
import com.city.server.dao.mapper.MenuMapper;
import com.city.server.dao.pojo.Admin;
import com.city.server.dao.pojo.Menu;
import com.city.server.service.MenuService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    /**
     * 通过用户id查询菜单列表
     * @return
     */
    @Override
    public Result getMenusByAdminId() {
        return Result.success(menuMapper.getMenusByAdminId(((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

    @Override
    @Cache(name = "Security权限组")
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    @Override
    public Result getAllMenus() {
        return Result.success(menuMapper.getAllMenus());
    }


}
