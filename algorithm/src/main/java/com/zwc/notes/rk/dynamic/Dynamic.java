package com.zwc.notes.rk.dynamic;

import java.util.HashMap;

/**
 * 大事化小，小事化了
 * <p>
 * 题目：
 * 有一座高度是10级台阶的楼梯，从下往上走，每跨一步只能向上1级或者2级台阶。要求用程序来求出一共有多少种走法。
 */

public class Dynamic {
    public static void main(String[] args) {
        int count = recursion(10);
        System.out.println("recursion:" + count);
        HashMap map = new HashMap();
        int count2 = memorandum(10, map);
        System.out.println("memorandum,map.size:" + map.size());
        System.out.println("memorandum:" + count2);
        int count3 = dynamic(10);
        System.out.println("dynamic:" + count3);


    }

    /**
     * 递归求解
     *
     * @param n
     * @return
     */
    public static int recursion(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return recursion(n - 1) + recursion(n - 2);
    }

    public static int memorandum(int n, HashMap<Integer, Integer> map) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (map.containsKey(n)) {
            return map.get(n);
        } else {
            int value = memorandum(n - 1, map) + memorandum(n - 2, map);
            map.put(n, value);
            return value;
        }
    }

    public static int dynamic(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int a = 1;
        int b = 2;
        int tmmp = 0;
        for (int i = 3; i <= n; i++) {
            tmmp = a + b;
            a = b;
            b = tmmp;

        }

        return tmmp;
    }

}
