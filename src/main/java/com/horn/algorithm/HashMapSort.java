package com.horn.algorithm;/*
 *@Author: horn
 *@DATE: 2023/1/16 0016 22:48
 *@Description
 *@Version 1.0
 */

import java.util.*;

public class HashMapSort {


    public static void main(String[] args) {
        Map<Integer, Integer> mapper = new HashMap<>();
        mapper.put(1, 2);
        mapper.put(2, 3);
        mapper.put(0, 1);
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(mapper.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        for(Map.Entry<Integer, Integer> map:mapper.entrySet()){
            System.out.println(map.getKey()+","+map.getValue());
        }

    }
}
