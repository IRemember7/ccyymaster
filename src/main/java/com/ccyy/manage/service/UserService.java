package com.ccyy.manage.service;

import com.ccyy.manage.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
public interface UserService extends IService<User> {

     Set<String> selectRoleNameByUserName(String username);

    /**
     * 获取用户拥有的所有菜单权限和操作权限.
     *
     * @param username 用户名
     * @return 权限标识符号列表
     */
     Set<String> selectPermsByUsername(String username);

    /**
     * 编辑用户的角色组
     * @param id
     * @param userName
     * @param roleIds
     */
     void editUserRoles(Long id, String userName, Long[] roleIds);

    /**
     * 创建用户
     * @param user
     * @param roleIds
     */
     Long insertUser(User user, Long[] roleIds);

    /**
     * 重置密码
     * @param userId
     * @param password
     */
    void updatePasswordByUserId(Long userId, String password);

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    Boolean disableUserByID(Long userId);

    /**
     *
     * @param userId
     * @return
     */
    Boolean enableUserByID(Long userId);
}
