package com.ccyy.manage.dao;

import com.ccyy.manage.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户所拥有的所有角色
     */
    Set<String> selectRoleNameByUserName(@Param("username") String username);

    /**
     * 获取用户所拥有的操作权限
     */
    Set<String> selectOperatorPermsByUserName(@Param("username") String username);

}
