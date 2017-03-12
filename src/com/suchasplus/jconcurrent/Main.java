package com.suchasplus.jconcurrent;

/**
 * Powered by suchasplus@gmail.com on 2017/3/6.
 */
public class Main {

    final static String amd64 = "amd64";

    public static void main(String[] args)  {
        String arch = System.getProperty("os.arch");
        if(arch.equals(amd64)) {
            System.out.println("jvm is X86_64, exec on an i586 jvm, Now EXIT");
            return;
        }
        LongHolder lh = new LongHolder();
        One w1 = new One(lh);
        NegOne w2 = new NegOne(lh);
        w1.setDaemon(true);
        w2.setDaemon(true);
        w1.start();
        w2.start();
        while (true) {
            long temp = lh.value;
            String str = toBinary(temp);
            if (!str.equals("0000000000000000000000000000000000000000000000000000000000000001")
                    && !str.equals("1111111111111111111111111111111111111111111111111111111111111111")) {
                System.out.println("Long is not violate write on i586 arch");
                System.out.println(temp);
                System.out.println(str);
                break;
            }
        }

    }

    private static String toBinary(long l) {
        StringBuilder sb = new StringBuilder(Long.toBinaryString(l));
        while (sb.length() < 64) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }



}

class LongHolder {
    public long value = 1L;
}

class One extends Thread {

    public One(LongHolder lh) {
        this.lh = lh;
    }

    private LongHolder lh;

    public void run() {
        while (true) {
            lh.value = 1l;
        }
    }
}

class NegOne extends Thread {

    public NegOne(LongHolder lh) {
        this.lh = lh;
    }

    private LongHolder lh;

    public void run() {
        while (true) {
            lh.value = -1l;
        }
    }
}
