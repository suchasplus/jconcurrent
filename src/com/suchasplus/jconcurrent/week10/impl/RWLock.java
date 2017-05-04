package com.suchasplus.jconcurrent.week10.impl;

import com.suchasplus.jconcurrent.week10.Counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class RWLock implements Counter {
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();

    private Lock r = rwlock.readLock();
    private Lock w = rwlock.writeLock();

    private long counter = 0;

    @Override
    public long getCounter() {
        try {
            r.lock();
            return counter;
        }
        finally {
            r.unlock();
        }
    }

    @Override
    public void increment() {
        try {
            w.lock();
            ++counter;
        }
        finally {
            w.unlock();
        }
    }
}
