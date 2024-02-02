package com.ame.token;

import java.time.ZonedDateTime;

public class TokenInfo {
    private long userId;
    private boolean isMultiLogin;
    private String userName;
    private String token;
    private String ip;
    private String osName;
    private String deviceType;
    private String browserVersion;
    private String browserName;
    private String browserType;
    private ZonedDateTime issueTime;
    private ZonedDateTime expiredTime;

    public boolean isMultiLogin() {
        return isMultiLogin;
    }

    public void setMultiLogin(boolean multiLogin) {
        isMultiLogin = multiLogin;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public ZonedDateTime getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(ZonedDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public ZonedDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(ZonedDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
}