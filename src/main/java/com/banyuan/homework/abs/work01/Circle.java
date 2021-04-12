package com.banyuan.homework.abs.work01;

public class Circle extends Shape {
    private double radius;

    public Circle() {
        super();
    }

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPer() {
        return 2 * radius * Math.PI;
    }

    @Override
    public void showAll() {
        System.out.println("半径:" + radius);
        System.out.println("颜色:" + getColor());
        System.out.println("周长:" + getPer());
        System.out.println("面积:" + getArea());
    }
}
