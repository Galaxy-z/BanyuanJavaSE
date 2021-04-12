package com.banyuan.exercise.calendar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class GetEmployee {
    public static void main(String[] args) {

        Employee[] employees = new Employee[10];
        for (int i = 0; i < employees.length; i++) {
            String name = "";
            for (int j = 0; j < 3; j++) {
                name += (char) (int) (Math.random() * 26 + 97);
            }

            int year = (int) (Math.random() * 21 + 1980);
            int month = (int) (Math.random() * 12);
            int day = (int) (Math.random() * 31 + 1);

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day, 0, 0, 0);
            Date date = cal.getTime();

            employees[i] = new Employee(name, date);
        }


        Arrays.sort(employees, (e1, e2) -> e2.getInTime().compareTo(e1.getInTime()));

        System.out.println(Arrays.toString(employees));
    }
}
