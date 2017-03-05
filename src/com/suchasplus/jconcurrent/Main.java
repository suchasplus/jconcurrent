package com.suchasplus.jconcurrent;

/**
 * Powered by suchasplus@gmail.com on 2017/3/6.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        //Q1
        Thread t1 = new Thread(new SequenceOps(1));
        Thread t2 = new Thread(new SequenceOps(2));
        Thread t3 = new Thread(new SequenceOps(3));
        ValueHolder.setStage(1);

        t3.start();
        t2.start();
        t1.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Q1 All Exec-ed");

        //Q2

        Thread bt1 = new Thread(new QueueOps(true));
        Thread bt2 = new Thread(new QueueOps(false));
        Thread bt3 = new Thread(new QueueOps(true));
        Thread bt4 = new Thread(new QueueOps(false));


        bt4.start();
        bt3.start();
        bt2.start();
        bt1.start();

        bt1.join();
        bt2.join();
        bt3.join();
        bt4.join();

    }
}
