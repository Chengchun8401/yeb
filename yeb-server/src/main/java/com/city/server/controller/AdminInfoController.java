package com.city.server.controller;

import com.city.server.dao.pojo.Admin;
import com.city.server.service.AdminService;
import com.city.server.utils.QiNiuUtils;
import com.city.server.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * 个人中心
 */
@RestController
public class AdminInfoController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("更新当前用户信息")
    @PutMapping("/admin/info")
    public Result updateAdmin(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }

    @ApiOperation("更新密码")
    @PutMapping("/admin/pass")
    public Result updateAdminPassword(@RequestBody Map<String,Object> info){
        return adminService.updateAdminPassword(info);
    }

    @ApiOperation("更换用户头像")
    @PutMapping("/admin/avatar")
    public Result changeAvatar(MultipartFile file, Integer id, Authentication authentication){
        // 获取文件原始名称   xxx.png
        String originalFilename = file.getOriginalFilename();

        // 生成唯一文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        // 上传到七牛云的免费服务器，减小自身服务器的带宽消耗
        boolean upload = QiNiuUtils.upload(file, fileName);
        if(upload){
            Admin admin = adminService.getById(id);
            admin.setUserFace("http://" + QiNiuUtils.url + "/" + fileName); // http, 不要设置为https
            if(adminService.updateById(admin)){
                return Result.success("更新成功",null);
            }
            return Result.fail(10086,"更新失败");
        }else{
            return Result.fail(20002, "头像上传失败");
        }
    }

}
