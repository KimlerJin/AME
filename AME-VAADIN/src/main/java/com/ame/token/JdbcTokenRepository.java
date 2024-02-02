package com.ame.token;


import com.ame.entity.UserAuthTokenEntity;
import com.ame.filter.EntityFilter;
import com.ame.service.IUserAuthTokenService;
import eu.bitwalker.useragentutils.BrowserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "ame.security.token.repository", havingValue = "jdbc", matchIfMissing = true)
public class JdbcTokenRepository implements TokenRepository {

    @Autowired
    IUserAuthTokenService userAuthTokenService;

    @Override
    public void add(TokenInfo tokenInfo) {
        UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
        userAuthToken.setToken(tokenInfo.getToken());
        userAuthToken.setIp(tokenInfo.getIp());
        userAuthToken.setBrowserName(tokenInfo.getBrowserName());
        userAuthToken.setBrowserType(tokenInfo.getBrowserType());
        userAuthToken.setBrowserVersion(tokenInfo.getBrowserVersion());
        userAuthToken.setDeviceType(tokenInfo.getDeviceType());
        userAuthToken.setOsName(tokenInfo.getOsName());
        userAuthToken.setUserId(tokenInfo.getUserId());
        userAuthToken.setUserName(tokenInfo.getUserName());
        userAuthToken.setIssueTime(tokenInfo.getIssueTime());
        userAuthToken.setExpiredTime(tokenInfo.getExpiredTime());
        // 如果不允许多地登陆,则应该移除上次通ip的登陆token

        if (!tokenInfo.isMultiLogin()) {
            EntityFilter filter = userAuthTokenService.createFilter();
            // filter.fieldNotEqualTo(UserAuthTokenEntity.IP, userAuthToken.getIp());
            filter.fieldEqualTo(UserAuthTokenEntity.USER_NAME, userAuthToken.getUserName());
            filter.fieldEqualTo(UserAuthTokenEntity.BROWSER_TYPE, BrowserType.WEB_BROWSER.getName());
            List<UserAuthTokenEntity> userAuthTokens = userAuthTokenService.listByFilter(filter);
            if (!userAuthTokens.isEmpty()) {
                userAuthTokenService
                        .deleteByIds(userAuthTokens.stream().map(UserAuthTokenEntity::getId).collect(Collectors.toList()));
            }
        }
        userAuthTokenService.save(userAuthToken);
    }

    @Override
    public TokenInfo get(String token) {
        UserAuthTokenEntity byToken = userAuthTokenService.getByToken(token);
        if (byToken == null) {
            return null;
        }
        if (byToken.getExpiredTime() != null && ZonedDateTime.now().isAfter(byToken.getExpiredTime())) {
            userAuthTokenService.delete(byToken);
            return null;
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(byToken.getToken());
        tokenInfo.setIp(byToken.getIp());
        tokenInfo.setBrowserName(byToken.getBrowserName());
        tokenInfo.setBrowserType(byToken.getBrowserType());
        tokenInfo.setBrowserVersion(byToken.getBrowserVersion());
        tokenInfo.setDeviceType(byToken.getDeviceType());
        tokenInfo.setOsName(byToken.getOsName());
        tokenInfo.setUserId(byToken.getUserId());
        tokenInfo.setUserName(byToken.getUserName());
        tokenInfo.setIssueTime(byToken.getIssueTime());
        tokenInfo.setExpiredTime(byToken.getExpiredTime());
        return tokenInfo;
    }

    @Override
    public void delete(TokenInfo tokenInfo) {
        UserAuthTokenEntity byToken = userAuthTokenService.getByToken(tokenInfo.getToken());
        if (byToken != null) {
            userAuthTokenService.delete(byToken);
        }
    }
}
