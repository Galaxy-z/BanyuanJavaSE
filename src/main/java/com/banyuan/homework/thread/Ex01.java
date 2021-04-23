package com.banyuan.homework.thread;

public class Ex01 {
    public static void main(String[] args) {
        String s = "abcdefg";
        for (int i = s.length()-1; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(s.charAt(i));
        }
    }
}
