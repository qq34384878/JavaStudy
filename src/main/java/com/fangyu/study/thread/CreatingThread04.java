package com.fangyu.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callabe接口
 * @author fangyu
 * @version v1.0.0
 * @since 2019/10/11 1:11 上午
 */
public class CreatingThread04 implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getId() + " is running");
        return Thread.currentThread().getId();
    }

    /**
     * 实现Callabe接口，可以获取线程执行的结果，FutureTask实际上实现了Runnable接口。
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Long> task = new FutureTask<>(new CreatingThread04());
        new Thread(task).start();
        System.out.println("等待完成任务");
        Long result = task.get();
        System.out.println("任务结果: " + result);
    }
}
