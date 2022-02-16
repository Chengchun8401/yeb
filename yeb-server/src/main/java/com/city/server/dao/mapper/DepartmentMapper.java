package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.server.dao.pojo.Department;
import com.city.server.vo.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CitySpring
 * @since 2022-02-04
 */
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 使用 Mybatis 的递归查询
     * @param parentId
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param dep
     * @return
     */
    void addDepartment(Department dep);

    /**
     *  根据id删除部门
     * @param dep
     */
    void deleteDepartment(Department dep);
}
