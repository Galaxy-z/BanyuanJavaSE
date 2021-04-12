package com.banyuan.homework.string;

public class String05 {
    public static void main(String[] args) {
        String s = "aAbBcCdDeE";
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (chars[i]>=97){
                chars[i] = (char)(chars[i] - 32);
            }else {
                chars[i] = (char)(chars[i] + 32);

            }
        }
        System.out.println(String.valueOf(chars));
    }
}
