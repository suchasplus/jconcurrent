package com.suchasplus.jconcurrent.demo.reEntrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 *
 * 可限时
 */
public class TimeLock implements Runnable {

    final static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if(lock.tryLock(2, TimeUnit.SECONDS)) {
                Thread.sleep(3000);
            } else {
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //if(lock.isHeldByCurrentThread()) {
                lock.unlock();
            //}
        }
    }

    public static void main(String[] args){
        TimeLock lock = new TimeLock();
        Thread t1 = new Thread(lock);
        Thread t2 = new Thread(lock);
        t1.start();t2.start();
        //t1.join();t2.join();
    }
}
