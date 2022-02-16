package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CitySpring
 * @since 2022-02-04
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);

    @Select("select count(*) from t_admin_role where rid = #{id}")
    Integer checkRole(Integer id);
}
