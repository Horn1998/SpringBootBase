package com.horn.atomic;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 17:05
 *@Description
 *@Version 1.0
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class AtomicExample1 {

    /**
     * 请求总数
     */
    private static int clientTotal = 5000;
    /**
     * 同时并发执行的线程数
     */
    private static int threadTotal = 200;
    private static AtomicInteger count3 = new AtomicInteger(0);
    private static AtomicReference<Integer> count2 = new AtomicReference<Integer>(0);

    private static LongAdder count = new LongAdder();
    public static void main(String[] args) throws InterruptedException {
        count2.compareAndSet(0,2);
        count2.compareAndSet(0,1);
        count2.compareAndSet(1,3);
        count2.compareAndSet(2,4);
        count2.compareAndSet(3,5);
        log.info("count:{}", count2);

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
    }

    private static void add(){
        // count.getAndIncrement(); // 先获取再累加
//        count.incrementAndGet(); // 先累加在获取
        count.increment();
    }
}
