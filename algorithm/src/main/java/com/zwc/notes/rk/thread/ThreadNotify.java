package com.zwc.notes.rk.thread;

import lombok.SneakyThrows;

public class ThreadNotify {

    private static Object lock = new Object();

    private static Thread[] threads = new Thread[10];

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            threads[i] = new ThreadA(i);
        }

        synchronized (lock) {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + "start");
                threads[i].start();
                Thread.sleep(20);
            }
        }

        Thread.sleep(2000);
        synchronized (lock) {
            System.out.println("notifyAll");
            lock.notifyAll();

        }

        Thread.sleep(2000);

        System.out.println("end");
    }

    static class ThreadA extends Thread {
        int i;

        public ThreadA(int i) {
            this.i = i;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    System.out.println(i);
                    lock.wait();
                    System.out.println("notifyAll_" + i);
                    break;
                }
            }
        }
    }
}
