package com.ccyy.manage.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccyy.manage.model.UserProfile;
import com.ccyy.manage.model.entity.User;
import com.ccyy.manage.service.UserService;
import com.ccyy.manage.utils.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author lianghanmao
 * @date 2021年07月22日 上午10:23
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    /**
     * 判断token是否为jwt token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof  JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

//        获取token
        String token = principalCollection.toString();
        //        获取username
        String username = jwtUtil.getClaimByToken(token).getSubject();
//         权限赋值
        Set<String> roles = userService.selectRoleNameByUserName(username);
        Set<String> perms = userService.selectPermsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(perms);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        获取token
        JwtToken jwtToken = (JwtToken) authenticationToken;
//        获取username
        String username = jwtUtil.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
//        获取当前user
        QueryWrapper<User> queryWrapper =new QueryWrapper();
        queryWrapper.eq("username",username);
        User user = userService.getOne(queryWrapper);
//        判定用户是否存在
        if (user == null){
            throw new UnknownAccountException("用户不存在");
        }
        //        判定用户是否激活
        if (user.getStatus() ==0){
            throw new LockedAccountException("该用户已经被锁定");
        }
//        返回用户基本信息
        UserProfile userProfile = new UserProfile();
        BeanUtil.copyProperties(user,userProfile);
        return new SimpleAuthenticationInfo(userProfile,jwtToken.getCredentials(),getName());
    }

    /**
     * 超级管理员拥有所有权限
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        User user = (User) principals.getPrimaryPrincipal();
        return "admin".equals(user.getUsername()) || super.isPermitted(principals, permission);
    }

    /**
     * 超级管理员拥有所有角色
     */
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        User user = (User) principals.getPrimaryPrincipal();
        return "admin".equals(user.getUsername()) || super.hasRole(principals, roleIdentifier);
    }
}
