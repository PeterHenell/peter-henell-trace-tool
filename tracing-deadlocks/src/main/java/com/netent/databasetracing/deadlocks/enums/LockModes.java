package com.netent.databasetracing.deadlocks.enums;

import java.io.ObjectStreamException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Sets;

public enum LockModes {
    NL(0, "NO LOCK"), SCH_S(1, "SCHEMA STABILITY"), SCH_M(2, "SCHEMA MODIFICATION"), S(3, "SHARED"), U(4, "UPDATE"), X(5, "EXCLUSIVE"), IS(
            6, "INTENT SHARED"), IU(7, "INTENT UPDATE"), IX(8, "INTENT EXCLUSIVE"), SIU(9, "SHARED INTENT UPDATE"), SIX(10,
            "SHARED INTENT EXCLUSIVE"), UIX(11, "UPDATE INTENT EXCLUSIVE"), BU(12, "BULK UPDATE"), RS_S(13, "SHARED RANGE SHARED"), RS_U(
            14, "SHARED RANGE UPDATE"), RI_N(15, "INSERT RANGE-NULL"), RI_S(16, "INSERT RANGE-SHARED"), RI_U(17, "INSERT RANGE-UPDATE"), RI_X(
            18, "INSERT RANGE-EXCLUSIVE"), RX_S(19, "EXCLUSIVE RANGE-SHARED"), RX_U(20, "EXCLUSIVE RANGE-UPDATE"), RX_X(21,
            "EXCLUSIVE RANGE-EXLCUSIVE");

    private final int id;
    private final String description;

    private static Map<LockModes, HashSet<LockModes>> lockInCompatibilities;

    static {
        lockInCompatibilities = new HashMap<LockModes, HashSet<LockModes>>();

        lockInCompatibilities.put(S, Sets.newHashSet(SCH_M, X, IX, SIX, UIX, BU));
        lockInCompatibilities.put(U, Sets.newHashSet(SCH_M, U, X, IU, IX, SIU, UIX, BU));
        lockInCompatibilities.put(SCH_S, Sets.newHashSet(SCH_M));
        lockInCompatibilities.put(SCH_M, Sets.newHashSet(SCH_S, SCH_M, S, U, X, IS, IU, IX, SIU, SIX, UIX, BU));
        lockInCompatibilities.put(X, Sets.newHashSet(SCH_M, S, U, X, IS, IU, IX, SIU, SIX, UIX, BU));
        lockInCompatibilities.put(IS, Sets.newHashSet(SCH_M, X, BU));
        lockInCompatibilities.put(IU, Sets.newHashSet(SCH_M, U, X, UIX, BU));
        lockInCompatibilities.put(IX, Sets.newHashSet(SCH_M, S, U, X, SIU, SIX, UIX, BU));
        lockInCompatibilities.put(SIU, Sets.newHashSet(SCH_M, U, X, IX, SIX, UIX, BU));
        lockInCompatibilities.put(SIX, Sets.newHashSet(SCH_M, S, U, X, IX, SIU, SIX, UIX, BU));
        lockInCompatibilities.put(UIX, Sets.newHashSet(SCH_M, S, U, X, IU, IX, SIU, SIX, UIX, BU));
        lockInCompatibilities.put(BU, Sets.newHashSet(SCH_M, S, U, X, IS, IU, IX, SIU, SIX, UIX));

    }


    private LockModes(int id, String description) {
        this.id = id;
        this.description = description;
    }


    public int getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }


    public static LockModes fromId(int id) {
        for (LockModes eventType : LockModes.values()) {
            if (id == eventType.getId()) {
                return eventType;
            }
        }

        throw new IllegalArgumentException("No LockModes with id '" + id + "' found!");
    }


    public static List<LockModes> toList() {
        return Arrays.asList(LockModes.values());
    }


    private Object readResolve() throws ObjectStreamException {
        return fromId(this.id);
    }


    public boolean isConflictingWith(LockModes requestedMode) {
        HashSet<LockModes> conflicts = lockInCompatibilities.get(this);
        if (conflicts != null) {
            return conflicts.contains(requestedMode);
        } else {
            throw new InvalidParameterException("This locktype [" + this.toString() + "] have not been implemented to search for conflicts");
        }
    }
}
