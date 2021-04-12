package com.banyuan.homework.syntax;

import java.util.Arrays;

public class Syntax10 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        for (int i = 0; i < a.length/2; i++) {
            int temp = a[i];
            a[i] = a[a.length-1-i];
            a[a.length-1-i] = temp;
        }
        System.out.println(Arrays.toString(a));
    }
}
