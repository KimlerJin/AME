package com.ame.lock;


import com.ame.dao.Dao;
import com.ame.uidgenerator.util.DateUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

public class LockDao  extends Dao {

    protected static final Logger log = LoggerFactory.getLogger(LockDao.class);

    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setPlatformTransactionManager(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void release(String lockName) {
        getHibernateTool().execute(session -> {
            LockEntity lockEntity = session.get(LockEntity.class, lockName);
            lockEntity.setLocked(false);
            lockEntity.setLockedTime(null);
            session.save(lockEntity);
            session.flush();
            return null;
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createIfNotExist(String lockName) {
        getHibernateTool().execute(session -> {
            LockEntity lockEntity;
            // 查询锁对象，会一直尝试创建直到能查询到锁对象
            while ((lockEntity = session.get(LockEntity.class, lockName)) == null) {
                try {
                    lockEntity = new LockEntity();
                    lockEntity.setLockName(lockName);
                    lockEntity.setLocked(false);
                    lockEntity.setLockedTime(null);
                    session.save(lockEntity);
                    session.flush();
                } catch (Exception e) {
                    log.debug(e.getMessage());
                }
            }
            return null;
        });
    }

    public void heartBeat(String lockName) {
        transactionTemplate.execute(status -> getHibernateTool().execute(session -> {
            Query query = session
                .createQuery("update LockEntity set heartbeatTime = current_timestamp where lockName = :lockName");
            // NativeQuery nativeQuery = session.createNativeQuery("update SYS_LOCK set HEART_BEAT_TIME = :heartBeatTime
            // where LOCK_NAME = :lockName");
            query.setParameter("lockName", lockName);
            query.executeUpdate();
            return null;
        }));
    }

    public boolean acquire(String lockName) {
        return transactionTemplate.execute(status -> getHibernateTool().execute(session -> {
            // 采取乐观锁方式获取全局锁对象
            Query query =
                session.createQuery("select t, current_timestamp from LockEntity t where lockName = :lockName");
            query.setParameter("lockName", lockName);
            Object[] res = ((Object[])query.getSingleResult());
            LockEntity lockEntity = (LockEntity)res[0];
            Date currentDatabaseTime = (Date)res[1];
            if (lockEntity.isLocked() && (lockEntity.getHeartbeatTime() == null
                || DateUtils.addSeconds(lockEntity.getHeartbeatTime(), 30).after(currentDatabaseTime))) {
                return false;
            } else {

                try {
                    lockEntity.setLocked(true);
                    lockEntity.setLockedTime(currentDatabaseTime);
                    lockEntity.setHeartbeatTime(currentDatabaseTime);
                    session.saveOrUpdate(lockEntity);
                    session.flush();
                    return true;
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    status.setRollbackOnly();
                    return false;
                }
            }
        }));
    }

}
