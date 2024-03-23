package com.ame.config;

import com.ame.realm.JdbcRealm;
import com.ame.shiro.*;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.event.EventBus;
import org.apache.shiro.event.support.DefaultEventBus;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;


@Configuration
@EnableConfigurationProperties(value = SecurityConfigurationProperties.class)
public class ShiroConfig {

    @Autowired
    private SecurityConfigurationProperties securityConfigurationProperties;


    @Bean
    public EventBus eventBus() {
        return new DefaultEventBus();
    }


    @Bean
    public AMERealmAuthorizer ameRealmAuthorizer() {
        return new AMERealmAuthorizer();
    }

    @Bean
    public AMERealmAuthenticator ameRealmAuthenticator(List<Realm> realms) {
        return new AMERealmAuthenticator(realms);
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(ObjectProvider<ShiroFilterChainDefinitionCustomizer> shiroFilterChainDefinitionCustomizers) {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "requestInfo");
        chainDefinition.addPathDefinition("/offline-stub.html", "anon");
        chainDefinition.addPathDefinition("/**/*.js", "anon");
        chainDefinition.addPathDefinition("/**/*.js", "anon");
        chainDefinition.addPathDefinition("/**/*.css", "anon");
        chainDefinition.addPathDefinition("/**/*.png", "anon");
        chainDefinition.addPathDefinition("/ws/**", "requestInfo");
        chainDefinition.addPathDefinition("/static/**", "requestInfo");
        chainDefinition.addPathDefinition("/rest/security/**", "requestInfo");
        chainDefinition.addPathDefinition("/VAADIN/push", "anon");
        chainDefinition.addPathDefinition("/", "requestInfo");
        chainDefinition.addPathDefinition("/**", "requestInfo, authc");
//        chainDefinition.addPathDefinition("/hello", "authc, requestInfo");
//        chainDefinition.addPathDefinition("/about", "authc, requestInfo");
//        chainDefinition.addPathDefinition("/upload", "authc, requestInfo");
        shiroFilterChainDefinitionCustomizers.orderedStream().forEach(shiroFilterChainDefinitionCustomizer -> {
            shiroFilterChainDefinitionCustomizer.config(chainDefinition);
        });
        return chainDefinition;
    }


    @Bean
    @ConditionalOnMissingBean
    public SessionsSecurityManager securityManager(List<Realm> realms, List<AuthenticationListener> listeners, EventBus eventBus) {

        AMERealmAuthenticator lumosRealmAuthenticator = new AMERealmAuthenticator(realms);
        lumosRealmAuthenticator.setAuthenticationListeners(listeners);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(new CustomSessionStorageEvaluator());

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setSubjectFactory(new CustomWebSubjectFactory());
        securityManager.setAuthenticator(lumosRealmAuthenticator);
        securityManager.setAuthorizer(new AMERealmAuthorizer());
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setRealms(realms);
        securityManager.setSessionManager(new ServletContainerSessionManager());
        securityManager.setEventBus(eventBus);
        return securityManager;
    }

    @Bean(name = "filterShiroFilterRegistrationBean")
    @Order(1)
    public FilterRegistrationBean<AbstractShiroFilter>
    filterShiroFilterRegistrationBean(@Qualifier("shiroFilter") Object shiroFilter) throws Exception {
        FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilter);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,
                                              ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setLoginUrl(securityConfigurationProperties.getLoginUrl());
        filterFactoryBean.setSuccessUrl(securityConfigurationProperties.getSuccessUrl());
        filterFactoryBean.setUnauthorizedUrl(securityConfigurationProperties.getUnauthorizedUrl());

        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        filterFactoryBean.getFilters().put("requestInfo", new RequestInfoFilter());
        filterFactoryBean.getFilters().put("authc",
                new TokenBasedAuthenticationFilter(securityConfigurationProperties.getAnonymousToken()));
        return filterFactoryBean;
    }


    @Bean
    public JdbcRealm jdbcRealm() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashedCredentialsMatcher.setHashIterations(1024);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
        return new JdbcRealm(hashedCredentialsMatcher);
    }
}
