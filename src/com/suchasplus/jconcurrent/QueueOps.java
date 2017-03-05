package com.suchasplus.jconcurrent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Powered by suchasplus@gmail.com on 2017/3/6.
 */
public class QueueOps implements  Runnable{

    private Boolean shouldPlus = null;

    private static AtomicInteger atomicInteger = null;

    public static BlockingQueue<Integer> bq = new BlockingQueue<>();

    public QueueOps(Boolean isAdder) {
        shouldPlus = isAdder;
        atomicInteger = new AtomicInteger(1);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(new Random().nextInt(1000));

                if(shouldPlus) {
                    Integer v = new Random().nextInt();
                    bq.add(v);
                    System.out.println(Thread.currentThread().getName() + " bq add " + v );
                } else {
                    Integer v = bq.get();
                    System.out.println(Thread.currentThread().getName() + " bq get " + v);
                }
                System.out.println(Thread.currentThread().getName() + " ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (atomicInteger.addAndGet(1) > 100) {
                return;
            }
        }

    }
}
