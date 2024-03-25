package com.ame.entity;


import java.time.LocalDateTime;

public interface IBaseEntity extends ISetDataId, IGetData {

    void setCompanyId(long companyId);

    void setPlatformId(String platformId);

    void setCreateTime(LocalDateTime createTime);

    void setCreateUserId(long createUserId);

    void setCreateUserName(String createUserName);

    void setCreateUserFullName(String createUserFullName);

    void setCreateIp(String createIp);

    void setDataPermissionPredicate(String dataPermissionPredicate);

    /**
     * 内部使用
     *
     * @return
     */
    void setCreateBid(long creationBid);

    void setLastModifyTime(LocalDateTime lastModifyTime);

    void setLastModifyUserId(long lastModifyUserId);

    void setLastModifyUserName(String lastModifyUserName);

    void setModifyBid(long modifyBid);

    void setRowLogId(String rowLogId);

    void setLastModifyUserFullName(String lastModifyUserFullName);

    void setLastModifyIp(String lastModifyIp);

    void setDeleteTime(LocalDateTime deleteTime);

    void setDeleteUserId(long deleteUserId);

    void setDeleteUserName(String deleteUserName);

    void setDeleteIp(String deleteIp);

    void setDeleted(boolean deleted);

    void setDeleteUserFullName(String deleteUserFullName);

}
