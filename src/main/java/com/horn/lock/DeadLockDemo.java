package com.horn.lock;/*
 *@Author: horn
 *@DATE: 2023/1/16 0016 23:05
 *@Description
 *@Version 1.0
 */

import java.util.TreeMap;

public class DeadLockDemo {
    private static Object resource1 = new Object();
    private static Object resourse2 = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (resource1){
                System.out.println(Thread.currentThread()+"get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resourse2){
                    System.out.println(Thread.currentThread()+"get resource2");
                }
            }
        }, "线程1").start();
        new Thread(()->{
            synchronized (resourse2){
                System.out.println(Thread.currentThread()+"get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource1){
                    System.out.println(Thread.currentThread()+"get resource1");
                }
            }
        }, "线程2").start();
    }
}
