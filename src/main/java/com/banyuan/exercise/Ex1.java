package com.banyuan.exercise;

public class Ex1 {
    public static void main(String[] args) {
        int n = 6;
        int x = 1;
        while (n > 1) {
            x *= n;
            n--;
        }
        System.out.println(x);
    }
}
