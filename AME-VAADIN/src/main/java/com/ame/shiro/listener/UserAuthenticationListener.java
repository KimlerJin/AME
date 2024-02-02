package com.ame.shiro.listener;


import com.ame.SecurityConstants;
import com.ame.UserInfo;
import com.ame.entity.UserEntity;
import com.ame.enums.UserStatusType;
import com.ame.service.IUserService;
import com.ame.spring.BeanManager;
import com.ame.token.AccessToken;
import com.ame.token.TokenInfo;
import com.ame.token.TokenRepository;
import com.ame.utils.WebUtils;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class UserAuthenticationListener implements AuthenticationListener {

//    @Autowired
//    @Lazy
//    private IUserAuthHistoryService userAuthHistoryService;

    // @Inject
    // IUserAuthTokenService userAuthTokenService;

    @Autowired
    @Lazy
    private TokenRepository tokenRepository;

    @Autowired
    @Lazy
    private IUserService userSevrice;



    @Value("${ame.userauth.password.failed.count:3}")
    private int passwordFailedCount;

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        UserInfo userInfo = (UserInfo) info.getPrincipals().getPrimaryPrincipal();

        UserEntity user = userSevrice.getById(userInfo.getUserId());
        Subject subject = SecurityUtils.getSubject();

        if (user != null) {
            HttpServletRequest httpServletRequest = org.apache.shiro.web.util.WebUtils.getHttpRequest(subject);
            HttpServletResponse httpServletResponse = org.apache.shiro.web.util.WebUtils.getHttpResponse(subject);
            UserAgent userAgent = WebUtils.getUserAgent(httpServletRequest);
            String originalClientIp = WebUtils.getOriginalClientIp(httpServletRequest);

            if (token instanceof AccessToken) {
                String accessToken = ((AccessToken) token).getAccessToken();
                Cookie cookie = new Cookie(SecurityConstants.TOKEN_NAME, accessToken);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                httpServletResponse.addCookie(cookie);
                httpServletResponse.setHeader(SecurityConstants.TOKEN_NAME, accessToken);
            } else if (token instanceof UsernamePasswordToken) {

                // 创建认证记录
//                UserAuthHistory userAuthHistory = new UserAuthHistory();
//                userAuthHistory.setIp(originalClientIp);
//                userAuthHistory.setAuthTime(ZonedDateTime.now());
//                userAuthHistory.setBrowserName(userAgent.getBrowser().getName());
//                userAuthHistory.setBrowserType(userAgent.getBrowser().getBrowserType().getName());
//                if (userAgent.getBrowserVersion() != null) {
//                    userAuthHistory.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
//                }
//                userAuthHistory.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
//                userAuthHistory.setOsName(userAgent.getOperatingSystem().getName());
//                userAuthHistory.setUserId(user.getId());
//                userAuthHistory.setUserName(user.getUserName());
//                userAuthHistory.setLoginSuccess(true);
//                userAuthHistory.setUserLoginStatus(user.getStatus());
//                userAuthHistoryService.save(userAuthHistory);

                // 创建token记录
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setMultiLogin(user.getMultiLogin());
                tokenInfo.setToken(UUID.randomUUID().toString());
                tokenInfo.setIp(originalClientIp);
                tokenInfo.setBrowserName(userAgent.getBrowser().getName());
                tokenInfo.setBrowserType(userAgent.getBrowser().getBrowserType().getName());
                if (userAgent.getBrowserVersion() != null) {
                    tokenInfo.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
                }
                tokenInfo.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
                tokenInfo.setOsName(userAgent.getOperatingSystem().getName());
                tokenInfo.setUserId(user.getId());
                tokenInfo.setUserName(user.getUserName());
                tokenInfo.setIssueTime(ZonedDateTime.now());
                // 如果不是浏览器请求,则应该设置token的过期时间
                if (!WebUtils.isBrowser(httpServletRequest)) {
                    tokenInfo.setExpiredTime(tokenInfo.getIssueTime().plus(7, ChronoUnit.DAYS));
                }
                // 如果不允许多地登陆,则应该移除上次通ip的登陆token
                tokenRepository.add(tokenInfo);

                // token 写到httpServletRequest里,用于后续的使用
                httpServletRequest.setAttribute(SecurityConstants.TOKEN_NAME, tokenInfo.getToken());

                // 如果是浏览器请求,则应该设置session的过期时间
                if (WebUtils.isBrowser(httpServletRequest)) {

                    // 将token写到session
                    httpServletRequest.getSession().setAttribute(SecurityConstants.TOKEN_NAME, tokenInfo.getToken());
                }

                // 将token写到cookie
                Cookie cookie = new Cookie(SecurityConstants.TOKEN_NAME, tokenInfo.getToken());
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                httpServletResponse.addCookie(cookie);

                // 将token写到response header
                httpServletResponse.setHeader(SecurityConstants.TOKEN_NAME, tokenInfo.getToken());
            }
        }
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        Subject subject = SecurityUtils.getSubject();
        if (token instanceof UsernamePasswordToken) {
            String username = ((UsernamePasswordToken) token).getUsername();
            UserEntity user = userSevrice.getByName(username);
            if (user == null) {
                return;
            }
//            int pswInputFailedCount =
//                    BeanManager.getService(IUserAuthHistoryService.class).getContinuousFailureCount(user);
//            if (pswInputFailedCount >= passwordFailedCount) {
//                user.setStatus(UserStatusType.LOCKED.getName());
//            }
            HttpServletRequest httpServletRequest = org.apache.shiro.web.util.WebUtils.getHttpRequest(subject);
            UserAgent userAgent = WebUtils.getUserAgent(httpServletRequest);
            String originalClientIp = WebUtils.getOriginalClientIp(httpServletRequest);

            // 创建认证记录
//            UserAuthHistory userAuthHistory = new UserAuthHistory();
//            userAuthHistory.setIp(originalClientIp);
//            userAuthHistory.setAuthTime(ZonedDateTime.now());
//            userAuthHistory.setBrowserName(userAgent.getBrowser().getName());
//            userAuthHistory.setBrowserType(userAgent.getBrowser().getBrowserType().getName());
//            if (userAgent.getBrowserVersion() != null) {
//                userAuthHistory.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
//            }
//            userAuthHistory.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
//            userAuthHistory.setOsName(userAgent.getOperatingSystem().getName());
//            userAuthHistory.setUserId(user.getId());
//            userAuthHistory.setUserName(user.getUserName());
//            userAuthHistory.setLoginSuccess(false);
//            userAuthHistory.setUserLoginStatus(user.getStatus());
//            userAuthHistoryService.save(userAuthHistory);
            userSevrice.save(user);
        }
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest httpServletRequest = org.apache.shiro.web.util.WebUtils.getHttpRequest(subject);
        HttpServletResponse httpServletResponse = org.apache.shiro.web.util.WebUtils.getHttpResponse(subject);

        AccessToken token = WebUtils.getToken(httpServletRequest);
        if (token != null && token.getTokenInfo() != null) {
            tokenRepository.delete(token.getTokenInfo());
        }

        Cookie cookie = new Cookie(SecurityConstants.TOKEN_NAME, "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);

//        UserLogoutEvent userLogoutEvent = new UserLogoutEvent(userInfo.getUsername(), userInfo.getFirstName(),
//                userInfo.getLastName(), ZonedDateTime.now());
//        BeanManager.getService(EventBus.class).publishAsynchronous(userLogoutEvent);
    }

}
