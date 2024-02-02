package com.ame.realm;


import com.ame.entity.UserEntity;
import com.ame.enums.UserStatusType;
import com.ame.exception.StopAuthenticatingException;
import org.apache.shiro.realm.AuthenticatingRealm;

public abstract class AbstractAuthenticatingRealm extends AuthenticatingRealm {

    protected void validate(UserEntity user) {
        if (user != null && user.getStatus().equals(UserStatusType.LOCKED.getName())) {
            throw new StopAuthenticatingException(
                    "The account is locked and cannot be logged in!");
        }
        if (user != null && user.getStatus().equals(UserStatusType.INACTIVE.getName())) {
            throw new StopAuthenticatingException(
                    "The account is inactive and cannot be logged in!");
        }
    }
}
