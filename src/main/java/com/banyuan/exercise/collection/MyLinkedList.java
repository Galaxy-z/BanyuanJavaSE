package com.banyuan.exercise.collection;

public class MyLinkedList {
    private Node first;

    private int size;

    // 追加到头部
    public void addFirst(Object obj) {
        first = new Node(obj, first);
        size++;
    }

    public void addLast(Object obj) {
        Node node = new Node();
        node.setObj(obj);
        if (size == 0) {
            first = node;
        } else {
            Node item = first;
            for (int i = 0; i < size - 1; i++) {
                item = item.getNext();
            }
            item.setNext(node);

        }
        size++;
    }

    public void add(int index, Object obj) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("index:" + index);
        }
        Node node = new Node();
        node.setObj(obj);
        if (size == 0) {
            first = node;
        } else {
            Node left = first;
            for (int i = 0; i < index - 1; i++) {
                left = left.getNext();
            }
            Node right = left.getNext();
            left.setNext(node);
            node.setNext(right);
        }
        size++;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("index:" + index);
        }

        Node item = first;
        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }
        return item.getObj();

    }

    public void removeFirst() {
        if (size == 0) {
            throw new IllegalStateException("列表为空，不可删除");
        }
        first = first.getNext();
        size--;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.addLast("aaa");
        list.addLast("bbb");
        list.addLast("ccc");
        list.addLast("ddd");
        list.add(1, "eee");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}

class Node {
    private Object obj;

    private Node next;

    public Node() {
    }

    public Node(Object obj, Node next) {
        this.obj = obj;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "obj=" + obj +
                ", next=" + next +
                '}';
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
