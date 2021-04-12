package com.banyuan.homework.syntax;

import java.util.Arrays;

public class Syntax12 {
    public static void main(String[] args) {
        int[] a = {3,5,1,7,8,11,22};
        int[] b = {2,8,6,4,88,66,44};
        for (int e:
             a) {
            System.out.println(e);
        }
        for (int e:
             b) {
            System.out.println(e);
        }

        int[] c = new int[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        System.out.println(Arrays.toString(c));

        Arrays.sort(c);
        System.out.println(Arrays.toString(c));

    }
}
