package com.banyuan.exercise.io;

import java.io.*;

public class ObjectSerialize {
    public static void main(String[] args) {
        Human human = new Human("aaa",21,'男');
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Human.txt"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Human.txt"))){
            oos.writeObject(human);
            Human h = (Human) ois.readObject();
            System.out.println(h);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
