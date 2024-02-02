package com.ame.shiro;

import com.ame.UserInfo;
import com.ame.token.AccessToken;
import com.ame.utils.WebUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

public class TokenBasedAuthenticationFilter extends AuthenticationFilter {

    // private static Logger LOGGER = LoggerFactory.getLogger(TokenBasedAuthenticationFilter.class);

    private String anonymousToken;

    public TokenBasedAuthenticationFilter(String anonymousToken) {
        this.anonymousToken = anonymousToken;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        AccessToken accessToken = WebUtils.getToken((HttpServletRequest) request);
        boolean authenticated = subject.isAuthenticated() && subject.getPrincipal() != null;
        if (authenticated) {
            UserInfo userInfo = (UserInfo) subject.getPrincipal();
            // 当其他应用登出且再次登陆后,token会变化,这时需要判断如果token的用户名变了后,需要把当前回话过期,并刷新页面
            if (accessToken != null && accessToken.getTokenInfo() != null
                    && !Objects.equals(accessToken.getTokenInfo().getUserName(), userInfo.getUsername())) {
                throw new TokenChangedException();
            }
        }
        if (accessToken == null) {
            if (isLoginRequest(request, response) || isRefererLoginRequest(request, response)) {
                return true;
            } else {
                throw new TokenMissedException();
            }
        } else if (accessToken.getTokenInfo() == null
                && !Objects.equals(accessToken.getAccessToken(), anonymousToken)) {
            if (isLoginRequest(request, response) || isRefererLoginRequest(request, response)) {
                return true;
            } else {
                throw new TokenMissedException();
            }
        }

        if (!authenticated) {
            try {
                subject.login(accessToken);
            } catch (AuthenticationException e) {
                // 当时登陆页面的ajax请求,且登陆失败时不需要做处理,主要解决getCurrentUserName在不登陆有token时无法返回正确用户名
                if (isRefererLoginRequest(request, response)) {
                    return true;
                }
                throw e;
            }
        }
        if (isLoginRequest(request, response)) {
            org.apache.shiro.web.util.WebUtils.issueRedirect(request, response, getSuccessUrl());
            return false;
        }
        return true;
    }

    protected boolean isRefererLoginRequest(ServletRequest request, ServletResponse response) {
        if (!WebUtils.isAjax((HttpServletRequest) request)) {
            return false;
        }
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        String referer = httpServletRequest.getHeader("Referer");
        if (referer != null) {
            int index = referer.indexOf("?");
            if (index > 0) {
                referer = referer.substring(0, index);
            }
            if (referer.contains(getLoginUrl())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws IOException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        Subject subject = getSubject(request, response);
        if (existing instanceof AuthenticationException) {
            if (WebUtils.isAjax(httpServletRequest)) {
                ((HttpServletResponse) response).sendError(401, existing.getClass().getName());
            } else {
                if (subject.getSession(false) != null) {
                    subject.getSession(false).stop();
                }
                if (existing instanceof TokenChangedException) {
                    String requestURI = httpServletRequest.getRequestURI();
                    String contextPath = httpServletRequest.getContextPath();
                    int index = requestURI.indexOf(contextPath);
                    org.apache.shiro.web.util.WebUtils.issueRedirect(request, response, requestURI.substring(index));
                } else {
                    String requestURI = httpServletRequest.getRequestURI();
                    String loginUrl = super.getLoginUrl() + "?path=" + requestURI;
                    org.apache.shiro.web.util.WebUtils.issueRedirect(request, response, loginUrl);
                }
            }
        }
    }
}
