package com.city.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.Admin;
import com.city.server.dao.pojo.Role;
import com.city.server.vo.Result;

import java.util.List;
import java.util.Map;

public interface AdminService extends IService<Admin> {
    /**
     * 根据用户名获取用户
     * @param username
     * @return Admin
     */
    Admin getAdminByUsername(String username);

    /**
     * 根据用户id查询角色
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员信息，排除当前已登录的，且根据权限查询
     * @param keyword
     * @return
     */
    Result getAllAdmins(String keyword);

    /**
     * 根据id删除操作员信息
     * @param id
     * @return
     */
    Result deleteAdmin(Integer id);

    /**
     * 更新操作员的角色信息
     * @param adminId
     * @param rids
     * @return
     */
    Result updateAdminRoles(Integer adminId, Integer[] rids);

    Result updateAdminPassword(Map<String, Object> info);
}
