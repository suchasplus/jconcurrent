package com.suchasplus.jconcurrent.demo.cdlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Powered by suchasplus@gmail.com on 2017/3/27.
 */
public class CountDL implements Runnable {
    static final CountDownLatch latch = new CountDownLatch(10);
    static final CountDL self = new CountDL();

    @Override
    public void run() {
        try {
            int len = new Random().nextInt(5) * 1000;
            Thread.sleep(len);
            System.out.println("Thread " + Thread.currentThread().getId() + " check complete " + len);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.submit(self);
        }

        latch.await();

        System.out.println("job done!");
        exec.shutdown();
    }
}
