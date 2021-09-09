package com.ccyy.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccyy.manage.model.entity.Menu;
import com.ccyy.manage.dao.MenuMapper;
import com.ccyy.manage.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccyy.manage.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> selectMenuTreeVOByUsername(String username) {
        List<Menu> menus;
        if ("admin".equals(username)) {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("menu_id","parent_id","menu_name","url","perms","order_num","create_time","modify_time","icon");
            menus = menuMapper.selectList(queryWrapper);
        } else {
            menus = menuMapper.selectMenuByUserName(username);
        }
        return toTree(menus);
    }

    /**
     * 转换为树形结构
     */
    private List<Menu> toTree(List<Menu> menuList) {
        return TreeUtil.toTree(menuList, "menuId", "parentId", "children", Menu.class);
    }

    @Override
    public List<Menu> selectCurrentUserMenuTree(String username) {
        return selectMenuTreeVOByUsername(username);
    }
}
