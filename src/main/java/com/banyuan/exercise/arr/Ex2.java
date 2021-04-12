package com.banyuan.exercise.arr;

import java.util.Arrays;

public class Ex2 {
    public static void main(String[] args) {

        //1.将两个有序数组合并
        int[] arr1 = {1, 3, 5, 7, 9};
        int[] arr2 = {2, 4, 6, 8, 10, 12, 14, 16};

        int[] arr = new int[arr1.length + arr2.length];
        int index1 = 0;
        int index2 = 0;

        for (int index = 0; index < arr.length; index++) {

            arr[index] = arr1[index1] < arr2[index2] ? arr1[index1++] : arr2[index2++];
            if (index1 == arr1.length) {
                index1--;
            }
            if (index2 == arr2.length) {
                index2--;
            }

        }
        System.out.println(Arrays.toString(arr));

        //2.二分搜索法，折半查找法
        int[] arr3 = new int[]{2, 4, 6, 8, 10, 12, 14, 16};
        int n = 12;
        int low = 0, high = arr1.length - 1;
        int index = -1;
        while (low <= high){
            int mid = (low + high) / 2;
            if (n < arr1[mid]){
                high = mid - 1;
            }else if(n >arr1[mid]){
                low = mid + 1;
            }
            else{
                index = mid;
                break;
            }
        }


        System.out.println(index);

    }
}
