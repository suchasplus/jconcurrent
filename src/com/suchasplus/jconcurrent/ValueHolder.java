package com.suchasplus.jconcurrent;

/**
 * Powered by suchasplus@gmail.com on 2017/3/6.
 */
public class ValueHolder {
    static int i = -1;
    public static int getValue() {
        return i;
    }

    public synchronized static void setStage(int value) {
        i = value;
    }
}
