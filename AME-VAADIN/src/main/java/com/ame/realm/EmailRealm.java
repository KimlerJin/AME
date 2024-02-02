//package com.ame.realm;
//
//import com.ame.meperframework.core.enums.AuthType;
//import com.ame.meperframework.suites.appbase.sdk.ConfigurationConstant;
//import com.ame.meperframework.suites.appbase.sdk.base.filter.EntityFilter;
//import com.ame.meperframework.suites.appbase.sdk.domain.Configuration;
//import com.ame.meperframework.suites.appbase.sdk.domain.User;
//import com.ame.meperframework.suites.appbase.sdk.entity.UserEntity;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IConfigurationService;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserAuthService;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserDataPermissionMapService;
//import com.ame.meperframework.suites.appbase.sdk.service.api.IUserService;
//import com.ame.meperframework.suites.appbase.web.security.UserInfo;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//
//import javax.mail.NoSuchProviderException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import java.time.ZonedDateTime;
//import java.util.Properties;
//
//public class EmailRealm extends AbstractAuthenticatingRealm {
//
//    @Autowired
//    @Lazy
//    private IConfigurationService configurationService;
//
//
//    @Autowired
//    @Lazy
//    private IUserService userService;
//
//    @Autowired
//    @Lazy
//    private IUserAuthService authService;
//
//    @Autowired
//    @Lazy
//    private IUserDataPermissionMapService dataPermissionService;
//
//
//    private Transport transport;
//
//    private String host;
//
//    private String port;
//
//    private static Logger logger = LoggerFactory.getLogger(EmailRealm.class);
//
//    @Override
//    protected void onInit() {
//        super.onInit();
//        Configuration smtpHost = configurationService.getByNameAndCategory(ConfigurationConstant.CFG_SMTP_HOST,
//                ConfigurationConstant.CATEGORY_SYSTEM_MAIL);
//
//        Configuration smtpPort = configurationService.getByNameAndCategory(ConfigurationConstant.CFG_SMTP_PORT,
//                ConfigurationConstant.CATEGORY_SYSTEM_MAIL);
//
//        if (smtpHost != null && smtpPort != null) {
//            host = smtpHost.getValue();
//            port = smtpPort.getValue();
//            Properties props = new Properties();
//            props.put("mail.smtp.host", host);
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.port", port);
//            Session session = Session.getDefaultInstance(props);
//            session.setDebug(false);
//            try {
//                transport = session.getTransport("smtp");
//            } catch (NoSuchProviderException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//    }
//
//    public EmailRealm() {
//        setCredentialsMatcher(new AllowAllCredentialsMatcher());
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
//        String mailAddress = usernamePasswordToken.getUsername();
//        char[] password = usernamePasswordToken.getPassword();
//        boolean validate = false;
//        if (transport != null && StringUtils.isNotEmpty(host)) {
//            try {
//                transport.connect(host, mailAddress, new String(password));
//                if (transport.isConnected()) {
//                    transport.close();
//                    validate = true;
//                }
//                transport.close();
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//        if (validate) {
//            EntityFilter filter = userService.createFilter();
//            filter.fieldEqualTo(UserEntity.EMAIL, mailAddress.toLowerCase());
//            User user = userService.getByFilter(filter);
//            if (user == null) {
//                if (mailAddress.contains("@") && mailAddress.split("@").length > 0) {
//                    String[] mailArgs = mailAddress.split("@");
//                    user = authService.synchronizeEmailUser(mailAddress, mailArgs[0], new String(password));
//                } else {
//                    throw new AuthenticationException("Email Account Access Failed");
//                }
//
//            }
//            super.validate(user);
//            UserInfo userInfo = new UserInfo();
//            userInfo.setAuthType(AuthType.EMAIL);
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
//        } else {
//            throw new AuthenticationException("Email Account Access Failed");
//        }
//    }
//}
