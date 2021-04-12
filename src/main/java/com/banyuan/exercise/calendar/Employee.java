package com.banyuan.exercise.calendar;

import java.util.Date;

public class Employee {
    private String name;

    private Date inTime;

    public Employee(String name, Date inTime) {
        this.name = name;
        this.inTime = inTime;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", inTime=" + inTime +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
}
