package com.suchasplus.jconcurrent.demo.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 * @link http://korhner.github.io/java/multithreading/detect-java-deadlocks-programmatically/
 * @link http://www.programcreek.com/java-api-examples/index.php?class=java.lang.management.ThreadMXBean&method=findDeadlockedThreads
 * 
 */
public class DeadlockDetector{

    private final IDeadlockHandler deadlockHandler;
    private final long period;
    private final TimeUnit unit;

    private final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    final Runnable deadlockCheck = new Runnable() {
        @Override
        public void run() {
            long[] deadlockedThreadIds = DeadlockDetector.this.mbean.findDeadlockedThreads();
            if(deadlockedThreadIds != null ) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);
                DeadlockDetector.this.deadlockHandler.handleDeadlock(threadInfos);
            }
        }
    };


    public DeadlockDetector(final IDeadlockHandler deadlockHandler, final long period, final TimeUnit timeUnit) {
        this.deadlockHandler = deadlockHandler;
        this.period = period;
        this.unit = timeUnit;
    }

    public void start() {
        this.scheduler.scheduleAtFixedRate(
                deadlockCheck,
                period,
                period,
                unit
        );
    }

}
