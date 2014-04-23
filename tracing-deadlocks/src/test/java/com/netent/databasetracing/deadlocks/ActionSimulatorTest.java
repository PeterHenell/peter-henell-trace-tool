package com.netent.databasetracing.deadlocks;
//package com.netent.databasetracing.deadlocks;
//
//import junit.framework.Assert;
//
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.ListenableDirectedGraph;
//import org.junit.Before;
//import org.junit.Test;
//
//public class ActionSimulatorTest {
//
//    LockAction l1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
//    LockAction l2 = new LockAction("Order", LockModes.S, 2000, LockRequest.Aqcuired);
//    LockAction l3 = new LockAction("Order", LockModes.X, 1000, LockRequest.Aqcuired);
//    LockAction l4 = new LockAction("Customer", LockModes.X, 2000, LockRequest.Aqcuired);
//
//    LockAction r1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Released);
//    LockAction r2 = new LockAction("Order", LockModes.S, 2000, LockRequest.Released);
//
//    private ListenableDirectedGraph<TakenLock, DefaultEdge> graph;
//
//
//    @Before
//    public void prepare() {
//        graph = new ListenableDirectedGraph<TakenLock, DefaultEdge>(DefaultEdge.class);
//    }
//
//
//    @Test
//    public void shouldAddAndRemoveLocks() {
//        ActionSimulator sim = new ActionSimulator();
//        sim.mainAction(l1);
//        Assert.assertEquals(1, sim.getMainTakenLocks().size());
//
//        sim.mainAction(r1);
//        Assert.assertEquals(0, sim.getMainTakenLocks().size());
//    }
//
//
//    @Test
//    public void shouldHaveUniqueLocks() {
//        LockActionSet lockSet = new LockActionSet(graph);
//        lockSet.doLockAction(l1);
//        lockSet.doLockAction(l1);
//        lockSet.doLockAction(l1);
//        lockSet.doLockAction(l1);
//        lockSet.doLockAction(l1);
//        lockSet.doLockAction(l1);
//        Assert.assertEquals(1, lockSet.size());
//    }
//
//
//    @Test
//    public void shouldRemoveLockThatDoesNotExist() {
//        LockActionSet lockSet = new LockActionSet(graph);
//
//        lockSet.doLockAction(r1);
//        Assert.assertEquals(0, lockSet.size());
//    }
//
//
//    @Test
//    public void shouldDetectBlocking() {
//        ActionSimulator sim = new ActionSimulator();
//        sim.mainAction(l1);
//
//        TakenLock foundConflict = sim.findConflict(l4);
//
//        Assert.assertNotNull(foundConflict);
//        Assert.assertEquals(new TakenLock(l1), foundConflict);
//    }
//
// }
