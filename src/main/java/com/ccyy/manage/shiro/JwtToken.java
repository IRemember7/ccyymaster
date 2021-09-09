package com.ccyy.manage.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lianghanmao
 * @date 2021年07月22日 上午10:41
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
