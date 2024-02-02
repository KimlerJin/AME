package com.ame.entity;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jdk.jfr.Description;
import org.springframework.beans.BeanUtils;

import java.time.ZonedDateTime;

@Table(name = "SYS_USER")
@Entity
public class UserEntity extends BaseEntity{


    public static final String NAME = "userName";
    public static final String STATUS = "status";

    public static final String LAST_LOGIN_TIME = "lastLoginTime";
    public static final String EMAIL = "email";
    public static final String DEPARTMENT_CODE = "departmentCode";
    public static final String POSITION_CODE = "positionCode";
    public static final String MANAGER_NAME = "managerName";
    public static final String MANAGER_ID = "managerId";

    public static final String WORK_PHONE = "workPhone";
    public static final String CELL_PHONE = "cellPhone";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PRIMARY_DATA_PERMISSION_CODE = "primaryDataPermissionCode";
    public static final String IMAGE_ID = "imageId";
    public static final String MULTI_LOGIN = "multiLogin";

    public static final String EC_0 = "ec_0";
    public static final String EC_1 = "ec_1";
    public static final String EC_2 = "ec_2";
    public static final String EC_3 = "ec_3";
    public static final String EC_4 = "ec_4";
    public static final String EC_5 = "ec_5";
    public static final String EC_6 = "ec_6";
    public static final String EC_7 = "ec_7";
    public static final String EC_8 = "ec_8";
    public static final String EC_9 = "ec_9";
    public static final String EC_10 = "ec_10";
    public static final String EC_11 = "ec_11";
    public static final String EC_12 = "ec_12";

    private static final long serialVersionUID = 5717661657767968093L;

    @Description("用户的名称，全局唯一")
    @Column(name = "USER_NAME")
    private String userName;

    @Description("该用户的状态:ACTIVE,INACTIVE,LOCKED")
    @Column(name = "STATUS")
    private String status;

    @Description("上次用户登录的时间")
    @Column(name = "LAST_LOGIN_TIME")
    private ZonedDateTime lastLoginTime;

    @Description("用户登录的时间")
    @Column(name = "LOGIN_TIME")
    private ZonedDateTime loginTime;

    @Description("用户的名字")
    @Column(name = "FIRST_NAME", length = 255)
    private String firstName;

    @Description("用户的姓氏")
    @Column(name = "LAST_NAME", length = 255)
    private String lastName;

    @Description("用户的邮件地址")
    @Column(name = "EMAIL")
    private String email;

    @Description("用户的部门编码")
    @Column(name = "DEPARTMENT_CODE", length = 255)
    private String departmentCode;

    @Description("用户的职位代码")
    @Column(name = "POSITION_CODE", length = 255)
    private String positionCode;

    @Description("用户的经理名称")
    @Column(name = "MANAGER_NAME", length = 255)
    private String managerName;

    @Description("用户的经理ID")
    @Column(name = "MANAGER_ID")
    private long managerId = -1;

    @Description("用户的工作电话")
    @Column(name = "WORK_PHONE", length = 255)
    private String workPhone;

    @Description("用户的手机号码")
    @Column(name = "CELL_PHONE", length = 255)
    private String cellPhone;

    @Description("用户主数据权限代码")
    @Column(name = "PRIMARY_DATA_PERMISSION_CODE", length = 255)
    private String primaryDataPermissionCode;

    @Description("用户头像的媒体文件ID, 取自Media对象的外键")
    @Column(name = "IMAGE_ID")
    private long imageId = -1;

    @Description("登录密码输入错误次数")
    @Column(name = "PSW_INPUT_FAILED_COUNT")
    private int pswInputFailedCount;

    @Description("是否允许多地登录")
    @Column(name = "MULTI_LOGIN")
    private boolean multiLogin = false;

    @Description("微信企业号")
    @Column(name = "WECHAT_ACCOUNT")
    private String wechatAccount;

    @Description("班次")
    @Column(name = "SHIFT_ID")
    private Long shiftId;

    @Description("此字段为临时属性，密码相关操作可参考 UserAuthEntity ")
    @Transient
    private String password;

