package com.banyuan.exercise.arr;

import java.util.Arrays;

public class Ex3 {
    public static void main(String[] args) {
        //冒泡排序
        int[] arr = {8, 3, 5, 2, 6, 9, 7, 4};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }
        System.out.println(Arrays.toString(arr));

        //选择排序
        arr = new int[]{8, 3, 5, 2, 6, 9, 7, 4};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;

                }

            }
        }
        System.out.println(Arrays.toString(arr));

        //直插法
        arr = new int[]{8, 3, 5, 2, 6, 9, 7, 4};
        for (int i = 1; i < arr.length; i++) {
            int index = i;
            int num = arr[i];
            while (index >= 1 && num < arr[index - 1]) {
                arr[index] = arr[index - 1];
                index--;
            }
            arr[index] = num;
        }
        System.out.println(Arrays.toString(arr));

    }
}

