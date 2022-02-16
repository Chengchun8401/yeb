package com.city.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.city.server.dao.pojo.Employee;
import com.city.server.service.EmployeeService;
import com.city.server.service.SalaryService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCngController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/salaries")
    public Result getAllSalaries(){
        return Result.success(salaryService.list());
    }

    @ApiOperation("查询所有员工账套")
    @GetMapping("")
    public Result getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize){
        return employeeService.getEmployeeWithSalary(currentPage,pageSize);
    }

    @ApiOperation("更新员工账套")
    @PutMapping("")
    public Result updateEmployeeSalary(Integer eId, Integer sId){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sId).eq("id",eId))){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }


}
