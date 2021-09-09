package com.ccyy.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccyy.manage.model.BaseResponse;
import com.ccyy.manage.model.entity.User;
import com.ccyy.manage.service.RoleService;
import com.ccyy.manage.service.UserService;
import com.ccyy.manage.utils.CommonUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@RequiresAuthentication
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取用户列表,可以动态添加参数")
    @GetMapping("/list")
    @ResponseBody
    public BaseResponse userList(@ApiParam("当前页：1") @RequestParam(value = "page", defaultValue = "1") int page,
                                @ApiParam("页大小：10") @RequestParam(value = "limit", defaultValue = "10") int limit,
                                User userQuery) {
//        动态sql + 分页查询 mybatis-plus 实现，可以动态添加参数
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if (ObjectUtils.isEmpty(userQuery.getUsername())){
            queryWrapper.eq("username",userQuery.getUsername());
        }
        if (ObjectUtils.isEmpty(userQuery.getEmail())){
            queryWrapper.eq("email",userQuery.getEmail());
        }
        if (ObjectUtils.isEmpty(userQuery.getStatus())){
            queryWrapper.eq("status",userQuery.getStatus());
        }
        if (ObjectUtils.isEmpty(userQuery.getDeptId())){
            queryWrapper.eq("dept_id",userQuery.getDeptId());
        }
        IPage<User> userPage = new Page<>(page, limit);
        userPage = userService.page(userPage,queryWrapper);
        return BaseResponse.success(userPage);
    }

    /**
     * 获取全部角色列表，新增角色的时候用
     * @return
     */
    @ApiOperation("获取全部角色列表")
    @GetMapping("/roleList")
    public BaseResponse roleList() {
        return BaseResponse.success(roleService.list());
    }

    @ApiOperation("编辑用户角色")
    @PutMapping("/editUserRoles")
    @ResponseBody
    public BaseResponse editUserRoles(@ApiParam("用户id")@RequestParam(value = "id")Long id,
                                      @ApiParam("用户名")@RequestParam(value = "username") String username,
                                      @ApiParam("角色列表")@RequestParam(value = "roles", required = false) Long[] roleIds) {
        userService.editUserRoles(id,username,roleIds);
        return BaseResponse.success();
    }

    @ApiOperation("添加角色")
    @PostMapping("/insertUser")
    @ResponseBody
    public BaseResponse insertUser(@ApiParam("用户实体") @Valid User user,
                                   @ApiParam("角色列表") @RequestParam(value = "roles", required = false) Long[] roleIds) {
        return BaseResponse.success(userService.insertUser(user, roleIds));
    }

    @ApiOperation("禁用账号")
    @PostMapping("/{userId}/disable")
    @ResponseBody
    public BaseResponse disable(@PathVariable("userId") Long userId) {
        return BaseResponse.success(userService.disableUserByID(userId));
    }

    @ApiOperation("激活账号")
    @PostMapping("/{userId}/enable")
    @ResponseBody
    public BaseResponse enable(@PathVariable("userId") Long userId) {
        return BaseResponse.success(userService.enableUserByID(userId));
    }

    @ApiOperation("删除账号")
    @DeleteMapping("/{userId}")
    @ResponseBody
    public BaseResponse delete(@ApiParam("用户id") @PathVariable("userId") Long userId) {
        userService.removeById(userId);
        return BaseResponse.success();
    }
//
    @ApiOperation("重置密码")
    @PostMapping("/{userId}/reset")
    @ResponseBody
    public BaseResponse resetPassword(@ApiParam("用户id") @PathVariable("userId") Long userId,
                                      @ApiParam("用户密码") String password) {
        userService.updatePasswordByUserId(userId, password);
        return BaseResponse.success();
    }
}
