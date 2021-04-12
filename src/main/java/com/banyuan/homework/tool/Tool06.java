package com.banyuan.homework.tool;

import java.util.Scanner;

public class Tool06 {
    public static void main(String[] args) {
        int[] arr = new int[10];
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int num = (int) (Math.random() * 2016 + 1);
            arr[i] = num;
            max = num > max ? num : max;
        }

        System.out.println(max);

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数字:");
        int guess = scanner.nextInt();
        while (guess != max) {
            if (guess > max) {
                System.out.println("大了");
            } else {
                System.out.println("小了");
            }
            guess = scanner.nextInt();
        }
        System.out.println("对了");

    }
}
