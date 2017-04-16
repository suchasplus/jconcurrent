package com.suchasplus.jconcurrent.demo.reEntrantLock;

import com.suchasplus.jconcurrent.demo.deadlock.DeadlockConsoleHandler;
import com.suchasplus.jconcurrent.demo.deadlock.DeadlockDetector;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Powered by suchasplus@gmail.com on 2017/3/26.
 *
 * 可中断
 */
public class Interruptable implements Runnable{

    enum LockType {
        LOCK1,
        LOCK2
    }

    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();
    LockType lockType;

    public Interruptable (LockType locktype) {
        lockType = locktype;
    }

    @Override
    public void run() {
        try {
            if(lockType == LockType.LOCK1) {
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
            }
            if(lockType == LockType.LOCK2) {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
            }

        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + " thread exit");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Interruptable i1 = new Interruptable(LockType.LOCK1);
        Interruptable i2 = new Interruptable(LockType.LOCK2);

        Thread t1 = new Thread(i1);
        Thread t2 = new Thread(i2);
        t1.start();t2.start();
        Thread.sleep(1000);

        //DeadlockDetector detector = new DeadlockDetector(new DeadlockConsoleHandler(), 1, TimeUnit.SECONDS);
        //detector.start();
    }
}

/*
2017-03-26 23:17:07
Full thread dump Java HotSpot(TM) 64-Bit AIOServer VM (25.60-b23 mixed mode):

"DestroyJavaVM" #14 prio=5 os_prio=0 tid=0x000000000234d800 nid=0x3c14 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Thread-1" #13 prio=5 os_prio=0 tid=0x0000000023108000 nid=0x5824 waiting on condition [0x0000000023eff000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007405ecc30> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:897)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
	at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
	at com.suchasplus.jconcurrent.demo.ReEntrantLock.Interruptable.run(Interruptable.java:44)
	at java.lang.Thread.run(Thread.java:745)

"Thread-0" #12 prio=5 os_prio=0 tid=0x0000000023107000 nid=0x59e4 waiting on condition [0x0000000023dfe000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007405ecc60> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:897)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
	at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
	at com.suchasplus.jconcurrent.demo.ReEntrantLock.Interruptable.run(Interruptable.java:35)
	at java.lang.Thread.run(Thread.java:745)

"Monitor Ctrl-Break" #11 daemon prio=5 os_prio=0 tid=0x0000000023118000 nid=0x2814 runnable [0x0000000023c4f000]
   java.lang.Thread.State: RUNNABLE
	at java.net.DualStackPlainSocketImpl.accept0(Native Method)
	at java.net.DualStackPlainSocketImpl.socketAccept(DualStackPlainSocketImpl.java:131)
	at java.net.AbstractPlainSocketImpl.accept(AbstractPlainSocketImpl.java:409)
	at java.net.PlainSocketImpl.accept(PlainSocketImpl.java:199)
	- locked <0x0000000740ac46a0> (a java.net.SocksSocketImpl)
	at java.net.ServerSocket.implAccept(ServerSocket.java:545)
	at java.net.ServerSocket.accept(ServerSocket.java:513)
	at com.intellij.rt.execution.application.AppMain$1.run(AppMain.java:79)
	at java.lang.Thread.run(Thread.java:745)

"Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000002303d000 nid=0x58a8 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread3" #9 daemon prio=9 os_prio=2 tid=0x0000000022fae000 nid=0x2d0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread2" #8 daemon prio=9 os_prio=2 tid=0x0000000022f9d000 nid=0x5630 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #7 daemon prio=9 os_prio=2 tid=0x0000000022f9c000 nid=0x4c24 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #6 daemon prio=9 os_prio=2 tid=0x0000000022f95000 nid=0x4da8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x0000000022f94800 nid=0x4c64 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000022f48000 nid=0x5888 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x00000000028eb800 nid=0x580 in Object.wait() [0x0000000022eae000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000007405070b8> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
	- locked <0x00000007405070b8> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x00000000028e5000 nid=0x51e0 in Object.wait() [0x0000000022daf000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x0000000740506af8> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:157)
	- locked <0x0000000740506af8> (a java.lang.ref.Reference$Lock)

"VM Thread" os_prio=2 tid=0x0000000020ea9800 nid=0x5b0c runnable

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000002807800 nid=0x55ac runnable

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000002809000 nid=0x35c8 runnable

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x000000000280a800 nid=0x4b04 runnable

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x000000000280c000 nid=0x3754 runnable

"GC task thread#4 (ParallelGC)" os_prio=0 tid=0x000000000280e000 nid=0x5908 runnable

"GC task thread#5 (ParallelGC)" os_prio=0 tid=0x000000000280f800 nid=0x5624 runnable

"GC task thread#6 (ParallelGC)" os_prio=0 tid=0x0000000002812800 nid=0x5584 runnable

"GC task thread#7 (ParallelGC)" os_prio=0 tid=0x0000000002814800 nid=0x5af8 runnable

"VM Periodic Task Thread" os_prio=2 tid=0x00000000230d7000 nid=0x5850 waiting on condition

JNI global references: 15


Found one Java-level deadlock:
=============================
"Thread-1":
  waiting for ownable synchronizer 0x00000007405ecc30, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
  which is held by "Thread-0"
"Thread-0":
  waiting for ownable synchronizer 0x00000007405ecc60, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
  which is held by "Thread-1"

Java stack information for the threads listed above:
===================================================
"Thread-1":
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007405ecc30> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:897)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
	at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
	at com.suchasplus.jconcurrent.demo.ReEntrantLock.Interruptable.run(Interruptable.java:44)
	at java.lang.Thread.run(Thread.java:745)
"Thread-0":
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007405ecc60> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:897)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
	at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
	at com.suchasplus.jconcurrent.demo.ReEntrantLock.Interruptable.run(Interruptable.java:35)
	at java.lang.Thread.run(Thread.java:745)

Found 1 deadlock.
 */