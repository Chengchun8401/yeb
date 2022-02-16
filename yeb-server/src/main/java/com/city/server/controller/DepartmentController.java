package com.city.server.controller;

import com.city.server.dao.pojo.Department;
import com.city.server.service.DepartmentService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("查询所有部门")
    @GetMapping("")
    public Result getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation("添加部门")
    @PostMapping("")
    public Result addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    public Result deleteDepartment(@PathVariable Integer id){
        return departmentService.deleteDepartment(id);
    }

}
