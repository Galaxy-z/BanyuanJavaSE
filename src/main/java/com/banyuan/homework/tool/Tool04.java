package com.banyuan.homework.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool04 {
    public static void main(String[] args) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String s = sdf.format(now);
        System.out.println(s);
    }
}
