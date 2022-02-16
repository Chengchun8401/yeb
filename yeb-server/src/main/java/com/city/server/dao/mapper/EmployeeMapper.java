package com.city.server.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.server.dao.pojo.Employee;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author CitySpring
 * @since 2022-02-04
 */

@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工(分页)
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询员工信息
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工的工资账套
     * @param page
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
