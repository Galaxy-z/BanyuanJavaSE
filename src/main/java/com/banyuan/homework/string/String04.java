package com.banyuan.homework.string;

public class String04 {
    public static void main(String[] args) {
        String s = " I love Java  ";
        System.out.println(s.length() - s.trim().length() + "个空格");
    }
}
