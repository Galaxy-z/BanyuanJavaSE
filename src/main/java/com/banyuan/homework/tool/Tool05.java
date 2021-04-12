package com.banyuan.homework.tool;

import java.util.Calendar;

public class Tool05 {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(1921, Calendar.JULY, 23);
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        

    }
}
