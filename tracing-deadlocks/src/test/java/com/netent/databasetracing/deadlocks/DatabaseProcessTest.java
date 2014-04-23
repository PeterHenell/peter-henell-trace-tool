package com.netent.databasetracing.deadlocks;

import junit.framework.Assert;

import org.junit.Test;

import com.netent.databasetracing.deadlocks.enums.LockModes;
import com.netent.databasetracing.deadlocks.enums.LockRequest;

public class DatabaseProcessTest {

    @Test
    public void shouldInstanciateDatabaseProcess() {
        DatabaseProcess p = new DatabaseProcess(100L);
        Assert.assertNotNull(p);
    }


    @Test
    public void shouldAddLockToProcess() {
        DatabaseProcess p = new DatabaseProcess(2000);

        LockAction lock1 = new LockAction("Customer", LockModes.S, 2000, LockRequest.Aqcuired);

        p.addLock(new TakenLock(lock1));
        Assert.assertEquals(1, p.getLockCount());
    }


    @Test
    public void shouldNotifyIfLockIsBlockedByProcess() {
        DatabaseProcess p = new DatabaseProcess(2000);
        LockAction lock1 = new LockAction("Customer", LockModes.S, 2000, LockRequest.Aqcuired);
        p.addLock(new TakenLock(lock1));

        LockAction lock2 = new LockAction("Customer", LockModes.X, 1000, LockRequest.Aqcuired);
        TakenLock isBlocking = p.findBlocker(new TakenLock(lock2));
        Assert.assertEquals(new TakenLock(lock1), isBlocking);
    }


    @Test
    public void shouldOnlyBeBlockedIfRequestedLockIsFromOtherTransaction() {
        int processtransactionId = 2000;
        DatabaseProcess p = new DatabaseProcess(2000);
        LockAction lock1 = new LockAction("Customer", LockModes.S, processtransactionId, LockRequest.Aqcuired);
        p.addLock(new TakenLock(lock1));

        LockAction lock2 = new LockAction("Customer", LockModes.X, 1000, LockRequest.Aqcuired);
        Assert.assertNotNull(p.findBlocker(new TakenLock(lock2)));

        LockAction lock3 = new LockAction("Customer", LockModes.X, processtransactionId, LockRequest.Aqcuired);
        Assert.assertNull(p.findBlocker(new TakenLock(lock3)));

    }


    @Test
    public void shouldRemoveLockFromProcess() {
        DatabaseProcess p = new DatabaseProcess(2000);

        LockAction aqcuireLock = new LockAction("Customer", LockModes.S, 2000, LockRequest.Aqcuired);

        TakenLock lock = new TakenLock(aqcuireLock);
        p.addLock(lock);

        Assert.assertEquals(1, p.getLockCount());

        p.removeLock(lock);
        Assert.assertEquals(0, p.getLockCount());

    }

}
