package com.city.server.vo.param;

import com.city.server.dao.pojo.Employee;
import lombok.Data;


@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    /**
     * 按条件查询，传递员工对象
     */
    private Employee employee;

    /**
     * 入职日期的范围，无法放在员工对象中，单独设置
     */
    private String beginDateScope;

}
