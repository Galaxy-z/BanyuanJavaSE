package com.banyuan.homework.abs.work01;

public class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle() {
        super();
    }

    public Rectangle(String color, int width, int height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPer() {
        return 2 * (width + height);
    }

    @Override
    public void showAll() {
        System.out.println("长:" + width);
        System.out.println("宽:" + height);
        System.out.println("颜色:" + getColor());
        System.out.println("周长:" + getPer());
        System.out.println("面积:" + getArea());

    }
}
