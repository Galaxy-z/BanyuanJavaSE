package com.banyuan.homework.string;

public class String06 {
    public static void main(String[] args) {
        String s = "abcdefg";
        char[] chars = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            chars[s.length()-1-i] = s.charAt(i);
        }
        System.out.println(chars);
    }
}
