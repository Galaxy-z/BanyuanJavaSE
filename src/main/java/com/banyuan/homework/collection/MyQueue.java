package com.banyuan.homework.collection;

import java.util.LinkedList;


public class MyQueue {
    private LinkedList list = new LinkedList();

    void put(Object o){
        list.offer(o);
    }

    Object get(){
        return list.poll();
    }

    boolean isEmpty(){
        return list.isEmpty();
    }


}
