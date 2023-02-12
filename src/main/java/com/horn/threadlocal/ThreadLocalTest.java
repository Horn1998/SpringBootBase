package com.horn.threadlocal;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 19:12
 *@Description
 *@Version 1.0
 */

import sun.awt.windows.ThemeReader;

public class ThreadLocalTest {
    private ThreadLocal<String> tl = new ThreadLocal<String>();
    private String name;

    public void set(String str, String name) {
        tl.set(str);
        this.name = name;
    }

    public String get() {
        return tl.get();
    }

    public String getName() {
        return name;
    }

}
class Main{
    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalTest tlt = new ThreadLocalTest();
        ThreadLocal<String> t1 = new ThreadLocal<>();
        new Thread(new Runnable() {

            public void run() {
                tlt.set("hello", "world");
                System.out.println(tlt.get());
                System.out.println(tlt.getName());
                System.out.println(t1.get());
            }
        }).start();
//        Thread.sleep(1000);
//        new Thread(new Runnable() {
//
//            public void run() {
//                System.out.println(tlt.get());
//                System.out.println(tlt.getName());
//            }
//        }).start();

    }
}