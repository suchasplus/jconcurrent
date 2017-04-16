package com.suchasplus.jconcurrent.week8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class AioWriteHandler implements CompletionHandler<Integer,ByteBuffer>
{
    private AsynchronousSocketChannel socket;

    public AioWriteHandler(AsynchronousSocketChannel socket) {
        this.socket = socket;
    }

    @Override
    public void completed(Integer i, ByteBuffer buf) {
        if (i > 0) {
            socket.write(buf, buf, this);
        }
        else if (i == -1) {
            try {
                System.out.println("client disconnected:" + socket.getRemoteAddress().toString());
                buf = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("cancelled");
    }

}