package com.horn.bq;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 16:28
 *@Description
 *@Version 1.0
 */

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueueDemo {
    // 队列长度默认为10
    private int limit = 10;
    private Queue queue = new LinkedList<>();

    //初始化队列容量
    public BlockingQueueDemo(int _limit){
        this.limit = _limit;
    }

    // 入队列
    public synchronized boolean push(Object object) throws InterruptedException{
        // 如果队列已满，再来添加队列的线程会进入等待
        while(this.queue.size() == limit){
            wait();
            // 为什么用while而不是if?
            /**
             * 因为在多对多场景下，该线程被唤醒不一定能抢到锁
             * 当自己抢到锁后，队列未满的条件不一定满足，可能别的线程把队列填满了
             * 如果是if，就会在抢到锁后直接执行后面的代码，不会再次判断
             * */
        }
        // 如果队列为空了，就唤醒消费者（不会立刻获得锁），然后生产产品
        if(this.queue.size() == 0){
            // 为什么是notifyAll而不是notify
            /**
             * 如果只有一个消费者和一个生产者，消费者唤醒的一定是生产者，生产者唤醒的一定是消费者
             * 但多对多的场景下，可能出现消费者唤醒消费者的情况（因为notify是随机的）
             * 最坏的情况，消费者唤醒的都是消费者，出现死锁情况*/
            this.notifyAll();
        }
        // 入队
        boolean add = this.queue.offer(object);
        return add;
    }

    // 出队列
    public synchronized Object pop() throws InterruptedException{

        if(this.queue.size() == 0){
            this.wait();
        }

        if(queue.size() == limit){
            this.notifyAll();
        }

        return queue.poll();
    }
}

class Food{

}
class demo{
    int limit = 10;
    Queue<Food> q = new LinkedList<>();
    public synchronized boolean push() throws InterruptedException {
        while (limit == q.size()){
            this.wait();
        }
        if(limit == 0){
            this.notifyAll();
        }
        boolean finished = q.offer(new Food());
        return finished;
    }

    public synchronized Food add() throws InterruptedException {
        while(limit == 0){
            this.wait();
        }
        if (limit == q.size()) {
            this.notifyAll();
        }
        return q.poll();
    }
}
