package com.suchasplus.jconcurrent.demo.trash;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * Powered by suchasplus@gmail.com on 2017/4/25.
 */
public class TestLinkedList {
    public static void main(String[] args) {
        Queue<Integer> data = new LinkedList<>();
        IntStream.range(0, 10).forEach(data::add);
        int i = 100;
        while(i > 0) {
            System.out.println(data.poll());
            i--;
        }
        IntStream.range(0,10).forEach(System.out::println);
    }
}
