package com.ame.service;


import com.ame.dao.BaseEntityDao;
import com.ame.dao.UserAuthTokenDao;
import com.ame.entity.UserAuthTokenEntity;
import com.ame.filter.EntityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserAuthTokenService extends AbstractBaseEntityService<UserAuthTokenEntity>
    implements IUserAuthTokenService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;

    @Override
    protected BaseEntityDao<UserAuthTokenEntity> getDao() {
        return userAuthTokenDao;
    }

    @Override
    public UserAuthTokenEntity getByToken(String token) {
        EntityFilter entityFilter = createFilter().fieldEqualTo(UserAuthTokenEntity.TOKEN, token);
        return getByFilter(entityFilter);
    }

}
