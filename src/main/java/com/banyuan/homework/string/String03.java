package com.banyuan.homework.string;

public class String03 {
    public static void main(String[] args) {
        String s = "java@oracle.com";
        System.out.println(s.substring(s.indexOf("@") + 1, s.indexOf(".")));
    }
}
