package com.suchasplus.jconcurrent.week4;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Powered by suchasplus@gmail.com on 2017/3/27.
 */
public class LockFreeStack<T> {

    // internal stack reference
    private AtomicReference<Node<T>> stack;
    // counts the number of setAndCompare calls
    private AtomicInteger tries;

    /**
     * Default constructor initializing the stack <code>head</code> to null.
     */
    public LockFreeStack() {
        stack = new AtomicReference<>();
        tries = new AtomicInteger();
    }

    /**
     * Pushes the item argument on top of the stack.
     *
     * @param item
     *            value to push on stack
     */
    public void push(T item) {
        Node<T> oldHead = null, newHead = new Node<T>(item);
        do {
            oldHead = stack.get();
            newHead.next = oldHead;

            tries.getAndIncrement();
        } while (!stack.compareAndSet(oldHead, newHead));
    }

    /**
     * Pops the first (head) element from the stack.
     *
     * @return first value of the stack
     */
    public T pop() {
        Node<T> oldHead = null, newHead = null;
        do {
            oldHead = stack.get();

            if (oldHead == null) {
                return null;
            }

            newHead = oldHead.next;
            tries.getAndIncrement();
        } while (!stack.compareAndSet(oldHead, newHead));

        return oldHead.value;
    }

    /**
     * Gets the number of tries for the
     * {@link AtomicReference#compareAndSet(Object, Object)} method.
     *
     * @return number of times
     *         {@link AtomicReference#compareAndSet(Object, Object)} was called
     */
    public int getTries() {
        return tries.get();
    }
}