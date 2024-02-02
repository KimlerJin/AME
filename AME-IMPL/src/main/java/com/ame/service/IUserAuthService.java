package com.ame.service;

import com.ame.entity.UserAuthEntity;
import com.ame.entity.UserEntity;
import com.ame.enums.AuthType;

import java.util.List;


public interface IUserAuthService extends  IBaseEntityService<UserAuthEntity> {

    UserAuthEntity getByAccessIdAndAuthType(String accessId, AuthType authType);

    UserAuthEntity getByUserIdAndAuthType(long userId, AuthType authType);

    List<UserAuthEntity> listByUserId(long userId);


}
