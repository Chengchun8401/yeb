package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.city.server.common.cache.Cache;
import com.city.server.dao.mapper.MenuRoleMapper;
import com.city.server.dao.pojo.MenuRole;
import com.city.server.service.MenuRoleService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public Result getMenuIdsByRoleId(Integer rid) {
        LambdaQueryWrapper<MenuRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(MenuRole::getRid, rid);
        queryWrapper.select(MenuRole::getMid);
        // 查出结果是MenuRole对象的List，使用stream流转成菜单id的List
        return Result.success(menuRoleMapper.selectList(queryWrapper).stream().map(MenuRole::getMid).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public Result updateMenuRole(Integer rid, Integer[] mids) {
        /**
         * 1.把该角色下的所有菜单清空
         * 2.批量更新角色菜单关系
         */
        menuRoleMapper.delete(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRid, rid));

        // 删除全部权限，不进行添加
        if(mids == null || mids.length == 0){
            return Result.success("更新成功",null);
        }

        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if(result == mids.length){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }
}
