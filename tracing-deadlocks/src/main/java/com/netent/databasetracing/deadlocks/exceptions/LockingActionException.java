package com.netent.databasetracing.deadlocks.exceptions;

public class LockingActionException extends RuntimeException {

    public LockingActionException(String message) {
        super(message);
    }

    private static final long serialVersionUID = -267686737222739770L;

}
