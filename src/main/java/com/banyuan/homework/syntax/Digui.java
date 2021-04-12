package com.banyuan.homework.syntax;

import java.util.Arrays;

public class Digui {
    static String i;

    public static void main(String[] args) {
//        hanoi(5,"A","B","C");
//        System.out.println(i);
        String s;
        System.out.println(i);
        Boolean b=new Boolean ("abcd");
        System.out.println(b);
        




    }

    public static int fibo(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return fibo(n - 1) + fibo(n - 2);
        }
    }

    public static void hanoi(int n, String a, String b, String c) {
        if (n == 1) {
            System.out.println(a + "->" + c);
        }
        else {
            hanoi(n - 1, a, c, b);
            System.out.println(a + "->" + c);
            hanoi(n - 1, b, a, c);
        }
    }
}

abstract class B{

}

