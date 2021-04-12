package com.banyuan.exercise.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TestArrayList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(Integer.valueOf(1));

        for (int s : list) {
            System.out.println(s);
        }
        ListIterator<Integer> listIterator = list.listIterator();
        listIterator.next();
        listIterator.add(4);
        System.out.println(list);




    }

}
