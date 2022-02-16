package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.city.server.dao.mapper.RoleMapper;
import com.city.server.dao.pojo.Role;
import com.city.server.service.RoleService;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result getAllRoles() {
        return Result.success(roleMapper.selectList(new LambdaQueryWrapper<>()));
    }

    @Override
    public Result addRoles(Role role) {
        // 角色名称判定
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }

        if(roleMapper.insert(role) > 0){
            return Result.success("添加成功",null);
        }
        return Result.fail(10086,"添加失败");
    }

    @Override
    public Result deleteRoles(Integer id) {
        if(roleMapper.checkRole(id) > 0){
            return Result.fail(10086,"该角色存在关联管理员，删除失败");
        }
        if(roleMapper.deleteById(id) > 0){
            return Result.success("删除成功",null);
        }
        return Result.fail(10086,"删除失败");
    }
}
