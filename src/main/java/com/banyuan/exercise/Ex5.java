package com.banyuan.exercise;

public class Ex5 {
    public static void main(String[] args) {
        int n = 23;
        //1.假设
        boolean flag = true;
        //2.检验
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                flag = false;
                break;
            }
        }
        //3.判断
        if (flag) {
            System.out.println(n + "是质数");
        } else {
            System.out.println(n + "不是质数");
        }
    }
}
