package com.fangyu.study.thread;

/**
 * 匿名内部类
 * @author fangyu
 * @version v1.0.0
 * @since 2019/10/11 1:05 上午
 */
public class CreatingThread03 {
    /**
     * 使用匿名类的方式，一是重写Thread的run()方法，二是传入Runnable的匿名类，三是使用lambda方式，现在一般使用第三种（java8+），简单快捷。
     * @param args
     */
    public static void main(String[] args) {
        // Thread匿名类，重写Thread的run()方法
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " is running");
            }
        }.start();

        // Runnable匿名类，实现其run()方法
        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }).start();

        // 同上，使用lambda表达式函数式编程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is running");
        }).start();

    }
}
