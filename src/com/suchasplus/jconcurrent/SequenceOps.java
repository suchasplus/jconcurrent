package com.suchasplus.jconcurrent;

/**
 * Powered by suchasplus@gmail.com on 2017/3/6.
 */
public class SequenceOps implements Runnable{

    private int flag = 0;

    public SequenceOps(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while(true) {
            if(ValueHolder.getValue() == flag) {
                for (int idx = 0; idx < 2; idx++) {
                    try {
                        Thread.sleep(10);
                        System.out.println("Thread "+flag+ " sleepy:" + idx);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ValueHolder.setStage(flag + 1);
                System.out.println("Thread "+flag+ " Exec Done! next Stage is: "  +
                    ValueHolder.getValue()
                );
                break;
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
