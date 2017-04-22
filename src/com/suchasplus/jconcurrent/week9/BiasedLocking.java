package com.suchasplus.jconcurrent.week9;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Powered by suchasplus@gmail.com on 2017/4/22.
 */
public class BiasedLocking {

    final static int CAP = 1000 * 1000 * 10;
    public static List<Integer> numList = new Vector<>(CAP);

    public static void main(String[] args)  throws InterruptedException{
        Arrays.asList(new String[]{"java.version", "os.arch", "os.name", "os.version"}).forEach((env) ->
                System.out.println(env + " " + System.getProperty(env)));
        long begin = System.currentTimeMillis();
        int counter = 0;
        int startNum = 0;

        while (counter < CAP) {
            numList.add(startNum);
            startNum += 2;
            counter++;
        }

        long end = System.currentTimeMillis();
        System.out.println("ms: " + (end - begin));
    }
}
