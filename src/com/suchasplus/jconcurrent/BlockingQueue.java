package com.suchasplus.jconcurrent;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Powered by suchasplus@gmail.com on 2017/3/6.
 */
public class BlockingQueue<T> {
    private final static int MAX_CAP = 2;
    private Queue<T> holder = new LinkedList<>();

    public synchronized void add(T t) throws InterruptedException {
        while(holder.size() == MAX_CAP) {
            System.out.println("BQ is FULL, invoke by " + Thread.currentThread().getName());
            wait();
        }
        holder.add(t);
        System.out.println("BQ size: " + holder.size());
        notify();
    }

    public synchronized T get() throws InterruptedException {
        while (holder.isEmpty()) {
            System.out.println("BQ is Empty! invoke by" + Thread.currentThread().getName());
            wait();
        }
        T t = holder.remove();
        System.out.println("BQ size: " + holder.size());
        notify();
        return t;

    }
}
