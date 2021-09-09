package com.ccyy.manage.controller;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccyy.manage.model.BaseResponse;
import com.ccyy.manage.model.UserProfile;
import com.ccyy.manage.model.entity.Menu;
import com.ccyy.manage.model.entity.User;
import com.ccyy.manage.service.MenuService;
import com.ccyy.manage.service.UserService;
import com.ccyy.manage.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lianghanmao
 * @date 2021年07月21日 下午3:22
 */
@Api("登录模块")
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse login(@ApiParam(value = "用户名:admin") @RequestParam("username")String username,
                              @ApiParam(value = "密码:123456")@RequestParam("password")String password,
                              HttpServletResponse response){
        if (username ==null || "".equals(username)){
            return  BaseResponse.fail(500,"用户名不能为空");
        }
        if ( password ==null ||"".equals(password)){
            return  BaseResponse.fail(500,"密码不能为空");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("username",username));
        Assert.notNull(user,"用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(password))){
            return  BaseResponse.fail(500,"密码不正确");
        }
        String jwt = jwtUtil.generateToken(user.getUsername());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        Map map = new HashMap();
        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(user,userProfile);
        map.put("user",userProfile);
//        获取menu
        List<Menu> menus=menuService.selectCurrentUserMenuTree(username);
        map.put("menus",menus);
     return  BaseResponse.success(map);
    }

    @ApiOperation(value = "退出登录")
    @RequiresAuthentication
    @GetMapping("/logout")
    public BaseResponse logout(){
        SecurityUtils.getSubject().logout();
        return BaseResponse.success();
    }

    @GetMapping("/")
    public BaseResponse index(){
        return BaseResponse.success("超超越越管理系统即将开发完成....");
    }


}
