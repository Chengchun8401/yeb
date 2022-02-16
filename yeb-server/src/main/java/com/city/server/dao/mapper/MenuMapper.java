package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CitySpring
 * @since 2022-02-04
 */

@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByAdminId(Integer id);

    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();

}
