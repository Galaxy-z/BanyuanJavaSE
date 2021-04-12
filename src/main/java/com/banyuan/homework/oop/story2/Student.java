package com.banyuan.homework.oop.story2;

public class Student {
    private String name;
    private int money;

    public Student(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void lend(Student stu,int num){
        this.money -= num;
        stu.money += num;
    }

    public void borrow(Student stu, int num){
        stu.lend(this, num);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
