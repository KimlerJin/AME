package com.ame.shiro;

import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ame.security.common")
public class SecurityConfigurationProperties {

    private String loginUrl = "/static/login.html";

    private String successUrl = "/";

    private String unauthorizedUrl = "/";

    private String anonymousToken = "23c5b471-d51b-4fd1-a755-c1e9d5907671";

    public String getAnonymousToken() {
        return anonymousToken;
    }

    public void setAnonymousToken(String anonymousToken) {
        this.anonymousToken = anonymousToken;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public static class CookieConfig {
        private String name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;

        private int maxAge = SimpleCookie.DEFAULT_MAX_AGE;

        private String domain;

        private String path;

        private boolean secure;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(int maxAge) {
            this.maxAge = maxAge;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isSecure() {
            return secure;
        }

        public void setSecure(boolean secure) {
            this.secure = secure;
        }
    }

}
