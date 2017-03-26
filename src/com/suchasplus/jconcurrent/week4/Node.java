package com.suchasplus.jconcurrent.week4;

/**
 * Powered by suchasplus@gmail.com on 2017/3/27.
 */
public class Node<T> {
    // value of node
    protected T value;
    // pointer to next node
    protected Node<T> next;

    /**
     * Default constructor with <code>value</code> equals <code>null</code>.
     */
    public Node() {
        this(null);
    }

    /**
     * Constructor which takes a value and initializes this as the value of
     * node.
     *
     * @param value
     *            new value for the node
     */
    public Node(T value) {
        this.value = value;
        this.next = null;
    }
}