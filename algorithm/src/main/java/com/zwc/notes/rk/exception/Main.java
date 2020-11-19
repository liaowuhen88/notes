package com.zwc.notes.rk.exception;

public class Main {
    public static void main(String[] args) {
        A();
    }

    public static void A() {
        try {
            B();
        } catch (Exception e) {
            throw new RuntimeException("执行B方法报错", e);
        }
    }

    public static void B() {
        try {
            C();
        } catch (Exception e) {
            throw new RuntimeException("执行C方法报错", e);
        }
    }

    public static void C() {
        throw new RuntimeException("C");
    }
}
