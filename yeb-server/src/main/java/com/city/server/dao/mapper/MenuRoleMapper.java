package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.MenuRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * @author CitySpring
 * @since 2022-02-04
 */

@Repository
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量插入角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
