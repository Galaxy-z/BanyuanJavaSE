package com.banyuan.homework.collection.Ex03;

import java.util.*;
import java.util.Map.Entry;

public class Test {
    Scanner scanner = new Scanner(System.in);
    Database database = new Database();

    public void serviceView() {
        System.out.println("1.向表中插入值");
        System.out.println("2.查询表中的值");
        System.out.println("3.修改表中的值");
        System.out.println("4.删除表中的值");
        System.out.println("其他退出");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                insertView();
            case 2:
                querySelectView();
            case 3:
                changeView();
            case 4:
                deleteView();
            default:
                System.exit(0);
        }

    }

    public void insertView() {
        System.out.println("请输入学生的ID");
        int id = scanner.nextInt();
        System.out.println("请输入学生的姓名");
        String name = scanner.next();
        System.out.println("请输入学生的年龄");
        int age = scanner.nextInt();
        database.insert(id, new Student(name, age));
        serviceView();
    }

    public void querySelectView() {
        System.out.println("请选择查询的方式：1 查询单行 2 查询全表");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                querySingleView();
            case 2:
                queryAllView();
        }
    }

    public void querySingleView() {
        System.out.println("请输入学生的ID");
        int id = scanner.nextInt();
        Student student = database.select(id);
        HashMap<Integer, Student> students = new HashMap<>();
        students.put(id, student);
        Set<Entry<Integer, Student>> entries = students.entrySet();
        showEntries(entries);

    }

    public void queryAllView() {
        Set<Entry<Integer, Student>> entries = database.queryAll();
        showEntries(entries);
    }

    public void showEntries(Set<Entry<Integer, Student>> entries) {
        System.out.println("  学号\t\t\t姓名\t\t\t年龄");
        System.out.println("=====================================");
        for (Entry<Integer, Student> o : entries) {
            int id = o.getKey();
            String name = o.getValue().getName();
            int age = o.getValue().getAge();
            System.out.println(id + "\t\t\t" + name + "\t\t\t" + age);
        }
        serviceView();

    }

    public void changeView() {
        System.out.println("请输入学生的ID");
        int id = scanner.nextInt();
        System.out.println("请输入学生的姓名");
        String name = scanner.next();
        System.out.println("请输入学生的年龄");
        int age = scanner.nextInt();
        database.update(id, new Student(name, age));
        serviceView();
    }

    public void deleteView() {
        System.out.println("请输入学生的ID");
        int id = scanner.nextInt();
        database.delete(id);
        serviceView();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.serviceView();
    }
}
