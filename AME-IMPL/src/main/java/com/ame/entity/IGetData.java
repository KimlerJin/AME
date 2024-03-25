package com.ame.entity;

import java.time.LocalDateTime;

public interface IGetData extends IData {

    String getPlatformId();

    long getCompanyId();

    String getDescription();

    void setDescription(String description);

    LocalDateTime getCreateTime();

    long getCreateUserId();

    String getCreateUserName();

    String getCreateUserFullName();

    String getCreateIp();

    String getDataPermissionPredicate();

    void setDataPermissionPredicate(String dataPermissionCode);

    /**
     * 内部使用
     *
     * @return
     */
    long getCreateBid();

    LocalDateTime getLastModifyTime();

    long getLastModifyUserId();

    String getLastModifyUserName();

    long getModifyBid();

    String getRowLogId();

    String getLastModifyUserFullName();

    String getLastModifyIp();

    LocalDateTime getDeleteTime();

    long getDeleteUserId();

    String getDeleteUserName();

    String getDeleteIp();

    boolean isDeleted();

    String getDeleteUserFullName();

    boolean isNew();

}
