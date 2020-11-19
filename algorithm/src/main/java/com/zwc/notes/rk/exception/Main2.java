package com.zwc.notes.rk.exception;

public class Main2 {
    public static void main(String[] args) {
        A();
    }

    public static void A() {
        B();
    }

    public static void B() {
        C();
    }

    public static void C() {
        throw new RuntimeException("C");
    }
}
