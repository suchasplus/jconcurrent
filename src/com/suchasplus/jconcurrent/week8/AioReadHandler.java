package com.suchasplus.jconcurrent.week8;

import org.mariuszgromada.math.mxparser.Expression;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class AioReadHandler implements CompletionHandler<Integer,ByteBuffer> {
    private AsynchronousSocketChannel socket;
    private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
    public  String msg;

    public AioReadHandler(AsynchronousSocketChannel socket) {
        this.socket = socket;
    }

    @Override
    public void completed(Integer i, ByteBuffer buf) {
        if (i > 0) {
            buf.flip();
            try {
                msg = decoder.decode(buf).toString();
                System.out.println("server recv " + socket.getRemoteAddress().toString() + " :" + msg);
                buf.compact();
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket.read(buf, buf, this);
            Double ret = new Expression(msg).calculate();
            try {
                String sendString="AIO :"+ msg.trim() + " = " + ret + "\n";
                ByteBuffer clientBuffer=ByteBuffer.wrap(sendString.getBytes("UTF-8"));
                socket.write(clientBuffer, clientBuffer, new AioWriteHandler(socket));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AioReadHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (i == -1) {
            try {
                System.out.println("client disconnect:" + socket.getRemoteAddress().toString());
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