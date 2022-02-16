package com.city.server.service;

import com.city.server.vo.Result;

public interface MenuRoleService {
    /**
     * 根据角色id获取菜单id
     * @param rid
     * @return
     */
    Result getMenuIdsByRoleId(Integer rid);

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Result updateMenuRole(Integer rid, Integer[] mids);
}
