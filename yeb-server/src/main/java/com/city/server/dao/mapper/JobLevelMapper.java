package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.JobLevel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author CitySpring
 * @since 2022-02-04
 */

@Repository
public interface JobLevelMapper extends BaseMapper<JobLevel> {

    @Select("select count(*) from t_employee where jobLevelId = #{id}")
    Integer jobLevelCheck(Integer id);
}
