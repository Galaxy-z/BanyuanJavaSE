package com.banyuan.exercise.io.exer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HeroList {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("hero.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("heros2.txt"))
        ) {
            List<Hero> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) continue;
                String[] params = line.split(",");
                String name = params[0];
                int age = Integer.parseInt(params[1]);
                char gender = params[2].charAt(0);
                String city = params[3];
                list.add(new Hero(name, age, gender, city));
            }

            list.sort((e1, e2) -> e2.getAge() - e1.getAge());
            for (Hero hero : list) {
                bw.write(hero.getName() + "," + hero.getAge() + "," + hero.getGender() + "," + hero.getCity());
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Hero {
    private String name;
    private int age;
    private char gender;
    private String city;

    public Hero(String name, int age, char gender, String city) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
