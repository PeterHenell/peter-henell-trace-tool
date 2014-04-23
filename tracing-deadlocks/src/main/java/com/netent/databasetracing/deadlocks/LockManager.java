package com.netent.databasetracing.deadlocks;

import org.jgrapht.alg.cycle.DirectedSimpleCycles;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

public class LockManager {

    private TakenLockSet locks;
    private ListenableDirectedGraph<DatabaseProcess, DefaultEdge> graph;


    public LockManager(ListenableDirectedGraph<DatabaseProcess, DefaultEdge> graph) {
        this.graph = graph;
        locks = new TakenLockSet(graph);
    }


    public boolean performLockAction(LockAction action) {
        TakenLock takenLock = locks.doLockAction(action);

        TakenLock conflictedTakenLock = findConflict(action);
        if (null != conflictedTakenLock) {
            // add edge between these

            DatabaseProcess blockingProcess = locks.findProcess(takenLock.getOwner());
            DatabaseProcess blockedProcess = locks.findProcess(conflictedTakenLock.getOwner());

            graph.addEdge(blockedProcess, blockingProcess);
            return true;
        }
        return false;
    }


    public TakenLock findConflict(LockAction action) {
        return locks.findConflict(action);
    }


    public boolean isDeadlock() {
        DirectedSimpleCycles<DatabaseProcess, DefaultEdge> cycles = new JohnsonSimpleCycles<>(graph);
        return cycles.findSimpleCycles().size() > 0;
        // return false;
    }


    public TakenLockSet getLocks() {
        return locks;
    }

}
