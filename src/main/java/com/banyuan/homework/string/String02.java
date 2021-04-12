package com.banyuan.homework.string;

public class String02 {
    public static void main(String[] args) {
        String s = "java@oracle.com";
        if (s.contains("@")) {
            System.out.println("包含");
        } else {
            System.out.println("不包含");
        }
    }
}
