package com.horn.aqs;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 16:38
 *@Description
 *@Version 1.0
 */

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CyclicBarrier1 {

    // 指定等待n个线程执行
    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 开辟10条线程
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(()->{
                try {
                    race(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("before");
        executor.shutdown();
//        System.out.println("finish");
    }

    private static void race(int threadNum) throws Exception {
        log.info("{} is ready", threadNum);
        Thread.sleep(new Random().nextInt(10)*2000);
        barrier.await();
        log.info("{} continue", threadNum);
    }
}
