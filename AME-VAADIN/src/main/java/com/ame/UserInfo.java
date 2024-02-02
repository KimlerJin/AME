package com.ame;


import com.ame.enums.AuthType;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 5344837603156145460L;

    private long userId;

    private String username;

    private String firstName;

    private String lastName;

    private ZonedDateTime loginTime;

    private AuthType authType;

    /**
     * 当前用户的所有数据权限列表
     */
    private List<String> dataPermissionList = new ArrayList<>();

    /**
     * 当前用户所在部门的数据权限代码
     */
    private String dataPermissionCode;

    private boolean fromNewLogin;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(ZonedDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public List<String> getDataPermissionList() {
        return dataPermissionList;
    }

    public void setDataPermissionList(List<String> dataPermissionList) {
        this.dataPermissionList = dataPermissionList;
    }

    public String getDataPermissionCode() {
        return dataPermissionCode;
    }

    public void setDataPermissionCode(String dataPermissionCode) {
        this.dataPermissionCode = dataPermissionCode;
    }

    public boolean isFromNewLogin() {
        return fromNewLogin;
    }

    public void setFromNewLogin(boolean fromNewLogin) {
        this.fromNewLogin = fromNewLogin;
    }

    public String getShowName(Locale locale) {
        if (locale != null && locale.equals(Locale.CHINA)) {
            return lastName + " " + firstName;
        } else {
            return firstName + " " + lastName;
        }
    }
}
