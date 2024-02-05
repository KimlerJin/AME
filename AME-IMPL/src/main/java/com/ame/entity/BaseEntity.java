package com.ame.entity;


import com.ame.hibernate.AMEEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jdk.jfr.Description;


import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AMEEntityListener.class)
public abstract class BaseEntity extends Entity implements IBaseEntity {

    public static final String PLATFORM_ID = "platformId";
    public static final String COMPANY_ID = "companyId";
    public static final String DESCRIPTION = "description";
    public static final String CREATE_TIME = "createTime";
    public static final String CREATE_USER_ID = "createUserId";
    public static final String CREATE_USER_NAME = "createUserName";
    public static final String CREATE_USER_FULL_NAME = "createUserFullName";
    public static final String CREATE_IP = "createIp";
    public static final String DTS_CREATION_BID = "createBid";
    public static final String LM_USER_ID = "lastModifyUserId";
    public static final String LM_USER_NAME = "lastModifyUserName";
    public static final String LM_USER_FULL_NAME = "lastModifyUserFullName";
    public static final String LM_TIME = "lastModifyTime";
    public static final String LM_IP = "lastModifyIp";
    public static final String DTS_MODIFIED_BID = "modifiedBid";
    public static final String DELETE_TIME = "deleteTime";
    public static final String DELETE_USER_ID = "deleteUserId";
    public static final String DELETE_USER_NAME = "deleteUserName";
    public static final String DELETE_USER_FULL_NAME = "deleteUserFullName";
    public static final String DELETE_IP = "deleteIp";
    public static final String ROW_LOG_ID = "rowLogId";
    public static final String DELETED = "deleted";
    public static final String DATA_PERMISSION_PREDICATE = "dataPermissionPredicate";
    public static final String EXTENSION_ENTITY = "extensionEntity";
    private static final long serialVersionUID = -3508702684001460301L;

    @Description("公有字段，平台的ID，暂时保留")
    @Column(name = "PLATFORM_ID")
    private String platformId;

    @Description("公有字段，公司的ID，主要为了云端多租户环境使用，在单用户情况下，该字段保留")
    @Column(name = "COMPANY_ID")
    private long companyId;

    @Description("公有字段，可以放入任意描述，供用户使用")
    @Column(name = "DESCRIPTION")
    private String description;

