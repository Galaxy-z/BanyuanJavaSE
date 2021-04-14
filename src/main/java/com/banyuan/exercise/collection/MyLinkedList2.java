package com.banyuan.exercise.collection;

public class MyLinkedList2 {
    private Node2 first;
    private Node2 last;
    private int size;

    public void addLast(Object obj) {
        Node2 node = new Node2();
        node.setObj(obj);
        if (size == 0) {
            first = node;
            last = node;
        } else {
            node.setPre(last);
            last.setNext(node);
            last = node;
        }
        size++;
    }

    public void addFirst(Object obj) {
        Node2 node = new Node2();
        node.setObj(obj);
        if (size == 0) {
            first = node;
            last = node;
        } else {
            node.setNext(first);
            first.setPre(node);
            first = node;
        }
        size++;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index" + index);
        }
        Node2 temp;
        if (index < (size / 2)) {
            temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
        } else {
            temp = last;
            for (int i = 0; i < size - 1 - index; i++) {
                temp = temp.getPre();
            }
        }
        return temp.getObj();
    }

    public void add(int index, Object obj) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index" + index);
        }
        Node2 node = new Node2();
        node.setObj(obj);

        if (index == 0) {
            addFirst(obj);
        } else if (index == size - 1) {
            addLast(obj);
        } else {
            Node2 pre;
            Node2 next;
            if (index < (size / 2)) {
                next = first;
                for (int i = 0; i < index; i++) {
                    next = next.getNext();
                }

            } else {
                next = last;
                for (int i = 0; i < size - 1 - index; i++) {
                    next = next.getPre();
                }
            }

            pre = next.getPre();
            pre.setNext(node);
            node.setPre(pre);

            next.setPre(node);
            node.setNext(next);
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index" + index);
        }

        if (index == 0) {
            first = first.getNext();
        } else if (index == size - 1) {
            last = last.getPre();
        } else {
            Node2 left;
            Node2 current;
            Node2 right;
            if (index < (size / 2)) {
                current = first;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }

            } else {
                current = last;
                for (int i = 0; i < size - 1 - index; i++) {
                    current = current.getPre();
                }
            }
            left = current.getPre();
            right = current.getNext();
            left.setNext(right);
            right.setPre(left);

        }
        size--;

    }

    public static void main(String[] args) {
        MyLinkedList2 list = new MyLinkedList2();
        list.addLast("aaa");
        list.addLast("bbb");
        list.addLast("ccc");
        list.addLast("ddd");
        list.add(1, "ppp");
        list.remove(0);
        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }

    }

}

class Node2 {
    private Object obj;

    private Node2 pre;

    private Node2 next;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Node2 getPre() {
        return pre;
    }

    public void setPre(Node2 pre) {
        this.pre = pre;
    }

    public Node2 getNext() {
        return next;
    }

    public void setNext(Node2 next) {
        this.next = next;
    }
}
