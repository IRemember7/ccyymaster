package com.ccyy.manage.dao;

import com.ccyy.manage.model.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取某个用户的所拥有的导航菜单
     */
    List<Menu> selectMenuByUserName(@Param("userName") String userName);

}
