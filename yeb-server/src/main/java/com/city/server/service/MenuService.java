package com.city.server.service;


import com.city.server.dao.pojo.Menu;
import com.city.server.vo.Result;

import java.util.List;

public interface MenuService {

    /**
     * 通过用户id查询菜单列表
     * @return List<Menu>
     */
    Result getMenusByAdminId();

    /**
     * 根据角色获取菜单列表
     * @return List<Menu>  在后端调用，无需封装进Result
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    Result getAllMenus();

}
