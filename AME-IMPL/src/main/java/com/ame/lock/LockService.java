package com.ame.lock;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.locks.Lock;

/**
 * 内部使用的基于数据库实现的分布式全局锁
 */
public class LockService {

    @Autowired
    private LockDao lockDao;

    /**
     * 获取锁对象
     *
     * @param lockKey
     * @return
     */
    public Lock obtain(String lockKey) {
        lockDao.createIfNotExist(lockKey);
        return new JdbcLock(lockDao, lockKey);
    }
}
