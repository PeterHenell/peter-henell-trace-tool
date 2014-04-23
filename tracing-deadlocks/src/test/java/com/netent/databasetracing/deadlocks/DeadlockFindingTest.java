package com.netent.databasetracing.deadlocks;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.netent.databasetracing.deadlocks.enums.LockModes;
import com.netent.databasetracing.deadlocks.enums.LockRequest;

public class DeadlockFindingTest {

    private List<LockAction> actions;


    public DeadlockFindingTest() {
        actions = new ArrayList<LockAction>();
        actions.add(new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired));
        actions.add(new LockAction("Order", LockModes.S, 2000, LockRequest.Aqcuired));
        actions.add(new LockAction("Order", LockModes.X, 1000, LockRequest.Aqcuired));
        actions.add(new LockAction("Customer", LockModes.X, 2000, LockRequest.Aqcuired));
    }


    @Test
    public void shouldListActions() {
        for (LockAction action : actions) {
            System.out.println(action);
        }
    }


    @Test
    public void shouldDetectConflictingLockTypes() {
        LockModes grantedMode = LockModes.S;
        LockModes requestedMode = LockModes.X;

        boolean conflicting = grantedMode.isConflictingWith(requestedMode);
        Assert.assertEquals(true, conflicting);

        Assert.assertTrue(LockModes.S.isConflictingWith(LockModes.X));
        Assert.assertTrue(LockModes.S.isConflictingWith(LockModes.IX));
        Assert.assertTrue(LockModes.S.isConflictingWith(LockModes.SIX));
        Assert.assertTrue(LockModes.S.isConflictingWith(LockModes.UIX));
        Assert.assertTrue(LockModes.S.isConflictingWith(LockModes.BU));

        // Should be combatible
        Assert.assertFalse(LockModes.S.isConflictingWith(LockModes.S));
        Assert.assertFalse(LockModes.S.isConflictingWith(LockModes.U));
        Assert.assertFalse(LockModes.S.isConflictingWith(LockModes.IS));
        Assert.assertFalse(LockModes.S.isConflictingWith(LockModes.IU));
        Assert.assertFalse(LockModes.S.isConflictingWith(LockModes.SIU));

        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.SCH_M));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.S));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.U));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.X));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.IS));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.IU));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.IX));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.SIU));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.UIX));
        Assert.assertTrue(LockModes.X.isConflictingWith(LockModes.BU));

    }


    @Test
    public void shouldNotHaveImplementedConflictingLocksForRangeModes() {
        int exceptionCount = 0;
        try {
            LockModes.RI_U.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RI_N.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RI_S.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RI_X.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RX_S.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RX_U.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RX_X.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RS_S.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        try {
            LockModes.RS_U.isConflictingWith(LockModes.S);
        } catch (Exception e) {
            exceptionCount++;
        }
        Assert.assertEquals(9, exceptionCount);
    }

    // @Test
    // public void isPossibleDeadlockTransactions() {
    //
    // boolean possibleDeadlock = new DeadlockFinder(new ActionSimulator()).find(actions);
    // Assert.assertTrue(possibleDeadlock);
    //
    // }

}
