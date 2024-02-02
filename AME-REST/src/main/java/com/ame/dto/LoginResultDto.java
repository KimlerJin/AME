package com.ame.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class LoginResultDto {

    private String accessToken;
    @JsonSerialize(using = ToStringSerializer.class)
    private long userId;
    private String userName;

    public String getAccessToken() {
        return accessToken;
    }

    public LoginResultDto setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public LoginResultDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public LoginResultDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
