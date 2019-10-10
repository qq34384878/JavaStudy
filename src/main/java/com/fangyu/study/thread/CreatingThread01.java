package com.fangyu.study.thread;

/**
 * 继承Thread类并重写run()方法
 * @author fangyu
 * @version v1.0.0
 * @since 2019/10/7 9:35 下午
 */
public class CreatingThread01 extends Thread {

    @Override
    public void run() {
        System.out.println(getName() + " is running");
    }

    /**
     * 继承Thread类并重写run()方法，这种方式的弊端是一个类只能继承一个父类，如果这个类本身已经继承了其它类，就不能使用这种方式了。
     * @param args
     */
    public static void main(String[] args) {
        new CreatingThread01().start();
        new CreatingThread01().start();
        new CreatingThread01().start();
        new CreatingThread01().start();
    }
}
