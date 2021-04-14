package com.banyuan.exercise.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestMap {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("aaa",111);
        map.put("bbb",222);
        map.put("ccc",333);
        map.put("ddd",444);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Integer in = map.get(key);
            System.out.println(in);
        }
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

        


    }
}

