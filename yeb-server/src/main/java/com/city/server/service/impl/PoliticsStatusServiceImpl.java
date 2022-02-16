package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.PoliticsStatusMapper;
import com.city.server.dao.pojo.PoliticsStatus;
import com.city.server.service.PoliticsStatusService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PoliticsStatusServiceImpl extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus> implements PoliticsStatusService {

    @Autowired
    private PoliticsStatusMapper politicsStatusMapper;

    @Override
    public Result getAllPoliticStatus() {
        return Result.success(politicsStatusMapper.selectList(new LambdaQueryWrapper<>()));
    }

}
