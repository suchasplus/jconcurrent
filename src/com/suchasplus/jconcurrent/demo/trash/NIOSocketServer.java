package com.suchasplus.jconcurrent.demo.trash;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//        Runnable server = new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    new NIOSocketServer(ADDR, PORT).start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Runnable client = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    new NIOSocketClient().start(ADDR, PORT, null);
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        new Thread(server).start();
//        new Thread(client, "client-A").start();
//        new Thread(client, "client-B").start();

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class NIOSocketServer {
    private Selector selector;
    private Map<SocketChannel, byte[]> dataMapper;
    private InetSocketAddress listenAddress;
    private boolean stop = false;

    private ExecutorService tp = Executors.newCachedThreadPool();

    private class CalcClient {
        private LinkedList<ByteBuffer> outQueue;

        CalcClient() {
            outQueue = new LinkedList<>();
        }
        public LinkedList<ByteBuffer> getOutQueue() {
            return outQueue;
        }
        public void enqueue(ByteBuffer bb) {
            outQueue.addFirst(bb);
        }
    }

    private class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey key, ByteBuffer buffer) {
            sk = key;
            bb = buffer;
        }

        @Override
        public void run() {
            CalcClient cc = (CalcClient) sk.attachment();
            cc.enqueue(bb);
            System.out.println("cc q size: " + cc.getOutQueue().size() +  " , cc loc: " + cc.toString());
            System.out.println("in HandleMsg sk: " + sk.toString());
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        }
    }

    public NIOSocketServer(String addr, int port) {
        listenAddress = new InetSocketAddress(addr, port);
        dataMapper = new HashMap<>();
    }

    public void stop() {
        stop = true;
    }

    public void start() throws IOException, InterruptedException {
        //selector = Selector.open();
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        serverChannel.socket().bind(listenAddress);
        SelectionKey acceptKey = serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIOServer started...");

        while (!stop) {
            selector.select();
            Iterator keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    doAccept(key);
                } else if (key.isReadable()) {
                    doRead(key);
                }
                else if (key.isWritable()) {
                    doWrite(key);
                }

            }
        }
    }

    private void doAccept(SelectionKey key) throws IOException {
//        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
//        SocketChannel channel = serverChannel.accept();
//        channel.configureBlocking(false);
//
//        Socket socket = channel.socket();
//        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
//        System.out.println("client Connected from: " + remoteAddr);
//
//        dataMapper.put(channel, null);
//        SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
//        clientKey.attach("123");
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);

        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("client Connected from: " + remoteAddr);

        //dataMapper.put(channel, null);
        CalcClient cc = new CalcClient();

        SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
        clientKey.attach(cc);
    }

    private void doRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int numRead = channel.read(buffer);

        if (numRead == -1) {
            dataMapper.remove(channel);
            System.out.println("Connection closed by client: " + channel.socket().getRemoteSocketAddress());
            channel.close();
            key.cancel();
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        System.out.println("AIOServer Got client msg: " + new String(data));
        buffer.flip();
        tp.execute(new HandleMsg(key, buffer));
//        byte[] data = new byte[numRead];
//        System.arraycopy(buffer.array(), 0, data, 0, numRead);
//        System.out.println("AIOServer Got client msg: " + new String(data));
//        channel.register(selector, SelectionKey.OP_WRITE);
//        dataMapper.put(channel, data);
//        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        //selector.wakeup();
    }

    private void doWrite(SelectionKey key) throws IOException, InterruptedException {
//        SocketChannel channel = (SocketChannel)key.channel();
//        byte[] buf = dataMapper.get(channel);
//        if (buf != null) {
//            dataMapper.remove(channel);
//            key.interestOps(SelectionKey.OP_READ);
//
//            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
//            String ret = new String(buf) + " " + Instant.now().toString();
//            ByteBuffer.wrap(ret.getBytes());
//            channel.write(byteBuffer);
//            System.out.println("doWrite to: " + channel.socket().getRemoteSocketAddress() +
//                    "; message: got" + ret);
//        }
        SocketChannel channel = (SocketChannel)key.channel();
        CalcClient cc = (CalcClient) key.attachment();

        LinkedList<ByteBuffer> outQueue = cc.getOutQueue();
        System.out.println(cc.toString() + " cc size: " + cc.getOutQueue().size() + " outQ: " + outQueue.size());
        Thread.sleep(1000);
        ByteBuffer bb =  outQueue.getLast();

        String s = "aaaa" + Instant.now();
        ByteBuffer newbb = ByteBuffer.wrap(s.getBytes());
        try {
            int len = channel.write(newbb);
            if(len == -1) {
                channel.close();
                key.cancel();
            }
            if(newbb.remaining() == 0) {
                outQueue.remove(bb);
            }
            System.out.println(s + " write len : " + len);
        } catch (Exception e) {
            System.out.printf("SERVER: failed to write to client");
            e.printStackTrace();
            channel.close();
            key.cancel();
        }
        if(outQueue.size() == 0) {
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}
