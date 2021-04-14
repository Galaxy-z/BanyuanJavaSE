package com.banyuan.homework.collection.Ex03;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Database {
    private TreeMap<Integer, Student> students = new TreeMap<>();


    public Student select(int id) {
        return students.get(id);
    }

    public void insert(int id, Student student) {
        students.put(id, student);
    }

    public void update(int id, Student student) {
        students.put(id, student);
    }

    public void delete(int id) {
        students.remove(id);
    }

    public Set<Entry<Integer,Student>> queryAll(){
        return students.entrySet();
    }

}
