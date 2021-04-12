package com.banyuan.exercise.collection;

import java.util.LinkedList;

public class TestLinkedList {
    public static void main(String[] args) {
        LinkedList<String> stack = new LinkedList<>();
        stack.push("111");
        stack.push("222");
        System.out.println(stack);

        LinkedList<String> queue = new LinkedList<>();
        queue.offer("111");
        queue.offer("222");
        System.out.println(queue.poll());
    }
}
