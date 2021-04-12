package com.banyuan.homework.syntax;

public class Syntax07 {
    public static void main(String[] args) {
        int num;
        boolean isPrime;
        for (num = 200; ; num++) {
            isPrime = true;
            for (int i = 2; i < num; i++) {
                if (num % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime){
                System.out.println(num);
                break;
            }
        }
    }
}
