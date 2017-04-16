package com.suchasplus.jconcurrent.demo.threadpoolPOC;

import java.util.List;
import java.util.Vector;

/**
 * Powered by suchasplus@gmail.com on 2017/3/28.
 */
public class ThreadPool {

    private static ThreadPool instance = null;

    private List<Worker> idleThreads;

    private int threadCounter;

    private boolean isShutdown = false;

    private ThreadPool() {
        this.idleThreads = new Vector<>(5);
        threadCounter = 0;
    }

    public int getCreatedThreadsCount() {
        return threadCounter;
    }

    public synchronized static ThreadPool getInstance() {
        if(instance == null) {
            instance = new ThreadPool();
        }
        return instance;
    }

    protected synchronized void repool(Worker repoolingThread) {
        if(!isShutdown) {
            idleThreads.add(repoolingThread);
        } else {
            repoolingThread.shutdown();
        }
    }

    public synchronized void shutdown() {
        isShutdown = true;
        for(int threadIdx = 0; threadIdx < idleThreads.size(); threadIdx++) {
            Worker idleThread = idleThreads.get(threadIdx);
            idleThread.shutdown();
        }
    }

    public synchronized void start(Runnable target) {
        Worker thread = null;
        if(idleThreads.size() > 0) {
            int lastIndex = idleThreads.size() - 1;
            thread = idleThreads.get(lastIndex);
            idleThreads.remove(lastIndex);
            thread.setTarget(target);
        } else {
            threadCounter++;
            thread = new Worker(target, "PThread #" + threadCounter, this);
            thread.start();
        }
    }
}