    @Description("公有字段，表示对象的创建时间，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "CREATE_TIME")
    private ZonedDateTime createTime;

    @Description("公有字段，表示对象的创建用户的ID，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "CREATE_USER_ID")
    private long createUserId;

    @Description("公有字段，表示对象的创建用户的用户名，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    @Description("公有字段，表示对象的创建用户的全名称，含名和姓，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "CREATE_USER_FULL_NAME")
    private String createUserFullName;

    @Description("公有字段，表示对象的创建用户的IP地址，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "CREATE_IP")
    private String createIp;

    @Description("公有字段，创建对象时的系统Build ID，用于后续数据迁移使用，对于系统运行没有影响，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DTS_CREATION_BID")
    private long createBid;

    @Description("公有字段，表示对象上一次修改时的时间，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "LM_TIME")
    private ZonedDateTime lastModifyTime;

    @Description("公有字段，表示对象的上次修改时的用户的ID，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "LM_USER_ID")
    private long lastModifyUserId;

    @Description("公有字段，表示对象的上次修改的用户名称，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "LM_USER_NAME")
    private String lastModifyUserName;

    @Description("公有字段，表示对象的上次修改的用户的全名，包含名和姓，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "LM_USER_FULL_NAME")
    private String lastModifyUserFullName;

    @Description("公有字段，表示对象的上次修改人员的IP地址，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "LM_IP")
    private String lastModifyIp;

    @Description("公有字段，上次修改对象时的系统Build ID，用于后续数据迁移使用，对于系统运行没有影响，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DTS_MODIFIED_BID")
    private long modifyBid;

    @Description("公有字段，表示对象被删除时的时间，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DELETE_TIME")
    private ZonedDateTime deleteTime;

    @Description("公有字段，表示对象被删除用户的ID，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DELETE_USER_ID")
    private long deleteUserId;

    @Description("公有字段，表示对象被删除用户的名称，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DELETE_USER_NAME")
    private String deleteUserName;

    @Description("公有字段，表示对象被删除用户的全名，包含名和姓，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DELETE_USER_FULL_NAME")
    private String deleteUserFullName;

    @Description("公有字段，表示对象被删除用户的IP，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "DELETE_IP")
    private String deleteIp;

    @Description("公有字段，内部使用，该字段会被系统自动放入，不允许用户修改。")
    @Column(name = "ROW_LOG_ID")
    private String rowLogId;

    @Description("公有字段，表示该对象是否被删除，该字段会被系统自动放入，不允许用户修改。<p>0表示该数据存在，！=0表示删除。如果该数据删除，将会把DELETED置为该行数据ID的值。\n"
            + "举例说明：新建一个user，名字叫‘张三’，deleted为0，id为16461354651335。如果把数据删除，则deleted的值变更为16461354651335\n</P>")
    @Column(name = "DELETED")
    private long deleted;

    @Description("公有字段，表示数据权限，该字段会被系统自动放入，不允许用户修改")
    @Column(name = "DATA_PERMISSION_PREDICATE")
    private String dataPermissionPredicate;

//    @Transient
//    @JSONField(serialize = false)
//    private CustomizedTableRecord extensionEntity = null;

    @Override
    public boolean isNew() {
        return getId() <= 0;
    }

    @Override
    public String getPlatformId() {
        return platformId;
    }

    @Override
    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    @Override
    public long getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public long getCreateUserId() {
        return createUserId;
    }

    @Override
    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String getCreateUserName() {
        return createUserName;
    }

    @Override
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public String getCreateUserFullName() {
        return createUserFullName;
    }

    @Override
    public void setCreateUserFullName(String createUserFullName) {
        this.createUserFullName = createUserFullName;
    }

    @Override
    public String getCreateIp() {
        return createIp;
    }

    @Override
    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    @Override
    public long getCreateBid() {
        return createBid;
    }

    @Override
    public void setCreateBid(long createBid) {
        this.createBid = createBid;
    }

    @Override
    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    @Override
    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public long getLastModifyUserId() {
        return lastModifyUserId;
    }

    @Override
    public void setLastModifyUserId(long lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }

    @Override
    public String getLastModifyUserName() {
        return lastModifyUserName;
    }

    @Override
    public void setLastModifyUserName(String lastModifyUserName) {
        this.lastModifyUserName = lastModifyUserName;
    }

    @Override
    public String getLastModifyUserFullName() {
        return lastModifyUserFullName;
    }

    @Override
    public void setLastModifyUserFullName(String lastModifyUserFullName) {
        this.lastModifyUserFullName = lastModifyUserFullName;
    }

    @Override
    public String getLastModifyIp() {
        return lastModifyIp;
    }

    @Override
    public void setLastModifyIp(String lastModifyIp) {
        this.lastModifyIp = lastModifyIp;
    }

    @Override
    public long getModifyBid() {
        return modifyBid;
    }

    @Override
    public void setModifyBid(long modifyBid) {
        this.modifyBid = modifyBid;
    }

    @Override
    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    @Override
    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public long getDeleteUserId() {
        return deleteUserId;
    }

    @Override
    public void setDeleteUserId(long deleteUserId) {
        this.deleteUserId = deleteUserId;
    }

    @Override
    public String getDeleteUserName() {
        return deleteUserName;
    }

    @Override
    public void setDeleteUserName(String deleteUserName) {
        this.deleteUserName = deleteUserName;
    }

    @Override
    public String getDeleteUserFullName() {
        return deleteUserFullName;
    }

    @Override
    public void setDeleteUserFullName(String deleteUserFullName) {
        this.deleteUserFullName = deleteUserFullName;
    }

    @Override
    public String getDeleteIp() {
        return deleteIp;
    }

    @Override
    public void setDeleteIp(String deleteIp) {
        this.deleteIp = deleteIp;
    }

    @Override
    public String getRowLogId() {
        return rowLogId;
    }

    @Override
    public void setRowLogId(String rowLogId) {
        this.rowLogId = rowLogId;
    }

    @Override
    public boolean isDeleted() {
        return deleted != 0;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = getId();
    }

    @Override
    public String getDataPermissionPredicate() {
        return dataPermissionPredicate;
    }

    @Override
    public void setDataPermissionPredicate(String dataPermissionPredicate) {
        this.dataPermissionPredicate = dataPermissionPredicate;
    }

//    public CustomizedTableRecord getExtensionEntity() {
//        return extensionEntity;
//    }
//
//    public void setExtensionEntity(CustomizedTableRecord extensionEntity) {
//        this.extensionEntity = extensionEntity;
//    }

}
