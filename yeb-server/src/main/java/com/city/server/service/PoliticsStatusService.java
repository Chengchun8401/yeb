package com.city.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.PoliticsStatus;
import com.city.server.vo.Result;



public interface PoliticsStatusService extends IService<PoliticsStatus> {
    /**
     * 获取所有政治面貌
     * @return
     */
    Result getAllPoliticStatus();

}
