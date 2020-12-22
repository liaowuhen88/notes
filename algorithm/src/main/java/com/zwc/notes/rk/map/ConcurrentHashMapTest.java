package com.zwc.notes.rk.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class ConcurrentHashMapTest {


    public static void main(String[] args) {

        System.out.println("--------------------");

        ConcurrentHashMap map = new ConcurrentHashMap();

        for (int i = 0; i < 30; i++) {
            map.put("key" + i, "value" + i);
        }


        System.out.println(map.get("key20"));
    }
}