    @Override
    public UserEntity clone() {
        UserEntity newUserEntity = new UserEntity();
        BeanUtils.copyProperties(this, newUserEntity, ID, LAST_LOGIN_TIME, EXTENSION_ENTITY);
        return newUserEntity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(ZonedDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public ZonedDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(ZonedDateTime loginTime) {
        this.loginTime = loginTime;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPrimaryDataPermissionCode() {
        return primaryDataPermissionCode;
    }

    public void setPrimaryDataPermissionCode(String primaryDataPermissionCode) {
        this.primaryDataPermissionCode = primaryDataPermissionCode;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public int getPasswordInputFailedCount() {
        return pswInputFailedCount;
    }

    public void setPasswordInputFailedCount(int pswInputFailedCount) {
        this.pswInputFailedCount = pswInputFailedCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getMultiLogin() {
        return multiLogin;
    }

    public void setMultiLogin(boolean multiLogin) {
        this.multiLogin = multiLogin;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    @Description("预留Sting类型字段EC_0")
    @Column(name = "EC_0", length = 80)
    private String ec_0;

    @Description("预留Sting类型字段EC_1")
    @Column(name = "EC_1", length = 80)
    private String ec_1;

    @Description("预留Sting类型字段EC_2")
    @Column(name = "EC_2", length = 80)
    private String ec_2;

    @Description("预留Sting类型字段EC_3")
    @Column(name = "EC_3", length = 80)
    private String ec_3;

    @Description("预留Sting类型字段EC_4")
    @Column(name = "EC_4", length = 80)
    private String ec_4;

    @Description("预留Sting类型字段EC_5")
    @Column(name = "EC_5", length = 80)
    private String ec_5;

    @Description("预留Sting类型字段EC_6")
    @Column(name = "EC_6", length = 80)
    private String ec_6;

    @Description("预留Sting类型字段EC_7")
    @Column(name = "EC_7", length = 80)
    private String ec_7;

    @Description("预留Sting类型字段EC_8")
    @Column(name = "EC_8", length = 80)
    private String ec_8;

    @Description("预留Sting类型字段EC_9")
    @Column(name = "EC_9", length = 80)
    private String ec_9;

    @Description("预留ZonedDateTime类型字段EC_10")
    @Column(name = "EC_10")
    private ZonedDateTime ec_10;

    @Description("预留ZonedDateTime类型字段EC_11")
    @Column(name = "EC_11")
    private ZonedDateTime ec_11;

    @Description("预留ZonedDateTime类型字段EC_12")
    @Column(name = "EC_12")
    private ZonedDateTime ec_12;

    public ZonedDateTime getEc_10() {
        return ec_10;
    }

    public void setEc_10(ZonedDateTime ec_10) {
        this.ec_10 = ec_10;
    }

    public ZonedDateTime getEc_11() {
        return ec_11;
    }

    public void setEc_11(ZonedDateTime ec_11) {
        this.ec_11 = ec_11;
    }

    public ZonedDateTime getEc_12() {
        return ec_12;
    }

    public void setEc_12(ZonedDateTime ec_12) {
        this.ec_12 = ec_12;
    }

    public String getEc_0() {
        return ec_0;
    }

    public void setEc_0(String ec_0) {
        this.ec_0 = ec_0;
    }

    public String getEc_1() {
        return ec_1;
    }

    public void setEc_1(String ec_1) {
        this.ec_1 = ec_1;
    }

    public String getEc_2() {
        return ec_2;
    }

    public void setEc_2(String ec_2) {
        this.ec_2 = ec_2;
    }

    public String getEc_3() {
        return ec_3;
    }

    public void setEc_3(String ec_3) {
        this.ec_3 = ec_3;
    }

    public String getEc_4() {
        return ec_4;
    }

    public void setEc_4(String ec_4) {
        this.ec_4 = ec_4;
    }

    public String getEc_5() {
        return ec_5;
    }

    public void setEc_5(String ec_5) {
        this.ec_5 = ec_5;
    }

    public String getEc_6() {
        return ec_6;
    }

    public void setEc_6(String ec_6) {
        this.ec_6 = ec_6;
    }

    public String getEc_7() {
        return ec_7;
    }

    public void setEc_7(String ec_7) {
        this.ec_7 = ec_7;
    }

    public String getEc_8() {
        return ec_8;
    }

    public void setEc_8(String ec_8) {
        this.ec_8 = ec_8;
    }

    public String getEc_9() {
        return ec_9;
    }

    public void setEc_9(String ec_9) {
        this.ec_9 = ec_9;
    }

    /**
     * 只是对象简单的复制，不支持关联关系的复制
     *
     * @return
     */
//    @Override
//    public UserEntity clone() {
//        UserEntity newUserEntity = new UserEntity();
//        BeanUtils.copyProperties(this, newUserEntity, ID, LAST_LOGIN_TIME, EXTENSION_ENTITY);
//        CustomizedTableRecord dynamicEntity = this.getExtensionEntity();
//        if (dynamicEntity != null && dynamicEntity.getColumns().size() > 0) {
//            newUserEntity.setExtensionEntity(new CustomizedTableRecord(dynamicEntity.getEntityName()));
//            dynamicEntity.getColumns().forEach((k, v) -> {
//                newUserEntity.getExtensionEntity().set(k, v);
//            });
//            newUserEntity.getExtensionEntity().setObjectId(0l);
//            newUserEntity.getExtensionEntity().setId(0l);
//        }
//        return newUserEntity;
//    }
}
