package com.netent.databasetracing.deadlocks;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.junit.Assert;
import org.junit.Test;

import com.netent.databasetracing.deadlocks.enums.LockModes;
import com.netent.databasetracing.deadlocks.enums.LockRequest;

public class TakenLockSetTest {

    @Test
    public void shouldInstanciateTakenLockSet() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        Assert.assertNotNull(set);
    }


    @Test
    public void shouldDoLockAction() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        TakenLock res = set.doLockAction(lock1);
        Assert.assertNotNull(res);

        Assert.assertEquals(1, set.getTotalLockCount());
        Assert.assertEquals(1, set.getProcessCount());
    }


    @Test
    public void shouldHaveOneProcessPerTransaction() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction lock2 = new LockAction("Customer", LockModes.S, 2000, LockRequest.Aqcuired);
        LockAction lock3 = new LockAction("Customer", LockModes.S, 3000, LockRequest.Aqcuired);
        LockAction lock4 = new LockAction("Customer", LockModes.S, 4000, LockRequest.Aqcuired);

        set.doLockAction(lock1);
        set.doLockAction(lock2);
        set.doLockAction(lock3);
        set.doLockAction(lock4);

        Assert.assertEquals(4, set.getProcessCount());
    }


    @Test
    public void shouldHaveOneLockPerLockNoMatterFromWhichTransaction() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction lock2 = new LockAction("Customer", LockModes.X, 1000, LockRequest.Aqcuired);
        LockAction lock3 = new LockAction("Order", LockModes.S, 2000, LockRequest.Aqcuired);
        LockAction lock4 = new LockAction("Order", LockModes.X, 2000, LockRequest.Aqcuired);

        set.doLockAction(lock1);
        set.doLockAction(lock2);
        set.doLockAction(lock3);
        set.doLockAction(lock4);

        Assert.assertEquals(2, set.getProcessCount());
        Assert.assertEquals(4, set.getTotalLockCount());
    }


    @Test
    public void shouldRemoveProcessWhenAllLocksAreReleased() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction aquire_lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction release_lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Released);

        set.doLockAction(aquire_lock1);
        Assert.assertEquals(1, set.getProcessCount());

        set.doLockAction(release_lock1);
        Assert.assertEquals(0, set.getTotalLockCount());
        Assert.assertEquals(0, set.getProcessCount());
    }


    @Test
    public void shouldFindConflictInSet() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction aquire_lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction no_conflict_lock = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction conflicting_lock = new LockAction("Customer", LockModes.X, 2000, LockRequest.Aqcuired);

        set.doLockAction(aquire_lock1);
        TakenLock conflict = set.findConflict(conflicting_lock);
        Assert.assertNotNull(conflict);

        Assert.assertNull(set.findConflict(no_conflict_lock));
    }


    @Test
    public void shouldFindProcessInSetIfProcessHaveActiveLocks() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction aquire_lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction aquire_lock2 = new LockAction("Customer", LockModes.S, 2000, LockRequest.Aqcuired);
        LockAction release_lock1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Released);

        set.doLockAction(aquire_lock1);
        set.doLockAction(aquire_lock2);
        Assert.assertNotNull(set.findProcess(aquire_lock1.getTransactionId()));
        Assert.assertNotNull(set.findProcess(aquire_lock2.getTransactionId()));

        set.doLockAction(release_lock1);
        Assert.assertNull(set.findProcess(aquire_lock1.getTransactionId()));

        Assert.assertEquals(1, set.getProcessCount());
    }


    @Test
    public void shouldBeBlockedByTheOtherProcess() {
        TakenLockSet set = new TakenLockSet(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        LockAction t1000 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction t4000 = new LockAction("Customer", LockModes.X, 4000, LockRequest.Aqcuired);

        set.doLockAction(t1000);
        TakenLock conflict = set.findConflict(t4000);
        Assert.assertEquals(new TakenLock(t1000), conflict);
    }
}
