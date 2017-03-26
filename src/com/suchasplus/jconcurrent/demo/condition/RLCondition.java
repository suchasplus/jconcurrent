package com.suchasplus.jconcurrent.demo.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 */
public class RLCondition implements Runnable {

    final static ReentrantLock lock = new ReentrantLock();
    final static Condition condition = lock.newCondition();

    @Override
    public void run() {

        lock.lock();
        try {
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RLCondition cond = new RLCondition();
        Thread t1 = new Thread(cond);
        t1.start();
        System.out.println("T1 Started");
        Thread.sleep(2000);

        //notify thread1 continue
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
