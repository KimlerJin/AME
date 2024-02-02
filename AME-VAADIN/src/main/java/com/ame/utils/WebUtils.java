package com.ame.utils;


import com.ame.SecurityConstants;
import com.ame.spring.BeanManager;
import com.ame.token.AccessToken;
import com.ame.token.TokenInfo;
import com.ame.token.TokenRepository;
import com.google.common.base.Strings;
import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import java.time.ZoneId;

public abstract class WebUtils extends org.springframework.web.util.WebUtils {

    public static AccessToken getToken(HttpServletRequest httpServletRequest) {
        AccessToken uuidToken = (AccessToken) httpServletRequest.getAttribute(AccessToken.class.getName());
        if (uuidToken != null) {
            return uuidToken;
        }
        String accessToken = getTokenString(httpServletRequest);
        if (!Strings.isNullOrEmpty(accessToken)) {
            TokenRepository tokenRepository = BeanManager.getService(TokenRepository.class);

            TokenInfo userAuthToken = tokenRepository.get(accessToken);
            uuidToken = new AccessToken(accessToken, userAuthToken);
            httpServletRequest.setAttribute(AccessToken.class.getName(), uuidToken);
        }
        return uuidToken;
    }

    public static String getTokenString(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SecurityConstants.TOKEN_NAME);
        if (Strings.isNullOrEmpty(token)) {
            Cookie cookie = getCookie(httpServletRequest, SecurityConstants.TOKEN_NAME);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    public static ZoneId getZoneId(HttpServletRequest httpServletRequest) {
        Cookie cookie = getCookie(httpServletRequest, SecurityConstants.ZONED_ID_NAME);
        try {
            if (cookie != null) {
                return ZoneId.of(cookie.getValue());
            }
        } catch (Exception e) {
            //低版本浏览器，如IE浏览器,拿不到cookie里面的值或者cookie里的值为空字符串，那么就用默认的ZoneId吧
            return ZoneId.systemDefault();
        }
        return ZoneId.systemDefault();
    }

    public static boolean isAjax(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("X-Requested-With") != null
                && httpServletRequest.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest");
    }

    public static boolean isBrowser(HttpServletRequest httpServletRequest) {
        UserAgent userAgent = getUserAgent(httpServletRequest);
        return userAgent.getBrowser().getBrowserType().equals(BrowserType.WEB_BROWSER)
                || userAgent.getBrowser().getBrowserType().equals(BrowserType.MOBILE_BROWSER);
    }

    public static UserAgent getUserAgent(HttpServletRequest httpServletRequest) {
        UserAgent userAgent = (UserAgent) httpServletRequest.getAttribute(SecurityConstants.USER_AGENT_NAME);
        if (userAgent == null) {
            String header = httpServletRequest.getHeader("User-Agent");
            userAgent = UserAgent.parseUserAgentString(header);
            httpServletRequest.setAttribute(SecurityConstants.USER_AGENT_NAME, userAgent);
        }
        return userAgent;
    }

    public static String getOriginalClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


}
