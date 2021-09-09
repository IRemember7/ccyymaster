package com.ccyy.manage.service;

import com.ccyy.manage.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
public interface RoleService extends IService<Role> {

    void grantMenu(Long roleId, Long[] menuIds);

    void grantOperator(Long roleId, Long[] operatorIds);
}
