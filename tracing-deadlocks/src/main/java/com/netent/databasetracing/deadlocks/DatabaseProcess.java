package com.netent.databasetracing.deadlocks;

import java.io.Serializable;
import java.util.HashSet;

public class DatabaseProcess implements Serializable {

    private static final long serialVersionUID = 6017921879481418229L;

    HashSet<TakenLock> transactionLocks = new HashSet<TakenLock>();
    private final long transactionId;


    public DatabaseProcess(long transactionId) {
        this.transactionId = transactionId;
    }


    public void addLock(TakenLock lock) {
        transactionLocks.add(lock);
    }


    public int getLockCount() {
        return transactionLocks.size();
    }


    public long getTransactionId() {
        return transactionId;
    }


    public TakenLock findBlocker(TakenLock requestedLock) {
        if (this.transactionId == requestedLock.getOwner()) {
            return null;
        }
        for (TakenLock holdingLock : transactionLocks) {
            if (holdingLock.getLockmode().isConflictingWith(requestedLock.getLockmode())) {
                return holdingLock;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "DatabaseProcess [transactionId=" + transactionId + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (transactionId ^ (transactionId >>> 32));
        return result;
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
        DatabaseProcess other = (DatabaseProcess) obj;
        if (transactionId != other.transactionId) {
            return false;
        }
        return true;
    }


    public boolean removeLock(TakenLock lock) {
        return transactionLocks.remove(lock);
    }

}
