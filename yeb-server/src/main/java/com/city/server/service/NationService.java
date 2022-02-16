package com.city.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.Nation;
import com.city.server.vo.Result;




public interface NationService extends IService<Nation> {
    /**
     * 获取所有民族
     * @return
     */
    Result getAllNations();

}
