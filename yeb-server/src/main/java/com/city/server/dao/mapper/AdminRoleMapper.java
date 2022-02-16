package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.AdminRole;
import com.city.server.vo.Result;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author CitySpring
 * @since 2022-02-04
 */
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操作员的角色信息
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRoles(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
