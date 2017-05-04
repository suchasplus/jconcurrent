package com.suchasplus.jconcurrent.week10;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class Reader implements Runnable {
    private final Counter counter;

    public Reader(Counter counter)
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

            long count = counter.getCounter();

            if (count > Week10Main.TARGET_NUMBER)
            {
                Week10Main.publish(System.currentTimeMillis());
                break;
            }
        }
    }
}
