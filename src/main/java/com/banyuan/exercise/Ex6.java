package com.banyuan.exercise;

public class Ex6 {
    public static void main(String[] args) {
        int x = 36;
        int y = 24;
        int min = x < y ? x : y;
        int gys = 0;
        for (int i = 1; i <= min; i++) {
            if (x % i == 0 && y % i == 0) {
                gys = i;
            }
        }
        System.out.println(gys);

        int x1 = 15;
        int y1 = 17;
        int a = x1 < y1 ? x1 : y1;
        int b = x1 > y1 ? x1 : y1;
        while (b % a != 0) {
            int yu = b % a;
            b = a;
            a = yu;
        }
        System.out.println(a);

    }
}
