package com.suchasplus.jconcurrent.demo.deadlock;

import java.lang.management.ThreadInfo;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 */
public interface IDeadlockHandler {
    void handleDeadlock(final ThreadInfo[] deadlockedThreads);
}
