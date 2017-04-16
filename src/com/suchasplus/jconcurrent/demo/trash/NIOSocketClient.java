package com.suchasplus.jconcurrent.demo.trash;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class NIOSocketClient {
    private Selector selector;
    private InetSocketAddress hostAddress;
    private SocketChannel client;

    protected void start(String addr, int port, String fml) throws IOException, InterruptedException  {
//        hostAddress = new InetSocketAddress(addr, port);
//        client = SocketChannel.open(hostAddress);
//        client.configureBlocking(true);
//        selector = Selector.open();
//        client.register(selector, SelectionKey.OP_READ);
//
//
//        String threadName = Thread.currentThread().getName();
//        System.out.println("Client " + threadName + " Start");
//        String[] messages = new String[]{threadName + ": test1", threadName + ": test2", threadName + ": test3"};
//        for (String msg : messages) {
//            byte[] message = msg.getBytes();
//            ByteBuffer buffer = ByteBuffer.wrap(message);
//            client.write(buffer);
//            System.out.println(" Buffer : " + buffer.toString() + ", Msg : " + new String(message));
//            buffer.clear();
//            //Thread.sleep(1000);
//        }
//        Thread.sleep(1000);
//        for(String msg : messages) {
//            ByteBuffer buffer = ByteBuffer.allocate(256);
//            client.read(buffer);
//            try {
//                Charset charset = Charset.forName("UTF-8");;
//                CharsetDecoder decoder = charset.newDecoder();
//                CharBuffer charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
//                System.out.println("client get return: " + charBuffer.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            buffer.clear();
//        }
//        client.close();


//        Socket client = new Socket();
//        client.connect(new InetSocketAddress(addr, port));
//        PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
//        writer.print("test " + Thread.currentThread().getName());
//        writer.println();
//        writer.flush();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//        System.out.printf("from server: "+ reader.readLine());

        Socket socket = new Socket(addr, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("test " + Thread.currentThread().getName());
        String ret = in.readUTF();
        System.out.println("------------FROM SERVER ret " + ret);
        out.close();
        in.close();
    }
}
