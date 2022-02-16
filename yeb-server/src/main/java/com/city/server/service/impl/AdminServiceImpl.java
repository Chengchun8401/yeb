package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.AdminMapper;
import com.city.server.dao.mapper.AdminRoleMapper;
import com.city.server.dao.mapper.RoleMapper;
import com.city.server.dao.pojo.Admin;
import com.city.server.dao.pojo.AdminRole;
import com.city.server.dao.pojo.Role;
import com.city.server.service.AdminService;
import com.city.server.utils.AdminUtils;
import com.city.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public Admin getAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        queryWrapper.eq(Admin::isEnabled, true);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRolesByAdminId(adminId);
    }

    @Override
    public Result getAllAdmins(String keywords) {
        return Result.success(adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords));
    }

    @Override
    public Result deleteAdmin(Integer id) {
        if(adminMapper.deleteById(id) > 0){
            return Result.success("删除成功",null);
        }
        return Result.fail(10086,"删除失败");
    }

    @Override
    @Transactional
    public Result updateAdminRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));

        if(rids == null || rids.length == 0){
            return Result.success("更新成功",null);
        }

        Integer result = adminRoleMapper.addAdminRoles(adminId, rids);
        if(result == rids.length){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }

    @Override
    public Result updateAdminPassword(Map<String, Object> info) {
        String oldPass = (String)info.get("oldPass");
        String newPass = (String)info.get("newPass");
        Integer adminId = (Integer)info.get("adminId");

        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(oldPass,admin.getPassword())){
            return Result.fail(10086,"密码错误，请正确输入原密码");
        }
        admin.setPassword(encoder.encode(newPass)); // 加密

        if(adminMapper.updateById(admin) > 0){
            return Result.success("更新成功",null);
        }

        return Result.fail(10086,"更新失败：未知错误");
    }


}
