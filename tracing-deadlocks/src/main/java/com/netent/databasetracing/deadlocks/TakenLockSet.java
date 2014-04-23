package com.netent.databasetracing.deadlocks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.netent.databasetracing.deadlocks.enums.LockRequest;
import com.netent.databasetracing.deadlocks.exceptions.LockingActionException;

public class TakenLockSet implements Serializable { // extends HashSet<TakenLock> {

    private static final long serialVersionUID = -3094383549704451767L;

    private final ListenableDirectedGraph<DatabaseProcess, DefaultEdge> graph;


    public TakenLockSet(ListenableDirectedGraph<DatabaseProcess, DefaultEdge> graph) {
        super();
        this.graph = graph;
    }

    Map<Long, DatabaseProcess> processes = new HashMap<>();


    public TakenLock doLockAction(LockAction action) {
        TakenLock takenLock = new TakenLock(action);
        DatabaseProcess p = getProcessForTransactionId(action.getTransactionId());

        if (action.getLockRequestType().equals(LockRequest.Aqcuired)) {
            p.addLock(takenLock);
        } else {

            if (p.removeLock(takenLock)) {
                // if the lock was present in p
                if (p.getLockCount() == 0) {
                    graph.removeVertex(p);
                    processes.remove(p.getTransactionId());
                }
            } else {
                // if lock was not present, then we cannot remove it
                throw new LockingActionException("Cannot release a lock that the process have not aqcuired");
            }
        }
        return takenLock;
    }


    private void assureProcessInGraph(DatabaseProcess p) {
        if (graph.containsVertex(p)) {
        } else {
            graph.addVertex(p);
        }
    }


    private DatabaseProcess getProcessForTransactionId(long transactionId) {
        DatabaseProcess p = processes.get(transactionId);
        if (null == p) {
            p = new DatabaseProcess(transactionId);
            processes.put(transactionId, p);
            assureProcessInGraph(p);
        }
        return p;
    }


    public int getProcessCount() {
        return processes.size();
    }


    public int getTotalLockCount() {
        int total = 0;
        for (DatabaseProcess p : processes.values()) {
            total += p.getLockCount();
        }
        return total;
    }


    public TakenLock findConflict(LockAction requestedLock) {
        for (DatabaseProcess p : processes.values()) {
            TakenLock blockedLock = new TakenLock(requestedLock);
            TakenLock blocker = p.findBlocker(blockedLock);
            if (null != blocker)
                return blocker;
        }
        return null;
    }


    public DatabaseProcess findProcess(long transactionId) {
        return processes.get(transactionId);
    }
}
