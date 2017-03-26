package com.suchasplus.jconcurrent.demo.deadlock;

import java.lang.management.ThreadInfo;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 */
public class DeadlockConsoleHandler implements IDeadlockHandler {
    @Override
    public void handleDeadlock(ThreadInfo[] deadlockedThreads) {
        if(deadlockedThreads != null) {
            int len = deadlockedThreads.length;
            for(Thread t : Thread.getAllStackTraces().keySet()) {
                for(int i = 0; i < len; i++) {
                    if(t.getId() == deadlockedThreads[i].getThreadId()) {
                        t.interrupt();
                    }
                }
            }
        }
    }
}
