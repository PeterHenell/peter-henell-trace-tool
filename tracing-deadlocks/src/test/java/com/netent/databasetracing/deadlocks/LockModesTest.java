package com.netent.databasetracing.deadlocks;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.netent.databasetracing.deadlocks.enums.LockModes;

public class LockModesTest {

    @Test
    public void shouldGetByStatics() {
        LockModes mode = LockModes.RI_N;
        Assert.assertNotNull(mode);
    }


    @Test
    public void shouldGetById() {
        LockModes noclock = LockModes.fromId(0);
        Assert.assertNotNull(noclock);
        Assert.assertEquals(LockModes.NL, noclock);
    }


    @Test
    public void shouldConflictWith() {
        LockModes s = LockModes.S;
        LockModes x = LockModes.X;

        Assert.assertTrue(s.isConflictingWith(x));
        Assert.assertTrue(x.isConflictingWith(s));
    }


    @Test
    public void shouldGetListOfModes() {
        List<LockModes> res = LockModes.toList();
        Assert.assertNotNull(res);
        Assert.assertEquals(22, res.size());

        Assert.assertEquals(LockModes.NL, res.get(0));
        Assert.assertEquals(LockModes.RX_X, res.get(21));

        for (LockModes lockModes : res) {
            Assert.assertNotNull(lockModes.getDescription());
            Assert.assertNotNull(lockModes.getId());
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGetInvalidMode() {
        LockModes.fromId(50);
    }

}
