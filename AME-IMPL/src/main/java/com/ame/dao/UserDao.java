package com.ame.dao;

import com.ame.entity.UserEntity;
import com.ame.filter.sql.SimpleSqlQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public class UserDao extends BaseEntityDao<UserEntity>{

    public int getUserCount() {
        SimpleSqlQuery simpleSqlQuery = new SimpleSqlQuery("SYS_USER");
        return baseDao.countBySqlQuery(simpleSqlQuery);
    }
}
