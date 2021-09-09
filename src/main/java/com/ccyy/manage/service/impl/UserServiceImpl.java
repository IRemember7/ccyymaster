package com.ccyy.manage.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ccyy.manage.dao.UserRoleMapper;
import com.ccyy.manage.model.entity.Menu;
import com.ccyy.manage.model.entity.User;
import com.ccyy.manage.dao.UserMapper;
import com.ccyy.manage.model.entity.UserRole;
import com.ccyy.manage.service.MenuService;
import com.ccyy.manage.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccyy.manage.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Set<String> selectRoleNameByUserName(String username) {
        return userMapper.selectRoleNameByUserName(username);
    }

    /**
     * 获取用户拥有的所有菜单权限和操作权限.
     *
     * @param username 用户名
     * @return 权限标识符号列表
     */
    @Override
    public Set<String> selectPermsByUsername(String username) {
        Set<String> perms = new HashSet<>();
        List<Menu> menuTreeVOS = menuService.selectMenuTreeVOByUsername(username);
        List<Menu> leafNodeMenuList = TreeUtil.getAllLeafNode(menuTreeVOS);
        for (Menu menu : leafNodeMenuList) {
            perms.add(menu.getPerms());
        }
        perms.addAll(userMapper.selectOperatorPermsByUserName(username));
        return perms;

    }

    @Override
    public void editUserRoles(Long id, String username, Long[] roleIds) {
        checkUserNameExistOnUpdate(id,username);
        grantRole(id,roleIds);
    }

    @Override
    public Long insertUser(User user, Long[] roleIds) {
        checkUserNameExistOnCreate(user.getUsername());
//        明文密码加密
        String encryptPassword = SecureUtil.md5(user.getPassword());
        user.setPassword(encryptPassword);
        userMapper.insert(user);
        grantRole(user.getId(),roleIds);
        return user.getId();

    }

    @Override
    public void updatePasswordByUserId(Long userId, String password) {
        String encryptPassword = SecureUtil.md5(password);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",userId);
        User user = userMapper.selectOne(userQueryWrapper);
        user.setPassword(encryptPassword);
        userMapper.updateById(user);
    }

    /**
     * 检查用户id和用户名是否有重复的
     */
    private void checkUserNameExistOnUpdate(Long id, String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.ne("id",id);
        Integer integer = userMapper.selectCount(queryWrapper);
        if (integer >0){
            throw new RuntimeException("用户名重复....");
        }

    }


    @Transactional
    public void grantRole(Long id, Long[] roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            throw new IllegalArgumentException("赋予的角色数组不能为空.");
        }
        // 清空原有的角色, 赋予新角色.
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        userRoleMapper.delete(queryWrapper);

        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    /**
     * 新增时校验用户名是否重复
     * @param username  用户名
     */
    public void checkUserNameExistOnCreate(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Integer integer = userMapper.selectCount(queryWrapper);
        if (integer > 0) {
            throw new RuntimeException("用户名重复....");
        }
    }

    @Override
    public Boolean disableUserByID(Long userId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",userId);
        User user = userMapper.selectOne(userQueryWrapper);
        user.setStatus(0);
        return userMapper.updateById(user)==1;
    }

    @Override
    public Boolean enableUserByID(Long userId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",userId);
        User user = userMapper.selectOne(userQueryWrapper);
        user.setStatus(1);
        return userMapper.updateById(user)==1;
    }
}
