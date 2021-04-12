package com.banyuan.exercise.math;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
//        System.out.println((int)(Math.random()*71+10));

        int[] arr = new int[10];
        int max = (int) (Math.random() * 100 + 1);
        int min = max;
        arr[0] = max;
        for (int i = 1; i < 10; i++) {
            int num = (int) (Math.random() * 100 + 1);
            arr[i] = num;
            max = max > num ? max : num;
            min = min < num ? min : num;
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("max:" + max);
        System.out.println("min:" + min);
    }
}
