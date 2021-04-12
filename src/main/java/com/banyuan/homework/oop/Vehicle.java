package com.banyuan.homework.oop;

public class Vehicle {
    private int speed;
    private int size;

    public Vehicle(int speed, int size) {
        this.speed = speed;
        this.size = size;
    }

    public void move(){

    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void speedUp(){
        speed++;
    }
    public void speedDown(){
        speed--;
    }
    public String toString(){
        return "speed:" + speed + ",size:" + size;
    }

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle(100,100);
        System.out.println(vehicle.toString());
        vehicle.speedUp();
        vehicle.speedUp();
        System.out.println(vehicle.toString());
        vehicle.speedDown();
        System.out.println(vehicle.toString());

    }
}
