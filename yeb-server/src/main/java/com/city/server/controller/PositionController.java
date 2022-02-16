package com.city.server.controller;

import com.city.server.dao.pojo.Position;
import com.city.server.service.PositionService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation("获取所有职位信息")
    @GetMapping("")
    public Result getAllPositions(){
        return positionService.getAllPositions();
    }

    @ApiOperation("添加职位信息")
    @PostMapping("")
    public Result addPosition(@RequestBody Position position){
        return positionService.addPosition(position);
    }

    @ApiOperation("更新职位信息")
    @PutMapping("")
    public Result updatePosition(@RequestBody Position position){
        return positionService.updatePosition(position);
    }

    @ApiOperation("删除职位信息")
    @DeleteMapping("/{id}")
    public Result deletePosition(@PathVariable Integer id){
        return positionService.deletePosition(id);
    }

    @ApiOperation("批量删除职位信息")
    @DeleteMapping("")
    public Result deletePositionByIds(Integer[] ids){
        return positionService.deletePositionByIds(ids);
    }

}
