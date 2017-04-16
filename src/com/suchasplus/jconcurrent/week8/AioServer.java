package com.suchasplus.jconcurrent.week8;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class AioServer implements Runnable {
    private AsynchronousChannelGroup asyncChannelGroup;
    private AsynchronousServerSocketChannel listener;
    private boolean shutdown = false;

    public AioServer(int port) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        listener = AsynchronousServerSocketChannel.open(asyncChannelGroup).bind(new InetSocketAddress(port));
    }

    public void run() {
        try {

            AioAcceptHandler acceptHandler = new AioAcceptHandler();
            listener.accept(listener, new AioAcceptHandler());
            while (!shutdown) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finished server");
        }
    }

    public static void main(String... args) throws Exception {
        AioServer server = new AioServer(18000);
        new Thread(server).start();
    }
}