package com.banyuan.exercise.collection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

/**
 * 1.随机生成20条狗
 * 颜色:白色、黄色、黑色、黄白、黑白
 * 体重:[1-50]斤
 * 2.将这些狗依次放入ArrayList中
 * 3.将该list倒序
 * 4.计算list中这些狗的平均体重
 * 5.将体重大于平均值的并且颜色为白色的狗从list中给删除
 * ----------------------------------
 * 6.判断该list是否包含体重为20，颜色是白色的狗狗
 * 7.如果存在，则打印该狗狗的下标
 * 8.找到体重最小和最大的狗狗，交换他们的位置
 * 9.对该list按照狗狗的体重降序
 */
public class Dog implements Comparable<Dog> {
    private String color;
    private int weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return weight == dog.weight && Objects.equals(color, dog.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, weight);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Dog(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    @Override
    public int compareTo(@NotNull Dog o) {
        return this.weight - o.weight;
    }

    public static void main(String[] args) {
        String[] colors = {"白色", "黄色", "黑色", "黄白", "黑白"};
        ArrayList<Dog> dogs = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int colorChoice = (int) (Math.random() * 5);
            int weight = (int) (Math.random() * 50 + 1);
            dogs.add(new Dog(colors[colorChoice], weight));
        }

        Collections.reverse(dogs);

        double sum = 0;
        for (Dog dog : dogs) {
            sum += dog.getWeight();
        }
        double average = sum / dogs.size();
        System.out.println("平均体重为:" + average);

//        dogs.removeIf(dog -> dog.getWeight() > average && dog.getColor().equals("白色"));
        Iterator<Dog> iterator = dogs.iterator();
        while (iterator.hasNext()) {
            Dog dog = iterator.next();
            if (dog.getWeight() > average && dog.getColor().equals("白色")) {
                iterator.remove();
            }
        }

        Boolean exist = dogs.contains(new Dog("白色", 20));
        System.out.println(exist);

        if (exist) {
            System.out.println(dogs.indexOf(new Dog("白色", 20)));
        }

        Dog minDog = Collections.min(dogs);
        int minIndex = dogs.indexOf(minDog);
        Dog maxDog = Collections.max(dogs);
        int maxIndex = dogs.indexOf(maxDog);
        dogs.set(minIndex, maxDog);
        dogs.set(maxIndex, minDog);

        Collections.sort(dogs);

        System.out.println(dogs);
    }
}
