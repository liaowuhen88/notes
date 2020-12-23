package com.zwc.notes.rk.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class ConcurrentHashMapTest {


    public static void main(String[] args) {

        test2();
    }

    private static void test2() {
        System.out.println("--------------------");

        HashMap map = new HashMap();

        for (int i = 0; i < 30; i++) {
            map.put("key" + i, "value" + i);
        }


        System.out.println(map.get("key20"));
    }

    private void test1() {
        System.out.println("--------------------");

        ConcurrentHashMap map = new ConcurrentHashMap();

        for (int i = 0; i < 30; i++) {
            map.put("key" + i, "value" + i);
        }


        System.out.println(map.get("key20"));
    }
}
