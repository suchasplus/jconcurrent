package com.suchasplus.jconcurrent.week4;

/**
 * Powered by suchasplus@gmail.com on 2017/3/27.
 */
public class StackRunnable implements Runnable {

    // number of iterations
    private int iterations;
    // shared stack instance
    private LockFreeStack<Integer> stack;

    /**
     *
     * @param iterations
     * @param stack
     */
    public StackRunnable(int iterations, LockFreeStack<Integer> stack) {
        this.iterations = iterations;
        this.stack = stack;
    }

    /**
     *
     */
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            // push current iteration number
            stack.push(i);
            // pop item from stack
            stack.pop();
        }
    }
}