package com.city.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.DepartmentMapper;
import com.city.server.dao.pojo.Department;
import com.city.server.service.DepartmentService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Result getAllDepartments() {
        return Result.success(departmentMapper.getAllDepartments(-1));
    }

    @Override
    public Result addDepartment(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDepartment(dep);

        if(dep.getResult() == 1){
            return Result.success("添加成功",dep);
        }
        return Result.fail(10086, "添加失败");
    }

    @Override
    public Result deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        // 放入department  为了能让操作结果result成功设置  直接传入id报错：There is no setter for property named 'result' in 'class java.lang.Integer'
        departmentMapper.deleteDepartment(department);
        if(department.getResult() == -2){
            return Result.fail(10086,"该部门下还有其他部门，删除失败");
        }else if(department.getResult() == -1){
            return Result.fail(10086,"该部门下还有员工，删除失败");
        }
        if(department.getResult() == 1){
            return Result.success("删除成功",null);
        }
        return Result.fail(10086,"删除失败");
    }
}
