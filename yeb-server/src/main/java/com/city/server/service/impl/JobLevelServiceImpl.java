package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.JobLevelMapper;
import com.city.server.dao.pojo.JobLevel;
import com.city.server.service.JobLevelService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Arrays;


@Service
@Transactional
public class JobLevelServiceImpl extends ServiceImpl<JobLevelMapper, JobLevel> implements JobLevelService {

    @Autowired
    private JobLevelMapper joblevelMapper;

    @Override
    public Result getAllJobLevels() {
        return Result.success(joblevelMapper.selectList(new LambdaQueryWrapper<>()));
    }

    @Override
    public Result addJobLevel(JobLevel jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        if(joblevelMapper.insert(jobLevel) > 0){
            return Result.success("添加成功",null);
        }
        return Result.fail(10086, "添加失败");
    }

    @Override
    public Result updateJobLevel(JobLevel jobLevel) {
        if(joblevelMapper.update(jobLevel,new UpdateWrapper<JobLevel>()
                .set("name",jobLevel.getName())
                .set("titleLevel",jobLevel.getTitleLevel())
                .set("enabled",jobLevel.getEnabled())
                .eq("id",jobLevel.getId())) > 0){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086, "更新失败");
    }

    @Override
    public Result deleteJobLevel(Integer id) {
        if(joblevelMapper.jobLevelCheck(id) > 0){
            return Result.fail(100086, "该职位存在关联员工，删除失败");
        }
        if(joblevelMapper.deleteById(id) > 0){
            return Result.success("删除成功",null);
        }
        return Result.fail(10086, "删除失败");
    }

    @Override
    @Transactional
    public Result deleteJobLevelByIds(Integer[] ids) {
        if(joblevelMapper.deleteBatchIds(Arrays.asList(ids)) > 0){
            return Result.success("批量删除成功",null);
        }
        return Result.fail(10086, "批量删除失败");
    }
}
