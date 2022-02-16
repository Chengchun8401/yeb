package com.city.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.Department;
import com.city.server.vo.Result;

import java.util.List;

public interface DepartmentService extends IService<Department> {
    /**
     * 获取所有部门
     * @return
     */
    Result getAllDepartments();

    /**
     * 添加部门
     * @param department
     * @return
     */
    Result addDepartment(Department department);

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    Result deleteDepartment(Integer id);

}
