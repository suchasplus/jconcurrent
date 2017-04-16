package com.suchasplus.jconcurrent.week8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class AioAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

    @Override
    public void completed(AsynchronousSocketChannel socket, AsynchronousServerSocketChannel attachment)
    {
        try {
            attachment.accept(attachment, this);
            System.out.println("client connected:" + socket.getRemoteAddress().toString());
            startRead(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment)
    {
        exc.printStackTrace();
    }

    public void startRead(AsynchronousSocketChannel socket) {
        ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
        AioReadHandler rd=new AioReadHandler(socket);
        socket.read(clientBuffer, clientBuffer, rd);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}