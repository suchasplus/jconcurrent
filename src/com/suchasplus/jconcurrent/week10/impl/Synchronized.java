package com.suchasplus.jconcurrent.week10.impl;

import com.suchasplus.jconcurrent.week10.Counter;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Synchronized implements Counter {

    final private Object lock = new Object();

    private int counter = 0;

    @Override
    public long getCounter() {
        synchronized (lock)
        {
            return counter;
        }
    }

    @Override
    public void increment() {
        synchronized (lock)
        {
            ++counter;
        }
    }
}
