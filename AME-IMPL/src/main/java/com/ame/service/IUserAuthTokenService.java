package com.ame.service;


import com.ame.entity.UserAuthTokenEntity;


public interface IUserAuthTokenService extends IBaseEntityService<UserAuthTokenEntity> {

    UserAuthTokenEntity getByToken(String token);

}
