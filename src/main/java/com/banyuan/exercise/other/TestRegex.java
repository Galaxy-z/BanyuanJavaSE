package com.banyuan.exercise.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("1321414");
        boolean b = m.matches();
        System.out.println(b);
    }
}
