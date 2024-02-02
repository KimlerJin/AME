package com.ame.shiro.listener;


import com.ame.SecurityConstants;
import com.ame.spring.BeanManager;
import com.ame.token.TokenInfo;
import com.ame.token.TokenRepository;
import com.google.common.base.Strings;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;


@WebListener
public class TokenExpiredListener implements HttpSessionListener {

    private TokenRepository tokenRepository;

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String token = (String) session.getAttribute(SecurityConstants.TOKEN_NAME);
        if (!Strings.isNullOrEmpty(token)) {
            TokenInfo tokenInfo = getTokenRepository().get(token);
            if (tokenInfo != null) {
                getTokenRepository().delete(tokenInfo);
            }
        }
    }

    private TokenRepository getTokenRepository() {
        if (tokenRepository == null) {
            tokenRepository = BeanManager.getService(TokenRepository.class);
        }
        return tokenRepository;
    }
}
