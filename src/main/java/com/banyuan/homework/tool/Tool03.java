package com.banyuan.homework.tool;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Tool03 {
    public static void main(String[] args) {
        double[] arr = {12.23,22.1,8.88,55.5,33.78};
        int[] ints= new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ints[i] = (int) Math.round(arr[i]);
        }
        Arrays.sort(ints);
        System.out.println(Arrays.toString(ints));
    }
}
