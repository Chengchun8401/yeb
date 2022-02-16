package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.Position;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author CitySpring
 * @since 2022-02-04
 */

@Repository
public interface PositionMapper extends BaseMapper<Position> {

    @Select("select count(*) from t_employee where posId = #{id}")
    Integer positionCheck(Integer id);

}
