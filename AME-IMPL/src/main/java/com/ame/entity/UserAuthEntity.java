package com.ame.entity;


import com.ame.annotation.Description;
import com.ame.service.IUserService;
import com.ame.spring.BeanManager;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

import java.time.ZonedDateTime;


/**
 * @author andrew_he
 * @date 2019/08/29
 */
@Description("用于存储用户的密码信息等")
@Entity
@Table(name = "SYS_USER_AUTH")
@Where(clause = "DELETED=0")
public class UserAuthEntity extends BaseEntity {
    public static final String SALT = "salt";
    public static final String SALT_HISTORY = "salt_history";
    public static final String PASSWORD_HISTORY = "passwordHistory";
    public static final String PASSWORD_START_USE_DATE = "passwordStartUseDate";
    public static final String ACCESS_ID = "accessId"; // 用户名
    public static final String PASSWORD = "password"; // 用户密码
    public static final String AUTH_TYPE = "authType"; // 登录类型 系统登录，第三方登录
    public static final String USER_ID = "userId"; // 用户id
    public static final String PSW_INPUT_FAILED_COUNT = "pswInputFailedCount"; // 密码输入错误次数
    /**
     *
     */
    private static final long serialVersionUID = 4137366459519917169L;
    @Description("用户的SALT字符串，用户和密码一起使用来得到用户的实际密码，系统录入，不用单独设置")
    @Column(name = "SALT")
    private String salt;

    @Description("过往的用户SALT值列表，以分好分割，存5个")
    @Column(name = "SALT_HISTORY")
    private String saltHistory;

    @Description("用户的密码的有效截止日期，超过该日期，用户将不能登录")
    @Column(name = "PASSWORD_START_USE_DATE")
    private ZonedDateTime passwordStartUseDate;

    @Description("过往的用户的密码列表，以分号分割，存5个")
    @Column(name = "PASSWORD_HISTORY")
    private String passwordHistory;

    @Description("用户的密码，存储时已经被加密")
    @Column(name = "PASSWORD")
    private String password;

    @Description("用户登录类型，系统登录；LDAP登录")
    @Column(name = "AUTH_TYPE")
    private String authType;

    @Description("用户ID")
    // @RelationFK(value = "UserEntity", col = "id")
    @Column(name = "USER_ID")
    private long userId;

    @Description("第三方用户名")
    @Column(name = "ACCESS_ID")
    private String accessId;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSaltHistory() {
        return saltHistory;
    }

    public void setSaltHistory(String saltHistory) {
        this.saltHistory = saltHistory;
    }

    public ZonedDateTime getPasswordStartUseDate() {
        return passwordStartUseDate;
    }

    public void setPasswordStartUseDate(ZonedDateTime passwordStartUseDate) {
        this.passwordStartUseDate = passwordStartUseDate;
    }

    public String getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(String passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public UserEntity getUser() {
        return BeanManager.getService(IUserService.class).getById(getUserId());
    }
}
