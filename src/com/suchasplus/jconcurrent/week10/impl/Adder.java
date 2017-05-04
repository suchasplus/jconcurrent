package com.suchasplus.jconcurrent.week10.impl;

import com.suchasplus.jconcurrent.week10.Counter;

import java.util.concurrent.atomic.LongAdder;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Adder implements Counter {
    private final LongAdder adder = new LongAdder();

    @Override
    public long getCounter() {
        return adder.longValue();
    }

    @Override
    public void increment() {
        adder.increment();
    }
}
