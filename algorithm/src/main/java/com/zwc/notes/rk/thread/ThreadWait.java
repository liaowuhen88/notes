package com.zwc.notes.rk.thread;

public class ThreadWait {

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
    }

    static class ThreadA extends Thread {
        int i;

        public ThreadA(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(i);
            }
        }
    }
}
