package com.city.server.utils;

import com.city.server.dao.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtils {

    /**
     * 获取当前登录的操作员信息
     * @return
     */
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
