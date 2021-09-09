package com.ccyy.manage.utils;

import com.ccyy.manage.model.UserProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author lianghanmao
 * @date 2021年07月27日 上午10:34
 * 获取shiro对象
 */
public class ShiroUtil {
    public static UserProfile getUserProfile(){
        return (UserProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
