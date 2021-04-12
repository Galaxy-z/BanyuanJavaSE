package com.banyuan.exercise;

public class Ex4 {
    public static void main(String[] args) {
        int x = 10, y = 100;
        boolean flag = false;
        for (int i = x; i < y; i++) {
            if (i % 3 == 0 && i % 7 == 0) {
                System.out.println("yes");
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("no");
        }
    }
}
