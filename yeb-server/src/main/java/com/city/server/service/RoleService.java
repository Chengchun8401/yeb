package com.city.server.service;

import com.city.server.dao.pojo.Role;
import com.city.server.vo.Result;

public interface RoleService {
    /**
     * 获取所有角色列表
     * @return
     */
    Result getAllRoles();

    /**
     * 添加角色
     * @param role
     * @return
     */
    Result addRoles(Role role);

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    Result deleteRoles(Integer id);
}
