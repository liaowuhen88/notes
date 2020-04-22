package com.zwc.notes.rk.dynamic;

import com.alibaba.fastjson.JSON;

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
        int w = 18;
        int[] g = new int[]{0, 400, 500, 200, 300, 350};
        int[] p = new int[]{0, 5, 5, 3, 4, 3};

        int count = recursion(n, w, g, p);
        System.out.println("recursion:" + count);
        HashMap map = new HashMap();
        int count2 = memorandum(n, w, g, p, map);
        System.out.println("memorandum,map.size:" + map.size());
        System.out.println("memorandum:" + count2);

        int count3 = dynamic(n, w, g, p);
        System.out.println("dynamic:" + count3);


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
        if (w <= 0) {
            return 0;
        }

        if (n == 1) {
            if (w < p[1]) {
                return 0;
            } else {
                return g[1];
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

        // 工人总数少于当前金矿所需工人，所以无法开采
        if (w < p[n]) {
            return recursion(n - 1, w, g, p);
        } else {
            int a = recursion(n - 1, w, g, p);
            int b = recursion(n - 1, w - p[n], g, p) + g[n];
            int num = a > b ? a : b;
            //System.out.println("n:"+n+"----w:" + w+"----num:" + num);
            return num;
        }
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
        if (w <= 0) {
            return 0;
        }
        if (n == 1) {
            if (w < p[0]) {
                return 0;
            } else {
                return g[0];
            }
        }

        if (w < p[n - 1]) {
            String key = n - 1 + "_" + w;
            if (map.containsKey(key)) {
                return map.get(key);
            } else {
                int num = memorandum(n - 1, w, g, p, map);
                //System.out.println(keyA);
                map.put(key, num);
                return num;
            }
        } else {
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
                map.put(keyB, b);
                //System.out.println(keyB);
            }

            int num = a > b ? a : b;
            //System.out.println("n:"+n+"----w:" + w+"----num:" + num);
            return num;
        }
    }

    public static int dynamic(int n, int w, int[] g, int[] p) {
        int[] preResults = new int[w + 1];
        int[] results = new int[w + 1];

        /**
         *   F(n,w) = 0    (n==1, w<p[0]);
         *
         *   F(n,w) = g[0]     (n==1, w>=p[0]);
         *
         *  F(n,w) = F(n-1,w)    (n>1, w<p[n-1])
         *
         *  F(n,w) = max(F(n-1,w),  F(n-1,w-p[n-1])+g[n-1])    (n>1, w>=p[n-1])
         */
        // 初始化边界条件，n=1的情况
        // n==1
        for (int j = 1; j <= w; j++) {
            // 当前工人数小于金矿所需功能
            if (j < p[1]) {
                preResults[j] = 0;
            } else {
                preResults[j] = g[1];
            }
        }

        System.out.println("init preResults:" + JSON.toJSONString(preResults));
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                // 当前工人数小于金矿所需功能
                // i==5,p[5]=3,g[5]=350,j=6
                if (j < p[i]) {
                    results[j] = preResults[j];
                } else {
                    int num = j - p[i];
                    results[j] = Math.max(preResults[j], preResults[num] + g[i]);
                }
            }
            System.arraycopy(results, 0, preResults, 0, results.length);
            System.out.println("preResults:" + JSON.toJSONString(preResults));
        }

        return results[w];
    }

}
