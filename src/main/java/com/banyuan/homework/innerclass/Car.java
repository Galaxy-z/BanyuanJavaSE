package com.banyuan.homework.innerclass;

import java.io.*;

public class Car implements Serializable {
    private String brand;
    private String model;
    private String color;
    private Tyre[] tyres;
    private int speed;

    public Car(String brand, String model, String color, Tyre tyre) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.tyres = new Tyre[]{tyre, tyre, tyre, tyre};
        speed = tyre.getDiameter() * 5;
    }

    public void drive() {
        System.out.println("速度为:" + speed);
        for (Tyre tyre : tyres) {
            tyre.roll();
        }
    }

    public static void main(String[] args) {
        Tyre tyre = new Tyre("Michelin", 60);
        Car car = new Car("奥迪", "A6", "白色", tyre);
        car.drive();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Car.txt"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Car.txt"))) {
            oos.writeObject(car);
            Car c = (Car) ois.readObject();
            c.drive();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class Tyre implements Serializable {
        private String brand;
        private int diameter;

        public Tyre(String brand, int diameter) {
            this.brand = brand;
            this.diameter = diameter;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getDiameter() {
            return diameter;
        }

        public void setDiameter(int diameter) {
            this.diameter = diameter;
        }

        public void roll() {
            System.out.println("滋啦滋啦");
        }
    }
}
