package com.banyuan.homework.syntax;

public class Syntax08 {
    public static void main(String[] args) {
        int count = 0;
        for (int a = 1; a <= 4; a++) {
            for (int b = 1; b <= 4; b++) {
                for (int c = 1; c <= 4; c++) {
                    if (a == b || b == c || a == c) {
                        continue;
                    }
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
