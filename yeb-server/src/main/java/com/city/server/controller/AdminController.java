package com.city.server.controller;


import com.city.server.dao.pojo.Admin;
import com.city.server.service.AdminService;
import com.city.server.service.RoleService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CitySpring
 * @since 2022-02-04
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @ApiOperation("获取所有操作员")
    @GetMapping("")
    public Result getAllAdmins(String keywords){
        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation("更新操作员信息")
    @PutMapping("")
    public Result updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }

    @ApiOperation("删除操作员")
    @DeleteMapping("/{id}")
    public Result deleteAdmin(@PathVariable Integer id){
        return adminService.deleteAdmin(id);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/roles")
    public Result getAllRoles(){
        return roleService.getAllRoles();
    }

    @ApiOperation("更新操作员的角色")
    @PutMapping("/roles")
    public Result updateAdminRoles(Integer adminId, Integer[] rids){
        return adminService.updateAdminRoles(adminId,rids);
    }

}
