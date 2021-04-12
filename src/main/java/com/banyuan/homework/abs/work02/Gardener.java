package com.banyuan.homework.abs.work02;

import java.util.Scanner;

public class Gardener {
    public Fruit create(String kind) {
        switch (kind) {
            case "苹果":
                return new Apple();
            case "梨子":
                return new Pear();
            case "橙子":
                return new Oranges();
            default:
                return null;
        }
    }

    public static void main(String[] args) {

        Gardener gardener = new Gardener();
        Scanner scanner = new Scanner(System.in);
        Fruit fruit = gardener.create(scanner.next());

    }
}

