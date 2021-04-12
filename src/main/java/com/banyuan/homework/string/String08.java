package com.banyuan.homework.string;

import java.util.Scanner;

public class String08 {
    public static void main(String[] args) {
        String s = "11#2#333#444#55";
        String[] strings = s.split("#");
        int sum = 0;
        for (String string : strings) {
            sum += Integer.parseInt(string);
        }
        System.out.println(sum);

    }

}
