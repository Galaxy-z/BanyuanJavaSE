package com.banyuan.homework.abs.work01;

public abstract class Shape {
    private int area;
    private int per;
    private String color;

    public Shape() {
    }

    public Shape(String color) {
        this.color = color;
    }

    public abstract double getArea();

    public abstract double getPer();

    public abstract void showAll();

    public String getColor() {
        return color;
    }
}
