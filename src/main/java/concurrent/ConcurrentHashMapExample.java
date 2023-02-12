package concurrent;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 17:45
 *@Description
 *@Version 1.0
 */

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class ConcurrentHashMapExample {
    /**
     * 请求总数
     */
    private static int clientTotal = 600;
    /**
     * 同时并发执行的线程数
     */
    private static int threadTotal = 200;

    private static Map<Integer,Integer> cmap = new ConcurrentHashMap<>();
    private static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < clientTotal; i++) {
            int finalI = i;
            service.execute(()->{
                try {
                    semaphore.acquire();
                    update(finalI);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis());
        service.shutdown();
        log.info("size:{},{}", map.size(), cmap.size());
    }

    private static void update(int i) throws InterruptedException {
        Thread.sleep(2000);
        map.put(i,i); cmap.put(i,i);
    }
}
