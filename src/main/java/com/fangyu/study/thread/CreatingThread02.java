package com.fangyu.study.thread;

/**
 * 实现Runnable接口
 * @author fangyu
 * @version v1.0.0
 * @since 2019/10/11 12:23 上午
 */
public class CreatingThread02 implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }

    /**
     * 实现Runnable接口，这种方式的好处是一个类可以实现多个接口，不影响其继承体系。
     * @param args
     */
    public static void main(String[] args) {
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
    }
}
