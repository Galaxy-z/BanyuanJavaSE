package com.banyuan.homework.syntax;

public class Syntax09 {
    public static void main(String[] args) {
        int sum;
        for (int i = 1; i < 1000; i++) {
            sum = 0;
            for (int j = 1; j < i; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }
            if (sum == i) {
                System.out.println(i);
            }
        }
    }
}
