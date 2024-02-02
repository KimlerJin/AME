package com.ame.token;

import org.apache.shiro.authc.AuthenticationToken;

public class AccessToken implements AuthenticationToken {

    /**
     * 
     */
    private static final long serialVersionUID = 2367525879195953935L;
    private String accessToken;
    private TokenInfo tokenInfo;

    public AccessToken(String accessToken, TokenInfo tokenInfo) {
        this.accessToken = accessToken;
        this.tokenInfo = tokenInfo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

}
