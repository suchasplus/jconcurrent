package com.suchasplus.jconcurrent.week11;

/**
 * Powered by suchasplus@gmail.com on 2017/5/4.
 */
public class Week11Main {

    public static void main(String[] args) {
        System.out.println("HEAD OFFSET : " + (MemoryUtils.getIntegersPerCacheLine() - 1));
        System.out.println("TAIL OFFSET : " + ((MemoryUtils.getIntegersPerCacheLine() - 1) + MemoryUtils.getIntegersPerCacheLine()));

        BlockingArrayQueue<Integer> baq = new BlockingArrayQueue<>(3, 1, 6);

        baq.offer(11);

        baq.offer(23);

        baq.offer(123);

        baq.poll();

        baq.offer(321);

        baq.offer(56);

        baq.offer(78);

        baq.clear();
    }
}
