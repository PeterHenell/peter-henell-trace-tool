package com.netent.databasetracing.deadlocks;

import junit.framework.Assert;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.netent.databasetracing.deadlocks.enums.LockModes;
import com.netent.databasetracing.deadlocks.enums.LockRequest;
import com.netent.databasetracing.deadlocks.exceptions.LockingActionException;

public class LockManagerTest {

    LockManager manager;

    LockAction l1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
    LockAction l2 = new LockAction("Order", LockModes.S, 2000, LockRequest.Aqcuired);
    LockAction l3 = new LockAction("Order", LockModes.X, 1000, LockRequest.Aqcuired);
    LockAction l4 = new LockAction("Customer", LockModes.X, 2000, LockRequest.Aqcuired);

    LockAction r1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Released);
    LockAction r2 = new LockAction("Order", LockModes.S, 2000, LockRequest.Released);

    private ListenableDirectedGraph<DatabaseProcess, DefaultEdge> graph;


    @Before
    public void prepare() {
        graph = new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class);
        manager = new LockManager(graph);
    }


    @Test
    public void shouldInstanciateLockManager() {
        LockManager ma = new LockManager(new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(DefaultEdge.class));
        Assert.assertNotNull(ma);
    }


    @Test
    public void shouldTakeLock() {
        manager.performLockAction(l1);

        Assert.assertEquals(1, manager.getLocks().getTotalLockCount());
    }


    @Test
    public void shouldReleaseTakenLock() {
        manager.performLockAction(l1);
        Assert.assertEquals(1, manager.getLocks().getTotalLockCount());

        manager.performLockAction(r1);
        Assert.assertEquals(0, manager.getLocks().getTotalLockCount());
    }


    @Test(expected = LockingActionException.class)
    public void shouldOnlyBeAbleToReleaseThingsThatTransactionItselfHaveReqcired() {
        LockAction aC_Tread1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction rC_Tread2 = new LockAction("Customer", LockModes.S, 2000, LockRequest.Released);

        manager.performLockAction(aC_Tread1);
        manager.performLockAction(rC_Tread2);
    }


    @Test
    @Ignore
    public void shouldShowLockGraph() {
        manager.performLockAction(l1);
        // manager.showGraph();
    }


    @Test
    public void shouldCauseBlockingOtherTransaction() {
        manager.performLockAction(l1);

        boolean isBlocked = manager.performLockAction(l4);
        Assert.assertTrue(isBlocked);

        manager.performLockAction(l2);
        boolean isBlocked2 = manager.performLockAction(l3);
        Assert.assertTrue(isBlocked2);
    }


    @Test
    public void shouldNotCauseBlockingItself() {
        LockAction sC_Tread1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction xC_Tread1 = new LockAction("Customer", LockModes.X, 1000, LockRequest.Aqcuired);

        manager.performLockAction(sC_Tread1);

        boolean isBlocked = manager.performLockAction(xC_Tread1);
        Assert.assertFalse(isBlocked);
    }


    @Test
    public void shouldHaveBlockingEdgeBetweenTwoProcesses() {
        manager.performLockAction(l1);
        manager.performLockAction(l4);

        Assert.assertEquals(1, graph.edgeSet().size());
        DefaultEdge e = (DefaultEdge) graph.edgeSet().toArray()[0];
        String s = e.toString();
        System.out.println(s);
        Assert.assertTrue(s.contains("1000"));
        Assert.assertTrue(s.contains("2000"));
    }


    @Test
    public void shouldOnlyHaveOneVertexPerTransaction() {
        manager.performLockAction(l1);
        manager.performLockAction(l4);

        manager.performLockAction(l2);
        manager.performLockAction(l3);

        Assert.assertEquals(2, graph.edgeSet().size());
        Assert.assertEquals(2, graph.vertexSet().size());
    }

}
