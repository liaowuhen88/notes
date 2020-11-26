package com.zwc.notes.rk.thread;

/**
 *
 */
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        System.out.println("**");
        t.interrupt(); // 中断t线程
        //1.当t中断之后，第27行hello.join，因为是join到线程t。所以会促发中断异常。
        //2.31行hello线程中断之后，第四十六行hello线程会触发中断异常。
        System.out.println("***");
        t.join(); // 等待t线程结束
        System.out.println("end");
    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        try {
            hello.join(); // 等待hello线程结束
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 表示当前线程被中断，此时中断HelloThread线程
            hello.interrupt();
        }

    }
}

class HelloThread extends Thread {
    @Override
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(Thread.currentThread().getName() + ":" + n + " hello!");
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}




