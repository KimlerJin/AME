package com.ame.dto;

/**
 * Author:Tracy
 * Date:2020/11/11
 * Description:
 */
public class CurrentUserDto {
    private String name;

    private String employeeNo;

    private String photoBase64Code;

    private String photoType;

    public String getName() {
        return name;
    }

    public CurrentUserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public CurrentUserDto setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
        return this;
    }

    public String getPhotoBase64Code() {
        return photoBase64Code;
    }

    public CurrentUserDto setPhotoBase64Code(String photoBase64Code) {
        this.photoBase64Code = photoBase64Code;
        return this;
    }

    public String getPhotoType() {
        return photoType;
    }

    public CurrentUserDto setPhotoType(String photoType) {
        this.photoType = photoType;
        return this;
    }
}
