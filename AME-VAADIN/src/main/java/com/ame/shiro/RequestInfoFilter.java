package com.ame.shiro;


import com.ame.SessionHelper;
import com.ame.UserInfo;
import com.ame.core.RequestInfo;
import com.ame.entity.UserEntity;
import com.ame.service.IUserService;
import com.ame.spring.BeanManager;
import com.ame.utils.WebUtils;
import com.google.common.base.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.OncePerRequestFilter;


import java.io.IOException;
import java.util.Locale;

public class RequestInfoFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException{
        try {
            RequestInfo requestInfo = new RequestInfo();
            RequestInfo.set(requestInfo);

            String ip = WebUtils.getOriginalClientIp((HttpServletRequest) request);
            requestInfo.setUserIpAddress(ip);

            Object principal = SecurityUtils.getSubject().getPrincipal();
            if (principal instanceof UserInfo) {
                UserInfo userInfo = (UserInfo) principal;
                requestInfo.setUserId(userInfo.getUserId());
                requestInfo.setUserName(userInfo.getUsername());
                requestInfo.setDataPermissionList(userInfo.getDataPermissionList());
                requestInfo.setDataPermissionCode(userInfo.getDataPermissionCode());
                UserEntity user = BeanManager.getService(IUserService.class).getById(userInfo.getUserId());
                if (user != null) {
                    requestInfo.setUserFirstName(user.getFirstName());
                    requestInfo.setUserLastName(user.getLastName());
                }

            }

            if (requestInfo.getUserLocal() == null) {
                requestInfo.setUserLocal(request.getLocale());
            }

            requestInfo.setUserZoneId(WebUtils.getZoneId((HttpServletRequest) request));

            chain.doFilter(request, response);
        } finally {
            RequestInfo.set(null);
        }
    }

}
