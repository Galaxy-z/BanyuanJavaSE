package com.banyuan.homework.syntax;

public class Syntax11 {
    public static void main(String[] args) {
        int[] a = {18,25,7,36,13,2,89,63};
        int index = 0;
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max){
                max = a[i];
                index = i;
            }
        }
        System.out.println(index);
    }
}
