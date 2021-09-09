package com.ccyy.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccyy.manage.dao.RoleMenuMapper;
import com.ccyy.manage.dao.RoleOperatorMapper;
import com.ccyy.manage.model.entity.Role;
import com.ccyy.manage.dao.RoleMapper;
import com.ccyy.manage.model.entity.RoleMenu;
import com.ccyy.manage.model.entity.RoleOperator;
import com.ccyy.manage.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleOperatorMapper roleOperatorMapper;

    /**
     * 为角色分配菜单
     * @param roleId    角色 ID
     * @param menuIds   菜单 ID 数组
     */
    @Transactional
    @Override
    public void grantMenu(Long roleId, Long[] menuIds) {
//        清空之前的id
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
//        重新分配菜单
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    /**
     * 为角色分配操作权限
     * @param roleId    角色 ID
     * @param operatorIds   操作权限 ID 数组
     */
    @Transactional
    @Override
    public void grantOperator(Long roleId, Long[] operatorIds) {
        //        清空之前的id
        QueryWrapper<RoleOperator> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleOperatorMapper.delete(queryWrapper);
//        重新分配菜单
        for (Long operatorId : operatorIds) {
            RoleOperator roleOperator = new RoleOperator();
            roleOperator.setOperatorId(operatorId);
            roleOperator.setRoleId(roleId);
            roleOperatorMapper.insert(roleOperator);
        }
    }
}
