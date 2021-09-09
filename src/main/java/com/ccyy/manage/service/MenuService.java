package com.ccyy.manage.service;

import com.ccyy.manage.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectMenuTreeVOByUsername(String username);

    List<Menu> selectCurrentUserMenuTree(String username);
}
