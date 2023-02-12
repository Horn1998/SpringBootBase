package com.horn.lock;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 18:19
 *@Description
 *@Version 1.0
 */

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;

@Slf4j
public class LockExample {

    /**
     * 请求总数
     */
    private static int clientTotal = 5000;
    /**
     * 同时并发执行的线程数
     */
    private static int threadTotal = 200;
    private static int count = 0;
    private final static StampedLock lock = new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            service.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        service.shutdown();
        log.info("count:{}", count);
        long end = System.currentTimeMillis();
        log.info("耗时：{}", end - start);
    }

    private static void add(){
        long stamp = lock.writeLock();
        try {
            count++;
        }finally {
            lock.unlock(stamp);
        }
    }
}
