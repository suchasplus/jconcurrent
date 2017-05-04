package com.suchasplus.jconcurrent.week10.impl;

import com.suchasplus.jconcurrent.week10.Counter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Atomic implements Counter {

    private final AtomicLong atomic = new AtomicLong(0);

    @Override
    public long getCounter() {
        return atomic.get();
    }

    @Override
    public void increment() {
        atomic.incrementAndGet();
    }
}
