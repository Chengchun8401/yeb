package com.city.server.controller;


import com.city.server.dao.pojo.Role;
import com.city.server.service.MenuRoleService;
import com.city.server.service.MenuService;
import com.city.server.service.RoleService;
import com.city.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/basic/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private MenuService menuService;

    @ApiOperation("获取所有角色列表")
    @GetMapping("")
    public Result getAllRoles(){
        return roleService.getAllRoles();
    }

    @ApiOperation("添加角色")
    @PostMapping("")
    public Result addRoles(@RequestBody Role role){
        return roleService.addRoles(role);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public Result addRoles(@PathVariable Integer id){
        return roleService.deleteRoles(id);
    }

    @ApiOperation("查询所有菜单")
    @GetMapping("/menus")
    public Result getAllMenus(){
        return menuService.getAllMenus();
    }

    @ApiOperation("根据角色id查询权限菜单id")
    @GetMapping("/menus/{rid}")
    public Result getMenusByRoleId(@PathVariable Integer rid){
        return menuRoleService.getMenuIdsByRoleId(rid);
    }

    @ApiOperation("更新角色菜单")
    @PutMapping("/menus")
    public Result updateMenuRole(Integer rid, Integer[] mids){
        return menuRoleService.updateMenuRole(rid, mids);
    }

}
