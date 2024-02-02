package com.ame.core;
import com.mysql.cj.util.StringUtils;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class RequestInfo implements Serializable {

    private static final long serialVersionUID = 175617063315068881L;
    private static ThreadLocal<RequestInfo> requestInfoThreadLocal = new ThreadLocal<>();
    private long userId;
    private long operatorGroupId;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userIpAddress;
    private Locale userLocal;
//    private AuthType authType;
    private String dataPermissionCode;
    private String userOpenStyle;
    private String dataSource;
    private boolean ignoreDataPermission = false;

    /**
     * Data Permission Code list
     */
    private List<String> dataPermissionList;
    private ZoneId userZoneId = ZoneId.systemDefault();
    private ZonedDateTime requestZonedDateTime = ZonedDateTime.now();
    private Map<String, Object> properties = new HashMap<>();

    /**
     * 公司ID用户多租户和多工作空间
     */
    private long companyId;

    public static RequestInfo current() {
        return requestInfoThreadLocal.get();
    }

    public static void set(RequestInfo requestInfo) {
        requestInfoThreadLocal.set(requestInfo);
    }

    public static ZoneId getCurrentZoneId() {
        RequestInfo current = RequestInfo.current();
        return current == null ? ZoneId.systemDefault() : current.getUserZoneId();
    }

    public static Locale getCurrentLocale() {
        return RequestInfo.current() == null ? Locale.getDefault() : RequestInfo.current().getUserLocal();
    }

    public String getUserOpenStyle() {
        return userOpenStyle;
    }

    public void setUserOpenStyle(String userOpenStyle) {
        this.userOpenStyle = userOpenStyle;
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserIpAddress() {
        return userIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        this.userIpAddress = userIpAddress;
    }

    public Locale getUserLocal() {
        return userLocal;
    }

    public void setUserLocal(Locale userLocal) {
        this.userLocal = userLocal;
    }

    public ZoneId getUserZoneId() {
        return userZoneId;
    }

    public void setUserZoneId(ZoneId userZoneId) {
        this.userZoneId = userZoneId;
    }

    public ZonedDateTime getRequestZonedDateTime() {
        return requestZonedDateTime;
    }

    public void setRequestZonedDateTime(ZonedDateTime requestZonedDateTime) {
        this.requestZonedDateTime = requestZonedDateTime;
    }

    public List<String> getDataPermissionList() {
        return dataPermissionList;
    }

    public void setDataPermissionList(List<String> dataPermissionCodeList) {
        this.dataPermissionList = dataPermissionCodeList;
    }

    public String getDataPermissionCode() {
        return dataPermissionCode;
    }

    public void setDataPermissionCode(String dataPermissionCode) {
        this.dataPermissionCode = dataPermissionCode;
    }

    public long getOperatorGroupId() {
        return operatorGroupId;
    }

    public void setOperatorGroupId(long operatorGroupId) {
        this.operatorGroupId = operatorGroupId;
    }

//    public AuthType getAuthType() {
//        return authType;
//    }
//
//    public void setAuthType(AuthType authType) {
//        this.authType = authType;
//    }


    public boolean isIgnoreDataPermission() {
        return ignoreDataPermission;
    }

    public void setIgnoreDataPermission(boolean ignoreDataPermission) {
        this.ignoreDataPermission = ignoreDataPermission;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public RequestInfo clone() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserId(this.userId);
//        requestInfo.setAuthType(this.authType);
        requestInfo.setOperatorGroupId(this.operatorGroupId);
        requestInfo.setUserIpAddress(this.userIpAddress);
        requestInfo.setUserFirstName(this.userFirstName);
        requestInfo.setUserLastName(this.userLastName);
        requestInfo.setUserName(this.userName);
        requestInfo.setUserLocal(this.userLocal);
        requestInfo.setUserZoneId(this.userZoneId);
        requestInfo.setDataPermissionCode(this.dataPermissionCode);
        requestInfo.setDataPermissionList(this.dataPermissionList);
        requestInfo.setDataSource(this.dataSource);
        requestInfo.setIgnoreDataPermission(this.ignoreDataPermission);
        requestInfo.setRequestZonedDateTime(ZonedDateTime.now());
        requestInfo.setCompanyId(this.companyId);
        return requestInfo;
    }

    public void execute(Executor taskExecutor, Runnable runnable) {
        taskExecutor.execute(() -> {
            RequestInfo requestInfo = RequestInfo.this.clone();
            try {
                RequestInfo.set(requestInfo);
                runnable.run();
            } finally {
                RequestInfo.set(null);
            }
        });
    }

    public void execute(Runnable runnable) {
        RequestInfo current = RequestInfo.current();
        try {
            RequestInfo.set(this);
            runnable.run();
        } finally {
            RequestInfo.set(current);
        }
    }

    public <R> R changeDataSource(String dataSource, Supplier<R> supplier) {
        if (!StringUtils.isNullOrEmpty(dataSource)) {
            RequestInfo current = RequestInfo.current();
            String previousDataSource = current.getDataSource();
            try {
                current.setDataSource(dataSource);
                return supplier.get();
            } finally {
                current.setDataSource(previousDataSource);
            }

        } else {
            return supplier.get();
        }
    }

}
