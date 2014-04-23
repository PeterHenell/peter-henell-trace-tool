package com.netent.databasetracing.deadlocks;

import java.io.Serializable;

import com.netent.databasetracing.deadlocks.enums.LockModes;

public class TakenLock implements Serializable {

    private static final long serialVersionUID = 5189801284293999079L;
    private final String resource;
    private final LockModes lockmode;
    private final long owner;


    public TakenLock(LockAction lockAction) {
        this.resource = lockAction.getResource();
        this.owner = lockAction.getTransactionId();
        this.lockmode = lockAction.getLockMode();
    }


    public TakenLock(String resource, long ownerTransactionid, LockModes lockmode) {
        this.resource = resource;
        this.owner = ownerTransactionid;
        this.lockmode = lockmode;
    }


    public LockModes getLockmode() {
        return lockmode;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lockmode == null) ? 0 : lockmode.hashCode());
        result = prime * result + (int) (owner ^ (owner >>> 32));
        result = prime * result + ((resource == null) ? 0 : resource.hashCode());
        return result;
    }


    @Override
    public String toString() {
        return "TakenLock [resource=" + resource + ", lockmode=" + lockmode + ", owner=" + owner + "]";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TakenLock other = (TakenLock) obj;
        if (lockmode != other.lockmode) {
            return false;
        }
        if (owner != other.owner) {
            return false;
        }
        if (resource == null) {
            if (other.resource != null) {
                return false;
            }
        } else if (!resource.equals(other.resource)) {
            return false;
        }
        return true;
    }


    public long getOwner() {
        return owner;
    }

}
