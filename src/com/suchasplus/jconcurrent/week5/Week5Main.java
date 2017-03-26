package com.suchasplus.jconcurrent.week5;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Powered by suchasplus@gmail.com on 2017/3/27.
 */
public class Week5Main {
    final static int VOLUME = 10000;
    final static int THR_COUNT = 10;



    public static void main(String[] args) throws InterruptedException {

        LinkedList<Integer> linkedList = new LinkedList<>();
        List<String> ls = new ArrayList<>(VOLUME);

        IntStream.range(0, VOLUME).forEach((i)->{
            linkedList.add(Math.abs(new Random().nextInt()));
            ls.add(String.valueOf((new Random()).nextLong()));
        });
        CopyOnWriteArrayList<String> cow1 = new CopyOnWriteArrayList<>(ls);
        CopyOnWriteArrayList<String> cow2 = new CopyOnWriteArrayList<>(cow1);

        System.out.println((cow1.hashCode() == cow1.hashCode()) + "\r\n" + System.identityHashCode(cow1) + "\r\n" + System.identityHashCode(cow2));
        // override hashcode equal, object addr not equal

        System.exit(0);

        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>(linkedList);
        ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>(linkedList);
        Instant bq_start = Instant.now();
        Thread[] threads = new Thread[THR_COUNT];
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i] = new Thread(new BQRun(bq));
            threads[i].start();
        }
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i].join();
        }
        Duration bqDuration = Duration.between(bq_start, Instant.now());
        Instant clq_start = Instant.now();
        threads = new Thread[THR_COUNT];
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i] = new Thread(new CLQRun(clq));
            threads[i].start();
        }
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i].join();
        }
        Duration clqDuration = Duration.between(clq_start, Instant.now());

        System.out.println("without SIZE COUNT: BQ: " + bqDuration.toString() + " , CLQ: " + clqDuration.toString());

        bq = new LinkedBlockingQueue<>(linkedList);
        clq = new ConcurrentLinkedQueue<>(linkedList);
        bq_start = Instant.now();
        threads = new Thread[THR_COUNT];
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i] = new Thread(new BQCountRun(bq));
            threads[i].start();
        }
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i].join();
        }
        bqDuration = Duration.between(bq_start, Instant.now());
        clq_start = Instant.now();
        threads = new Thread[THR_COUNT];
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i] = new Thread(new CLQCountRun(clq));
            threads[i].start();
        }
        for (int i = 0; i < THR_COUNT; i++) {
            threads[i].join();
        }
        clqDuration = Duration.between(clq_start, Instant.now());
        System.out.println("with SIZE COUNT: BQ: " + bqDuration.toString() + " , CLQ: " + clqDuration.toString());
        //without SIZE COUNT: BQ: PT0.006S , CLQ: PT0.004S
        //with SIZE COUNT: BQ: PT0.005S , CLQ: PT0.004S
        /*
        D:\Dev\jdk1.8.0_60\bin\java -Didea.launcher.port=7543 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.2.3\bin" -Dfile.encoding=UTF-8 -classpath "D:\Dev\jdk1.8.0_60\jre\lib\charsets.jar;D:\Dev\jdk1.8.0_60\jre\lib\deploy.jar;D:\Dev\jdk1.8.0_60\jre\lib\javaws.jar;D:\Dev\jdk1.8.0_60\jre\lib\jce.jar;D:\Dev\jdk1.8.0_60\jre\lib\jfr.jar;D:\Dev\jdk1.8.0_60\jre\lib\jfxswt.jar;D:\Dev\jdk1.8.0_60\jre\lib\jsse.jar;D:\Dev\jdk1.8.0_60\jre\lib\management-agent.jar;D:\Dev\jdk1.8.0_60\jre\lib\plugin.jar;D:\Dev\jdk1.8.0_60\jre\lib\resources.jar;D:\Dev\jdk1.8.0_60\jre\lib\rt.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\access-bridge-64.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\cldrdata.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\dnsns.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\jaccess.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\jfxrt.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\localedata.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\nashorn.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\sunec.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\sunjce_provider.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\sunmscapi.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\sunpkcs11.jar;D:\Dev\jdk1.8.0_60\jre\lib\ext\zipfs.jar;D:\Dev\github\jconcurrent\output\production\jconcurrent;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.2.3\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain com.suchasplus.jconcurrent.week5.Week5Main
without SIZE COUNT: BQ: PT0.006S , CLQ: PT0.004S
with SIZE COUNT: BQ: PT0.005S , CLQ: PT0.004S

Process finished with exit code 0

         */
    }

}

class BQRun implements Runnable {

    final  BlockingQueue<Integer> queue;

    public BQRun(BlockingQueue<Integer> q) {
        queue = q;
    }

    @Override
    public void run() {
        while(queue.poll() != null) {
        }
    }
}

class CLQRun implements Runnable {

    final  ConcurrentLinkedQueue<Integer> queue;

    public CLQRun(ConcurrentLinkedQueue<Integer> q) {
        queue = q;
    }

    @Override
    public void run() {
        while(queue.poll() != null) {
        }
    }
}

class BQCountRun implements Runnable {

    final  BlockingQueue<Integer> queue;

    public BQCountRun(BlockingQueue<Integer> q) {
        queue = q;
    }

    @Override
    public void run() {
        while(!queue.isEmpty()) {
            queue.poll();
        }
    }
}

class CLQCountRun implements Runnable {

    final  ConcurrentLinkedQueue<Integer> queue;

    public CLQCountRun(ConcurrentLinkedQueue<Integer> q) {
        queue = q;
    }

    @Override
    public void run() {
        while(!queue.isEmpty()) {
            queue.poll();
        }
    }
}
