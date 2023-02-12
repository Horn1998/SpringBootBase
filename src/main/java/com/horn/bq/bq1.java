package com.horn.bq;/*
 *@Author: horn
 *@DATE: 2023/1/16 0016 22:31
 *@Description
 *@Version 1.0
 */

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class bq1 {
    private int limit = 3;
    private Queue<Integer> q = new LinkedList<>();
    public synchronized boolean push(int i) throws InterruptedException {
        while(q.size() == limit){
            this.wait();
        }
        if(q.size() == 0){
            this.notifyAll();
        }
        return q.offer(i);
    }
    public synchronized int get() throws InterruptedException {
        while (q.size() == 0){
            this.wait();
        }
        if(q.size() == limit){
            this.notifyAll();
        }
        return q.poll();
    }

    public int getSize(){
        return q.size();
    }
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        bq1 bq = new bq1();

        while (sc.hasNextInt()){
            int now = sc.nextInt();
            sc.nextLine();
            if(now == -1) break;
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    bq.push(now);
                    System.out.println(bq.getSize());
                }
            }).start();
        }

        while (sc.hasNextInt()){
            int now = sc.nextInt();
            sc.nextLine();
            if(now == -1) break;
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    bq.get();
                    System.out.println(bq.getSize());
                }
            }).start();
        }
    }

}
