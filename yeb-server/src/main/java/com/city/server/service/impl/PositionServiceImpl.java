package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.PositionMapper;
import com.city.server.dao.pojo.Position;
import com.city.server.service.PositionService;
import com.city.server.vo.ErrorCode;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    private PositionMapper positionMapper;


    @Override
    public Result getAllPositions() {
        return Result.success(positionMapper.selectList(new LambdaQueryWrapper<>()));
    }

    @Override
    public Result addPosition(Position position) {
        position.setCreateDate(LocalDateTime.now());
        if(positionMapper.insert(position) > 0){
            return Result.success("添加成功",null);
        }
        return Result.fail(ErrorCode.ADD_FAIL.getCode(), ErrorCode.ADD_FAIL.getMsg());
    }

    @Override
    public Result updatePosition(Position position) {
        if(positionMapper.update(position,new UpdateWrapper<Position>().set("name",position.getName()).eq("id",position.getId())) > 0){
            return Result.success("更新成功",null);
        }
        return Result.fail(100086, "更新失败");
    }

    @Override
    public Result deletePosition(Integer id) {
        if(positionMapper.positionCheck(id) > 0){
            return Result.fail(100086, "该职位存在关联员工，删除失败");
        }
        if(positionMapper.deleteById(id) > 0){
            return Result.success("删除成功",null);
        }
        return Result.fail(100086, "删除失败");
    }

    @Override
    @Transactional
    public Result deletePositionByIds(Integer[] ids) {
        if(positionMapper.deleteBatchIds(Arrays.asList(ids)) > 0){
            return Result.success("批量删除成功",null);
        }
        return Result.fail(100086, "批量删除失败");
    }
}
