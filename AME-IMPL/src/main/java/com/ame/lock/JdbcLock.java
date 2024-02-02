package com.ame.lock;

import com.ame.core.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class JdbcLock implements Lock {

    protected static final Logger log = LoggerFactory.getLogger(JdbcLock.class);

    private final static ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR =
        new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {

            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private ThreadGroup threadGroup = new ThreadGroup("JdbcLock-heartbeat");

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(threadGroup, r, "thread-" + threadNumber.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        });

    private final LockDao lockDao;

    private final String path;

    private ScheduledFuture<?> scheduledFuture = null;

    public JdbcLock(LockDao lockDao, String path) {
        this.lockDao = lockDao;
        this.path = path;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lock() {
        while (!tryLock()) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new PlatformException(e);
            }
        }
    }

    @Override
    public boolean tryLock() {
        // log.info("trying to lock {}", path);
        boolean acquired = this.lockDao.acquire(this.path);
        if (acquired) {
            scheduledFuture = SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(() -> {
                lockDao.heartBeat(path);
            }, 5, 5, TimeUnit.SECONDS);
        }
        return acquired;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
        try {
            this.lockDao.release(this.path);
        } catch (Exception e) {
            throw e;
        } finally {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

}
