package com.banyuan.homework.syntax;

public class Syntax06 {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) {
                sum += i;
            }
        }
        System.out.println(sum);

        sum = 0;
        int j = 1;
        while (j <= 100) {
            if (j % 3 == 0) {
                sum += j;
            }
            j++;
        }
        System.out.println(sum);

        sum = 0;
        int k = 1;
        do {
            if (k % 3 == 0) {
                sum += k;
            }
            k++;
        } while (k <= 100);
        System.out.println(sum);
    }
}
