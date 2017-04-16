package com.suchasplus.jconcurrent.demo.threadpoolPOC;

import java.util.concurrent.ForkJoinPool;

/**
 * Powered by suchasplus@gmail.com on 2017/3/28.
 */
public class Worker extends Thread {

    private ThreadPool pool;

    private Runnable target;

    private boolean isShutdown = false;

    private boolean isIdle = false;

    public Worker(Runnable target, String name, ThreadPool pool) {
        super(name);
        this.target = target;
        this.pool = pool;
        ForkJoinPool forkJoinPool;
    }

    public Runnable getTarget() {
        return target;
    }

    public synchronized void setTarget(Runnable newTarget) {
        this.target = newTarget;
        notifyAll();
    }

    public boolean isIdle() {
        return isIdle;
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public void run() {
        while (!isShutdown) {
            isIdle = false;
            if(target != null) {
                target.run();
            }

            isIdle = true;

            try {
                pool.repool(this);
                synchronized (this) {
                    //等待下一个任务
                    wait();
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            isIdle = false;
        }
    }

    public synchronized void shutdown() {
        isShutdown = true;
        notifyAll();
    }
}
