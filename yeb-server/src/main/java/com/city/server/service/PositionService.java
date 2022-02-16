package com.city.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.Position;
import com.city.server.vo.Result;

import java.util.List;

public interface PositionService extends IService<Position> {
    /**
     * 获取所有职位信息
     * @return
     */
    Result getAllPositions();

    /**
     * 添加职位信息
     * @param position
     * @return
     */
    Result addPosition(Position position);

    /**
     * 更新职位信息
     * @param position
     * @return
     */
    Result updatePosition(Position position);

    /**
     * 根据id删除职位信息
     * @param id
     * @return
     */
    Result deletePosition(Integer id);

    /**
     * 批量删除职位信息
     * @param ids
     * @return
     */
    Result deletePositionByIds(Integer[] ids);

}
