package com.zwc.notes.rk.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwenchao19
 */
public class PigInThePython {

    static final int ENOUGH_PIGS = 600;
    static volatile List pigs = new ArrayList();
    static volatile int pigsEaten = 0;

    public static void main(String[] args) throws InterruptedException {
        new PigEater().start();
        new PigDigester().start();
    }

    static void takeANap(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class PigEater extends Thread {

        @Override
        public void run() {
            while (true) {
                pigs.add(new byte[32 * 1024 * 1024]); //32MB per pig
                if (pigsEaten > ENOUGH_PIGS) {
                    return;
                }
                takeANap(100);
            }
        }
    }

    static class PigDigester extends Thread {
        @Override
        public void run() {
            long start = System.currentTimeMillis();

            while (true) {
                takeANap(2000);
                pigsEaten += pigs.size();
                pigs = new ArrayList();
                if (pigsEaten > ENOUGH_PIGS) {
                    System.out.format("Digested %d pigs in %d ms.%n", pigsEaten, System.currentTimeMillis() - start);
                    return;
                }
            }
        }
    }
}
