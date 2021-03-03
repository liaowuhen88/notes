package com.zwc.notes.rk.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Test {
    public static void main(String[] args) {
        ChinesePoxy chineseProxy = new ChinesePoxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Chinese.class);
        enhancer.setCallback(chineseProxy);

        Chinese proxy = (Chinese) enhancer.create();
        proxy.sayHello();
    }
}
