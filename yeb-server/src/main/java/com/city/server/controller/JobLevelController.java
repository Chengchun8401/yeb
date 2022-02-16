package com.city.server.controller;


import com.city.server.dao.pojo.JobLevel;
import com.city.server.service.JobLevelService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/basic/job")
public class JobLevelController {

    @Autowired
    private JobLevelService jobLevelService;

    @ApiOperation("获取所有职称信息")
    @GetMapping("")
    public Result getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @ApiOperation("添加职称信息")
    @PostMapping("")
    public Result addJobLevel(@RequestBody JobLevel jobLevel){
        return jobLevelService.addJobLevel(jobLevel);
    }

    @ApiOperation("更新职称信息")
    @PutMapping("")
    public Result updateJobLevel(@RequestBody JobLevel jobLevel){
        return jobLevelService.updateJobLevel(jobLevel);
    }

    @ApiOperation("删除职称信息")
    @DeleteMapping("/{id}")
    public Result deleteJobLevel(@PathVariable Integer id){
        return jobLevelService.deleteJobLevel(id);
    }

    @ApiOperation("批量删除职称信息")
    @DeleteMapping("")
    public Result deleteJobLevelByIds(Integer[] ids){
        return jobLevelService.deleteJobLevelByIds(ids);
    }

}
