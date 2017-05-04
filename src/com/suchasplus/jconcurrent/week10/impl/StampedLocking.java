package com.suchasplus.jconcurrent.week10.impl;

import com.suchasplus.jconcurrent.week10.Counter;

import java.util.concurrent.locks.StampedLock;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class StampedLocking implements Counter {
    private StampedLock rwlock = new StampedLock();

    private long counter;

    @Override
    public long getCounter() {
        long stamp = rwlock.tryOptimisticRead();
        try {
            long result = counter;
            if(rwlock.validate(stamp)) {
                return result;
            }
            stamp = rwlock.readLock();
            result = counter;
            rwlock.unlockRead(stamp);
            return result;
        } finally {

        }
    }

    @Override
    public void increment() {
        long stamp = rwlock.writeLock();
        try {
            counter++;
        } finally {
            rwlock.unlockWrite(stamp);
        }
    }
}
