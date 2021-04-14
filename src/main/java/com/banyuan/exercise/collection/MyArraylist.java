package com.banyuan.exercise.collection;

import java.util.Arrays;

public class MyArraylist {

    private Object[] arr = new Object[5];

    private int size;

    public void add(Object obj) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + 5);
        }
        arr[size++] = obj;
    }

    public void addFirst(Object obj) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + 5);
        }
        System.arraycopy(arr, 0, arr, 1, size);
        arr[0] = obj;
        size++;
    }

    public void removeFirst() {
        if (size >= 1) {
            System.arraycopy(arr, 1, arr, 0, size - 1);
            size--;
        }else {
            throw new IllegalStateException("容器为空，不可删除元素");
        }
    }

    public void add(int index, Object obj){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index:" + index);
        }
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + 5);
        }
        System.arraycopy(arr,index, arr, index+1, size-index);
        arr[index] = obj;
        size++;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index:" + index);
        }
        return arr[index];
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        MyArraylist list = new MyArraylist();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add(1, "ddd");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));

        }

    }
}
