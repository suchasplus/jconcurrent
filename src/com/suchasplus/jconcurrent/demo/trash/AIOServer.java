package com.suchasplus.jconcurrent.demo.trash;

import javax.naming.Context;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class AIOServer implements Runnable {
    AtomicInteger clientId = new AtomicInteger(0);

    /**
     * @param args
     */
    public static void main(String[] args) {
        AIOServer server = new AIOServer();
        server.run();
    }

    @Override
    public void run() {

        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            AsynchronousChannelGroup asyncChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(executor, 10);
            final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(asyncChannelGroup)
                    .bind(new InetSocketAddress(7910));
            listener.accept(null,
                    new CompletionHandler<AsynchronousSocketChannel, Context>() {
                        @Override
                        public void completed(AsynchronousSocketChannel ch, Context context) {
                            clientId.addAndGet(1);
                            // accept the next connection
                            listener.accept(null, this);
                            // handle this connection
                            System.out.println("conn :" + clientId);
                            handle(ch, context);
                        }

                        @Override
                        public void failed(Throwable exc, Context context) {
                            exc.printStackTrace();
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handle(final AsynchronousSocketChannel ch, Context context) {
        final ByteBuffer buf = ByteBuffer.allocate(2048);
        final int id = clientId.get();
        //Future result =
        ch.read(buf, null, new CompletionHandler<Integer, Context>() {
            @Override
            public void completed(Integer result, Context context) {
                if (result > 0) {
                    buf.flip();
                    byte[] data = new byte[buf.limit()];
                    buf.get(data);
                    System.err.println(id + " read done！" + result + ",data=" + bin2hexstr(data));
                    buf.clear();
                }
                ch.read(buf, null, this);
            }

            @Override
            public void failed(Throwable exc, Context context) {

            }
        });



    }

    public String bin2hexstr(byte[] src) {
        return bin2hexstr(src, 0, src.length);
    }

    public String bin2hexstr(byte[] src, int start, int len) {
        char[] hex = new char[2];
        StringBuilder sb = new StringBuilder(len * 2);
        int abyte;
        for (int i = start; i < start + len; i++) {
            abyte = src[i] < 0 ? 256 + src[i] : src[i];
            hex[0] = HEX[abyte / 16];
            hex[1] = HEX[abyte % 16];
            sb.append(hex);
        }
        return sb.toString();
    }

    public final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
}
