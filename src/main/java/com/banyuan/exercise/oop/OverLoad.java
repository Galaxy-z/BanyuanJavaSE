package com.banyuan.exercise.oop;

import java.util.Arrays;

public class OverLoad {
    public void bubbleSort(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = new int[] {4,2,6,7,5,9};
        OverLoad o = new OverLoad();
        o.bubbleSort(a);
        System.out.println(Arrays.toString(a));
    }
}
