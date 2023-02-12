package com.horn.aqs;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 16:32
 *@Description
 *@Version 1.0
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CountDownLatch1 {
    private final static int threadCount = 10;

    public static void main(String[] args) throws Exception {

        // 创建线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 创建AQS同步器
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        // 开启200线程
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    System.out.println("start:"+threadNum);
                    // countDown与 await 保证之前代码与主线程同步
                    countDownLatch.countDown();
                    test(threadNum);
                    System.out.println("end"+threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
//                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(5000);
        log.info("{}", threadNum);
        Thread.sleep(100);
    }
}
