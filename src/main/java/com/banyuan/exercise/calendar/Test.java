package com.banyuan.exercise.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(scanner.next());
        c.setTime(date);
        int today = c.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, 1);

        System.out.println("日\t一\t二\t三\t四\t五\t六");
        for (int i = 1; i < c.get(Calendar.DAY_OF_WEEK); i++) {
            System.out.print("\t");
        }
        for (int i = 0; i < c.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            int day = c.get(Calendar.DAY_OF_MONTH);

            if (c.get(Calendar.DAY_OF_MONTH) == today) {
                System.out.print("*");
            }

            System.out.print(day + "\t");
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println();
            }
            c.set(Calendar.DAY_OF_MONTH, day + 1);
        }
    }
}
