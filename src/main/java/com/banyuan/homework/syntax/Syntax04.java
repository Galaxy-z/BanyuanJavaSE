package com.banyuan.homework.syntax;

public class Syntax04 {
    public static void main(String[] args) {
        int num = 345;
        int sum = 0;
        while (num!=0){
            sum += num%10;
            num /=10;
        }
        System.out.println(sum);
    }
}
