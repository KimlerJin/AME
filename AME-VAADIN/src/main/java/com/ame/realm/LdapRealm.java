//package com.ame.realm;
//
//import com.ame.meperframework.core.enums.AuthType;
//import com.ame.meperframework.suites.appbase.sdk.domain.User;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserAuthService;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserDataPermissionMapService;
//import com.ame.meperframework.suites.appbase.web.config.security.LdapProperties;
//import com.ame.meperframework.suites.appbase.web.security.UserInfo;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.ldaptive.Credential;
//import org.ldaptive.LdapException;
//import org.ldaptive.auth.*;
//import org.ldaptive.pool.PooledConnectionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//
//import java.time.ZonedDateTime;
//
///**
// * Ldap 认证
// */
//public class LdapRealm extends AbstractAuthenticatingRealm {
//
//    @Autowired
//    @Lazy
//    private IUserAuthService authService;
//
//    @Autowired
//    @Lazy
//    private IUserDataPermissionMapService dataPermissionService;
//
//    @Autowired
//    private PooledConnectionFactory pooledConnectionFactory;
//
//    @Autowired
//    private LdapProperties ldapProperties;
//
//    private Authenticator authenticator;
//
//    public LdapRealm() {
//        setCredentialsMatcher(new AllowAllCredentialsMatcher());
//    }
//
//    @Override
//    protected void onInit() {
//        super.onInit();
//        FormatDnResolver formatDnResolver = new FormatDnResolver();
//        formatDnResolver.setFormat(ldapProperties.getFormatDn());
//        PooledBindAuthenticationHandler authHandler = new PooledBindAuthenticationHandler(pooledConnectionFactory);
//        authenticator = new Authenticator(formatDnResolver, authHandler);
//    }
//
//    /**
//     * 登录时调用
//     */
//    @Override
//    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
//        String username = usernamePasswordToken.getUsername();
//        char[] password = usernamePasswordToken.getPassword();
//        User user;
//        try {
//            AuthenticationResponse response =
//                    authenticator.authenticate(new AuthenticationRequest(username, new Credential(password)));
//            if (response.getResult()) {
//                user = authService.synchronizeLdapUser(username, new String(password));
//            } else {
//                throw new AuthenticationException("LDAP authentication failed.");
//            }
//        } catch (LdapException e) {
//            throw new AuthenticationException("LDAP authentication failed.", e);
//        }
//        if (user != null) {
//            super.validate(user);
//
//            UserInfo userInfo = new UserInfo();
//            userInfo.setAuthType(AuthType.LDAP);
//            userInfo.setUserId(user.getId());
//            userInfo.setUsername(user.getUserName());
//            userInfo.setFirstName(user.getFirstName());
//            userInfo.setLastName(user.getLastName());
//            userInfo.setLoginTime(ZonedDateTime.now());
//            userInfo.setDataPermissionList(dataPermissionService.listDataPermission(user.getId()));
//            userInfo.setDataPermissionCode(user.getPrimaryDataPermissionCode());
//
//            SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
//            simplePrincipalCollection.add(userInfo, getName());
//
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
//            simpleAuthenticationInfo.setPrincipals(simplePrincipalCollection);
//            return simpleAuthenticationInfo;
//        }
//        return null;
//    }
//
//}
