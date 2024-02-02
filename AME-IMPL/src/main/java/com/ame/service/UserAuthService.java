package com.ame.service;


import com.ame.dao.BaseEntityDao;
import com.ame.dao.UserAuthDao;
import com.ame.entity.UserAuthEntity;
import com.ame.enums.AuthType;
import com.ame.filter.EntityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthService extends AbstractBaseEntityService<UserAuthEntity> implements IUserAuthService {

    @Autowired
    private UserAuthDao dao;

    @Override
    public UserAuthEntity getByAccessIdAndAuthType(String accessId, AuthType authType) {
        EntityFilter filter = createFilter();
        filter.fieldEqualTo(UserAuthEntity.ACCESS_ID, accessId);
        filter.fieldEqualTo(UserAuthEntity.AUTH_TYPE,authType.getName());
        return dao.getByFilter(filter);
    }

    @Override
    public UserAuthEntity getByUserIdAndAuthType(long userId, AuthType authType) {
        EntityFilter filter =  createFilter();
        filter.fieldEqualTo(UserAuthEntity.USER_ID, userId);
        filter.fieldEqualTo(UserAuthEntity.AUTH_TYPE,authType.getName());
        return dao.getByFilter(filter);
    }

    @Override
    public List<UserAuthEntity> listByUserId(long userId) {
        EntityFilter filter =  createFilter();
        filter.fieldEqualTo(UserAuthEntity.USER_ID, userId);
        return dao.listByFilter(filter);
    }


    @Override
    public void clearCacheManually() {

    }


    @Override
    protected BaseEntityDao<UserAuthEntity> getDao() {
        return dao;
    }
}
