package com.suchasplus.jconcurrent.week10.impl;

import com.suchasplus.jconcurrent.week10.Counter;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Volatile implements Counter {

    private volatile long counter = 0;

    @Override
    public long getCounter() {
        return counter;
    }

    @Override
    public void increment() {
        counter++;
    }
}
