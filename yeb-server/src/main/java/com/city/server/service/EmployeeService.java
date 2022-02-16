package com.city.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.Employee;
import com.city.server.vo.Result;
import com.city.server.vo.param.PageParam;

import java.util.List;

public interface EmployeeService extends IService<Employee> {

    /**
     * 获取所有员工(分页)
     * @return
     */
    Result getEmployeeByPage(PageParam pageParam);

    /**
     * 获取最大工号
     * @return
     */
    Result getMaxWorkId();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    Result addEmployee(Employee employee);

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    Result updateEmployee(Employee employee);

    /**
     * 根据id删除员工信息
     * @param id
     * @return
     */
    Result deleteEmployee(Integer id);

    /**
     * 查询员工信息，不传入id则查全部，代码复用
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工的工资账套
     * @param currentPage
     * @param pageSize
     * @return
     */
    Result getEmployeeWithSalary(Integer currentPage, Integer pageSize);
}
