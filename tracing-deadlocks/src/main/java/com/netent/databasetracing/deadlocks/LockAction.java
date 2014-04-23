package com.netent.databasetracing.deadlocks;

import com.netent.databasetracing.deadlocks.enums.LockModes;
import com.netent.databasetracing.deadlocks.enums.LockRequest;

public class LockAction {

    private final String resource;
    private final LockModes lockMode;
    private final long transactionId;
    private final LockRequest lockRequestType;


    public LockAction(String resource, LockModes lockType, long transactionId, LockRequest lockRequestType) {
        this.resource = resource;
        this.lockMode = lockType;
        this.transactionId = transactionId;
        this.lockRequestType = lockRequestType;
    }


    public String getResource() {
        return resource;
    }


    public LockModes getLockMode() {
        return lockMode;
    }


    public long getTransactionId() {
        return transactionId;
    }


    public LockRequest getLockRequestType() {
        return lockRequestType;
    }


    @Override
    public String toString() {
        return "LockAction [resource=" + resource + ", lockMode=" + lockMode + ", transactionId=" + transactionId + ", lockRequestType="
                + lockRequestType + "]";
    }

}
