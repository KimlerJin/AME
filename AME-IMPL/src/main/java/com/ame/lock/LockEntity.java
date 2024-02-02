package com.ame.lock;

import jakarta.persistence.*;
import org.hibernate.annotations.OptimisticLock;


import java.util.Date;

@Entity
@Table(name = "SYS_LOCK")
public class LockEntity {

    @Id
    @Column(name = "LOCK_NAME")
    private String lockName;

    @Column(name = "LOCKED")
    private boolean locked;

    @Column(name = "LOCKED_TIME")
    private Date lockedTime;

    @OptimisticLock(excluded = true)
    @Column(name = "HEART_BEAT_TIME")
    private Date heartbeatTime;

    @Column(name = "LOCKED_BY")
    private String lockedBy;

    @Version
    @Column(name = "OPTLOCK_VERSION")
    private long version;

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Date getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Date lockedTime) {
        this.lockedTime = lockedTime;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }
}
