package com.ame.realm;

import com.ame.UserInfo;
import com.ame.entity.UserAuthEntity;
import com.ame.entity.UserEntity;
import com.ame.enums.AuthType;
import com.ame.service.IUserAuthService;
import com.google.common.base.Strings;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.time.ZonedDateTime;
import java.util.Base64;

public class JdbcRealm extends AbstractAuthenticatingRealm {

    @Autowired
    @Lazy
    private IUserAuthService userAuthService;


    // @Autowired
    // private IUserService userService;

    public JdbcRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        UserAuthEntity userAuth =
                userAuthService.getByAccessIdAndAuthType(usernamePasswordToken.getUsername(), AuthType.SYSTEM);
        if (userAuth == null) {
            throw new AuthenticationException("System authentication failed.");
        }
        UserEntity user = userAuth.getUser();
        super.validate(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setAuthType(AuthType.SYSTEM);
        userInfo.setUserId(user.getId());
        userInfo.setUsername(user.getUserName());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setLoginTime(ZonedDateTime.now());
        userInfo.setDataPermissionCode(user.getPrimaryDataPermissionCode());
        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
        simplePrincipalCollection.add(userInfo, getName());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        simpleAuthenticationInfo.setCredentials(userAuth.getPassword());
        simpleAuthenticationInfo
                .setCredentialsSalt(ByteSource.Util.bytes(Base64.getDecoder().decode(userAuth.getSalt())));
        simpleAuthenticationInfo.setPrincipals(simplePrincipalCollection);
        return simpleAuthenticationInfo;
    }

}
