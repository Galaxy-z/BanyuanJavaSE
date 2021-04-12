package com.banyuan.homework.tool;

import java.util.Objects;

public class Car {
    private String band;
    private String color;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(car.price, price) == 0 && Objects.equals(band, car.band) && Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(band, color, price);
    }

    @Override
    public String toString() {
        return "Car{" +
                "band='" + band + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}

