//package com.ame.realm;
//
//import com.ame.meperframework.core.enums.AuthType;
//import com.ame.meperframework.suites.appbase.sdk.domain.User;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserDataPermissionMapService;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserService;
//import com.ame.meperframework.suites.appbase.web.config.security.SecurityConfigurationProperties;
//import com.ame.meperframework.suites.appbase.web.security.TokenExpiredException;
//import com.ame.meperframework.suites.appbase.web.security.TokenMissedException;
//import com.ame.meperframework.suites.appbase.web.security.UserInfo;
//import com.ame.meperframework.suites.appbase.web.security.token.AccessToken;
//import com.ame.service.IUserService;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//
//import java.time.ZonedDateTime;
//import java.util.Objects;
//
//public class AccessTokenRealm extends AbstractAuthenticatingRealm {
//
//    @Autowired
//    @Lazy
//    private IUserService userService;
//
//    @Autowired
//    @Lazy
//    private IUserDataPermissionMapService dataPermissionService;
//
//    @Autowired
//    private SecurityConfigurationProperties securityConfigurationProperties;
//
//    public AccessTokenRealm() {
//        setAuthenticationTokenClass(AccessToken.class);
//        setCredentialsMatcher(new AllowAllCredentialsMatcher());
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
//            throws AuthenticationException{
//        AccessToken accessToken = (AccessToken) authenticationToken;
//        if (accessToken.getTokenInfo() == null
//                && !Objects.equals(securityConfigurationProperties.getAnonymousToken(), accessToken.getAccessToken())) {
//            throw new TokenMissedException();
//        }
//        if (accessToken.getTokenInfo() != null && accessToken.getTokenInfo().getExpiredTime() != null
//                && ZonedDateTime.now().isAfter(accessToken.getTokenInfo().getExpiredTime())) {
//            throw new TokenExpiredException();
//        }
//        User user;
//        if (accessToken.getTokenInfo() != null) {
//            user = userService.getByName(accessToken.getTokenInfo().getUserName());
//        } else {
//            user = new User();
//            user.setUserName("anonymous");
//        }
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(user.getId());
//        userInfo.setUsername(user.getUserName());
//        userInfo.setFirstName(user.getFirstName());
//        userInfo.setLastName(user.getLastName());
//
//        if (accessToken.getTokenInfo() != null) {
//            userInfo.setLoginTime(accessToken.getTokenInfo().getIssueTime());
//        }
//
//        userInfo.setAuthType(AuthType.ACCESS_TOKEN);
//        if (user.getId() > 0) {
//            userInfo.setDataPermissionList(dataPermissionService.listDataPermission(user.getId()));
//        }
//        userInfo.setDataPermissionCode(user.getPrimaryDataPermissionCode());
//        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
//        simplePrincipalCollection.add(userInfo, getName());
//
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
//        simpleAuthenticationInfo.setPrincipals(simplePrincipalCollection);
//        return simpleAuthenticationInfo;
//    }
//}
