package com.suchasplus.jconcurrent.demo.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 */
public class Semafore implements Runnable {
    final Semaphore smf = new Semaphore(5);

    @Override
    public void run() {
        try {
            smf.acquire(2);
            // simulate timing ops
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + " : done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            smf.release(2);
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final Semafore smf = new Semafore();
        IntStream.range(0,20).forEach((i) ->{
            exec.submit(smf);
        });

        exec.shutdown();
    }
}
