package com.city.server.controller;

import com.city.server.dao.pojo.Salary;
import com.city.server.service.SalaryService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("")
    public Result getAllSalaries(){
        return Result.success(salaryService.list());
    }

    @ApiOperation("添加工资账套")
    @PostMapping("")
    public Result addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if(salaryService.save(salary)){
            return Result.success("添加成功",null);
        }
        return Result.fail(10086,"添加失败");
    }

    @ApiOperation("更新工资账套")
    @PutMapping("")
    public Result updateSalary(@RequestBody Salary salary){
        if(salaryService.updateById(salary)){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }

    @ApiOperation("删除工资账套")
    @DeleteMapping("/{id}")
    public Result addSalary(@PathVariable Integer id){
        if(salaryService.removeById(id)){
            return Result.success("删除成功",null);
        }
        return Result.fail(10086,"删除失败");
    }


}
