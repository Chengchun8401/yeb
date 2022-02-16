package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.NationMapper;
import com.city.server.dao.pojo.Nation;
import com.city.server.service.NationService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {

    @Autowired
    private NationMapper nationMapper;

    @Override
    public Result getAllNations() {
        return Result.success(nationMapper.selectList(new LambdaQueryWrapper<>()));
    }

}
