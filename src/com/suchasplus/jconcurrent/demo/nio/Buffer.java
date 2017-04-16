package com.suchasplus.jconcurrent.demo.nio;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * Powered by suchasplus@gmail.com on 2017/4/16.
 *
 * position capacity limit
 */
public class Buffer {

    private static void eg() {
        ByteBuffer b = ByteBuffer.allocate(15);
        System.out.println("limit: " + b.limit() + " capacity: " + b.capacity() + " position: " + b.position());

        IntStream.range(0, 10).forEach((i) -> {
            b.put((byte)i);
        });

        System.out.println("limit: " + b.limit() + " capacity: " + b.capacity() + " position: " + b.position());

        b.flip();

        System.out.println("limit: " + b.limit() + " capacity: " + b.capacity() + " position: " + b.position());
        IntStream.range(0,5).forEach((i)-> System.out.println(b.get()));

        System.out.println();

        System.out.println("limit: " + b.limit() + " capacity: " + b.capacity() + " position: " + b.position());

        b.flip();

        System.out.println("limit: " + b.limit() + " capacity: " + b.capacity() + " position: " + b.position());


    }

    public static void main(String[] args) {
        eg();
    }
}
