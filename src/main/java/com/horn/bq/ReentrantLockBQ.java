package com.horn.bq;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 17:37
 *@Description
 *@Version 1.0
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBQ {
    private int limit = 10;
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition p1 = lock.newCondition();
    private Queue<Integer> q1 = new LinkedList<>();
    private Queue<Integer> q2 = new LinkedList<>();

    public void addTask(int i){
        lock.lock();
        try{
            if(i%2 == 0){
                q2.offer(i);
                c2.signal();
                System.out.println(String.format("双数队列 添加数字：%d, 唤醒双数", i));
            }else{
                while(q1.size() == limit){
                    p1.await();
                }
                q1.offer(i);
                c1.signal(); // 消费奇数的一个线程会被唤醒
                //c1.signalAll(); // 消费奇数的所有线程（绑定到c1上的线程）会被唤醒
                System.out.println(String.format("单数队列 添加数字：%d, 唤醒单数", i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public Integer readSignTask(){
        lock.lock();
        try{
            while(q1.isEmpty()){
                c1.await(); // 消费奇数的当前线程会被阻塞
            }
            if(q1.size() != limit){
                p1.signal();
            }
            return q1.poll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return -1;
    }
    public Integer readDobTask(){
        lock.lock();
        try{
            while(q2.isEmpty()){
                c2.await();
            }
            return q2.poll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return -1;
    }

    public static void main(String[] args) {
        ReentrantLockBQ reentrantLockBQ = new ReentrantLockBQ();
        Random random = new Random();
        Thread readSignTask = new Thread(()->{
            while (true)
            try {
                Thread.sleep(5000);
                Integer integer = reentrantLockBQ.readSignTask();
                System.out.println(String.format("读取单数：%d", integer));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        Thread readDobTask = new Thread(()->{
            while(true)
            try {
                Thread.sleep(5000);
                Integer integer = reentrantLockBQ.readDobTask();
                System.out.println(String.format("读取偶数：%d", integer));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        readSignTask.start();
        readDobTask.start();
        Scanner scanner = new Scanner(System.in);
        while(true){
            String next = scanner.next();
            int i = Integer.parseInt(next);
            reentrantLockBQ.addTask(i);

        }
    }
}
