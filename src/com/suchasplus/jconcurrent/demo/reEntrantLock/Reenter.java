package com.suchasplus.jconcurrent.demo.reEntrantLock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 *
 * 基准demo 可重入
 */
public class Reenter implements Runnable {
    static ReentrantLock lock = new ReentrantLock();
    static int i = 0;

    @Override
    public void run() {
        IntStream.range(0, 1000).forEach((idx) -> {
            lock.lock();
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock(); //必须要在finally里面释放锁,  synchronized 是jvm自动释放
                lock.unlock(); // try to comment this line, using jps and jstack $pid in cmd
            }
        });
    }


    public static void main(String[] args) throws InterruptedException{
        Reenter l = new Reenter();
        Thread t1 = new Thread(l);
        Thread t2 = new Thread(l);
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("i added as: " + i);
    }
}
