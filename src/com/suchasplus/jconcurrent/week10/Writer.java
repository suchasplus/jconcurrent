package com.suchasplus.jconcurrent.week10;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Writer implements Runnable {
    private final Counter counter;

    public Writer(Counter counter)
    {
        this.counter = counter;
    }

    @Override
    public void run() {
        while (true)
        {
            if (Thread.interrupted())
            {
                break;
            }

            counter.increment();
        }
    }
}
