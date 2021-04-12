package com.banyuan.exercise.arr;

import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) {
        int[] arr = {1, 9, 5, 3, 7, 4, 6, 8};
//        //1.倒序打印
//        for (int i = arr.length - 1; i >= 0; i--) {
//            System.out.println(arr[i]);
//        }

//        //2.统计偶数个数
//        int count = 0;
//        for (int e :
//                arr) {
//            if (e % 2 == 0) {
//                count++;
//            }
//        }
//        System.out.println(count);

        //3.找到该数组里的最大值和最小值
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("最大值：" + max + ",最小值：" + min);

        arr = new int[]{1, 8, 5, 3, 7, 9, 6, 2};
        //4.最大值和最小值交换
        int maxIndex = 0;
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        int temp = arr[maxIndex];
        arr[maxIndex] = arr[minIndex];
        arr[minIndex] = temp;
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 8, 5, 3, 7, 9, 6, 2};
        //5.倒序排列
        for (int i = 0; i < arr.length / 2; i++) {
            int temp1 = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp1;
        }
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 8, 5, 3, 7, 9, 6, 2};
        //6.将某个数组里的奇数放入到一个新数组里

        arr = new int[]{1, 8, 5, 3, 7, 9, 0, 0};
        //7.头部插入数字6
        for (int i = arr.length-1; i > 0; i--) {
            arr[i] = arr[i-1];
        }
        arr[0] = 6;
        System.out.println(Arrays.toString(arr));

    }
}
