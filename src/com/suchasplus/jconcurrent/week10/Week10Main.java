package com.suchasplus.jconcurrent.week10;


import com.suchasplus.jconcurrent.week10.impl.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Week10Main {

    final static long TARGET_NUMBER = 1000000L;
    private static String COUNTER = Counters.RWLOCK.toString();
    private static ExecutorService es;
    private static long start;
    private final static AtomicBoolean done = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        es = Executors.newFixedThreadPool(21);
        start = System.currentTimeMillis();
        Counter counter = getCounter();
        es.execute(new Writer(counter));
        IntStream.range(0,20).forEach((i) -> {
            es.execute(new Reader(counter));
        });

        es.awaitTermination(1, TimeUnit.MINUTES);

    }

    public static final void publish(long ms) {
        //synchronized (done) {
            if (!done.get()) {
                done.set(true);
                System.out.println(COUNTER + " use ms:" + (ms - start));
                es.shutdownNow();
            }
        //}
    }

    public static Counter getCounter() {
        Counters counterTypeEnum = Counters.valueOf(COUNTER);
        switch(counterTypeEnum) {
            case UNCONCURRENT:
                return new UnConcurrent();
            case VOLATILE:
                return new Volatile();
            case SYNCHRONIZED:
                return new Synchronized();
            case RWLOCK:
                return new RWLock();
            case ATOMIC:
                return new Atomic();
            case ADDER:
                return new Adder();
            case STAMPEDLOCKING:
                return new StampedLocking();
            default:
                return new Volatile();

        }
    }

    private enum Counters
    {
        UNCONCURRENT,
        VOLATILE,
        SYNCHRONIZED,
        RWLOCK,
        ATOMIC,
        ADDER,
        STAMPEDLOCKING
    }
}

