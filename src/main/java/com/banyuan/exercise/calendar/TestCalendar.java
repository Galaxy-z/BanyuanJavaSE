package com.banyuan.exercise.calendar;

import java.util.Calendar;

public class TestCalendar {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);
        cal.set(1949, Calendar.OCTOBER, 1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);
        System.out.println(cal.getActualMaximum(Calendar.DATE));
    }
}
