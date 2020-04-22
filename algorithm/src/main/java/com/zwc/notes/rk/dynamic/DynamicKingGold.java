package com.zwc.notes.rk.dynamic;

import java.util.HashMap;

/**
 * 大事化小，小事化了
 * <p>
 * 题目：
 * 有一个国家发现了5座金矿，每座金矿的黄金储量不同，需要参与挖掘的工人数也不同。
 * <p>
 * 参与挖矿工人的总数是10人。每座金矿要么全挖，要么不挖，不能派出一半人挖取一半金矿。要求用程序求解出，要想得到尽可能多的黄金，应该选择挖取哪几座金矿？
 */

public class DynamicKingGold {
    public static void main(String[] args) {
        int n = 5;
        int w = 10;
        int[] g = new int[]{400, 500, 200, 300, 350};
        int[] p = new int[]{5, 5, 3, 4, 3};

        int count = recursion(n, w, g, p);
        System.out.println("recursion:" + count);
        HashMap map = new HashMap();
        int count2 = memorandum(n, w, g, p, map);
        System.out.println("memorandum,map.size:" + map.size());
        System.out.println("memorandum:" + count2);

         /*int count3 = dynamic(10);
        System.out.println("dynamic:" + count3);*/


    }

    /**
     * 递归求解
     *
     * @param n
     * @return
     */
    public static int recursion(int n, int w, int[] g, int[] p) {

        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            if (w < p[0]) {
                return 0;
            } else {
                return g[0];
            }
        }

        /**
         *  F(n,w) = 0    (n==1, w<p[0]);
         *
         *  F(n,w) = g[0]     (n==1, w>=p[0]);
         *
         *  F(n,w) = F(n-1,w)    (n>1, w<p[n-1])
         *
         *  F(n,w) = max(F(n-1,w),  F(n-1,w-p[n-1])+g[n-1])    (n>1, w>=p[n-1])
         */
        int a = recursion(n - 1, w, g, p);
        int b = recursion(n - 1, w - p[n - 1], g, p) + g[n - 1];

        return a > b ? a : b;
    }

    /**
     * 备忘录算法
     *
     * @param n
     * @param w
     * @param g
     * @param p
     * @param map
     * @return
     */
    public static int memorandum(int n, int w, int[] g, int[] p, HashMap<String, Integer> map) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            if (w < p[0]) {
                return 0;
            } else {
                return g[0];
            }
        }

        String keyA = n - 1 + "_" + w;
        String keyB = n - 1 + "_" + (w - p[n - 1]);
        int a;
        int b;
        if (map.containsKey(keyA)) {
            a = map.get(keyA);
        } else {
            a = memorandum(n - 1, w, g, p, map);
            //System.out.println(keyA);
            map.put(keyA, a);
        }

        if (map.containsKey(keyB)) {
            b = map.get(keyB);
        } else {
            b = memorandum(n - 1, w - p[n - 1], g, p, map) + g[n - 1];
            map.put(keyB, a);
            //System.out.println(keyB);
        }

        return a > b ? a : b;
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
