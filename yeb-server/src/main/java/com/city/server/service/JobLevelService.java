package com.city.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.server.dao.pojo.JobLevel;
import com.city.server.vo.Result;


public interface JobLevelService extends IService<JobLevel> {

    /**
     * 获取全部职称信息
     * @return
     */
    Result getAllJobLevels();

    /**
     * 添加职称信息
     * @param jobLevel
     * @return
     */
    Result addJobLevel(JobLevel jobLevel);

    /**
     * 更新职称信息
     * @param jobLevel
     * @return
     */
    Result updateJobLevel(JobLevel jobLevel);

    /**
     * 删除职称信息
     * @param id
     * @return
     */
    Result deleteJobLevel(Integer id);

    /**
     * 批量删除职称信息
     * @param ids
     * @return
     */
    Result deleteJobLevelByIds(Integer[] ids);

}
