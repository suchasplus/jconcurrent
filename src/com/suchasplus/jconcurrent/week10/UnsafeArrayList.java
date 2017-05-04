package com.suchasplus.jconcurrent.week10;

import java.util.ArrayList;

/**
 * Powered by suchasplus@gmail.com on 2017/4/24.
 */
public class UnsafeArrayList {
    static ArrayList<Object> arrayList = new ArrayList<>(10000);
    static class AddTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i=0; i<10000000; i++) {
                arrayList.add(new Object());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddTask(), "t1");
        Thread t2 = new Thread(new AddTask(), "t2");
        t1.start();
        t2.start();
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    try {
                            Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
