package com.ccyy.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccyy.manage.model.BaseResponse;
import com.ccyy.manage.model.entity.Role;
import com.ccyy.manage.model.entity.User;
import com.ccyy.manage.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@Api("用户模块")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("查询角色列表")
    @GetMapping("/list")
    @ResponseBody
    public BaseResponse getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "limit", defaultValue = "10")int limit) {
        IPage<Role> roleIPage  = new Page<>(page, limit);
        roleIPage = roleService.page(roleIPage,null);
        return BaseResponse.success(roleIPage);
    }


    @ApiOperation("新增角色")
    @PostMapping
    @ResponseBody
    public BaseResponse add(Role role) {
        roleService.save(role);
        return BaseResponse.success();
    }

    @ApiOperation("修改角色")
    @PutMapping
    @ResponseBody
    public BaseResponse update(Role role) {
        roleService.updateById(role);
        return BaseResponse.success();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    @ResponseBody
    public BaseResponse delete(@PathVariable("roleId") Long roleId) {
        roleService.removeById(roleId);
        return BaseResponse.success();
    }

    @ApiOperation("为角色授予菜单")
    @PostMapping("/{roleId}/grant/menu")
    @ResponseBody
    public BaseResponse grantMenu(@PathVariable("roleId") Long roleId,
                                  @RequestParam(value = "menuIds[]", required = false) Long[] menuIds) {
        roleService.grantMenu(roleId, menuIds);
        return BaseResponse.success();
    }


    @ApiOperation("为角色授予操作权限")
    @PostMapping("/{roleId}/grant/operator")
    @ResponseBody
    public BaseResponse grantOperator(@PathVariable("roleId") Long roleId,
                                      @RequestParam(value = "operatorIds[]", required = false) Long[] operatorIds) {
        roleService.grantOperator(roleId, operatorIds);
        return BaseResponse.success();
    }

//    /**
//     * 获取角色拥有的菜单
//     */
//    @ApiOperation("获取角色拥有的菜单")
//    @GetMapping("/{roleId}/own/menu")
//    @ResponseBody
//    public BaseResponse getRoleOwnMenu(@PathVariable("roleId") Long roleId) {
//        return BaseResponse.success(roleService.getMenusByRoleId(roleId));
//    }
//
//    /**
//     * 获取角色拥有的操作权限
//     */
//
//    @ApiOperation("获取角色拥有的操作权限")
//    @GetMapping("/{roleId}/own/operator")
//    @ResponseBody
//    public BaseResponse getRoleOwnOperator(@PathVariable("roleId") Long roleId) {
//        Long[] operatorIds = roleService.getOperatorsByRoleId(roleId);
//        for (int i = 0; i < operatorIds.length; i++) {
//            operatorIds[i] = operatorIds[i] + 10000;
//        }
//        return BaseResponse.success(operatorIds);
//    }
}
