package com.banyuan.exercise;

public class Ex3 {
    public static void main(String[] args) {
//        int n = 20;
//        for (int i = 1; i <= n/2; i++) {
//            if (n % i == 0){
//                System.out.println(i);
//            }
//        }

        int sum;
        for (int i = 1; i < 1000; i++) {
            sum = 0;
            for (int j = 1; j <= i/2 ; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }

            if (sum == i){
                System.out.println(i);
            }

        }
    }
}
