package concurrent;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 17:52
 *@Description
 *@Version 1.0
 */

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class CopyOnWriteArrayListExample {
    /**
     * 请求总数
     */
    private static int clientTotal = 5000;
    /**
     * 同时并发执行的线程数
     */
    private static int threadTotal = 200;

    private static List<Integer> list = new CopyOnWriteArrayList<>();
    private static List<Integer> list2 = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            service.execute(()->{
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        service.shutdown();
        log.info("size:{}{}", list.size(), list2.size());
    }

    private static void update(){
        /**
         * 1.先获得锁lock
         * 2.获取原数组
         * 3.copy赋值原数组为一个新的数组并且数组长度+1
         * 4.将新的元素赋值到新的数组中
         * 5.新数组替换原数组
         * 6.释放锁unlock
         */
        list.add(1); list2.add(1);
    }
}

