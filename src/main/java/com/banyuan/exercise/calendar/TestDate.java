package com.banyuan.exercise.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) throws ParseException {
        Date now = new Date();
        System.out.println(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(now));
        Date date = sdf.parse("2102-11-12 14:35:21");
        System.out.println(date);
        System.out.println(date.getClass().getSimpleName());
    }


}
