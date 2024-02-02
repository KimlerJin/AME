package com.ame.entity;
import com.ame.annotation.Description;
import com.ame.entity.BaseEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Entity
@Table(name = "SYS_USER_AUTH_TOKEN")
@Cacheable
public class UserAuthTokenEntity extends BaseEntity {

    private static final long serialVersionUID = 7197805498099551075L;

    public static String USER_ID = "userId";
    public static String USER_NAME = "userName";
    public static String TOKEN = "token";
    public static String IP = "ip";
    public static String OS_NAME = "osName";
    public static String DEVICE_TYPE = "deviceType";
    public static String BROWSER_VERSION = "browserVersion";
    public static String BROWSER_NAME = "browserName";
    public static String BROWSER_TYPE = "browserType";
    public static String ISSUE_TIME = "issueTime";
    public static String EXPIRED_TIME = "expiredTime";
    public static String ADDITIONAL_PROPERTIES = "additionalProperties";

    @Column(name = "USER_ID")
    @Description("用户id")
    private long userId;

    @Column(name = "USER_NAME")
    @Description("用户name")
    private String userName;

    @Column(name = "TOKEN")
    @Description("token")
    private String token;

    @Column(name = "IP")
    @Description("客户端ip地址")
    private String ip;

    @Column(name = "OS_NAME")
    @Description("客户端操作系统名称")
    private String osName;

    @Column(name = "DEVICE_TYPE")
    @Description("客户端设备类型")
    private String deviceType;

    @Column(name = "BROWSER_VERSION")
    @Description("客户端版本")
    private String browserVersion;

    @Column(name = "BROWSER_NAME")
    @Description("客户端名称")
    private String browserName;

    @Column(name = "BROWSER_TYPE")
    @Description("客户端类型")
    private String browserType;

    @Column(name = "ISSUE_TIME")
    @Description("token颁发时间")
    private ZonedDateTime issueTime;

    @Column(name = "EXPIRED_TIME")
    @Description("过期时间")
    private ZonedDateTime expiredTime;

    @Column(name = "ADDITIONAL_PROPERTIES")
    @Description("额外的属性，以map的json字符串表示")
    private String additionalProperties;

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

    public String getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(String additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}