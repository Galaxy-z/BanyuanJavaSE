package com.banyuan.homework.collection;

import java.util.*;

public class Ex01 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String letter = scanner.next();
            if (letter.equals("#")){
                break;
            }
            list.add(letter);
        }
        list.sort(Comparator.comparing(String::toLowerCase));
        System.out.println(list);
    }
}
