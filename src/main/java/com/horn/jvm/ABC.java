package com.horn.jvm;/*
 *@Author: horn
 *@DATE: 2023/1/17 0017 15:03
 *@Description
 *@Version 1.0
 */


public class ABC {
    public static void main(String[] args) throws Exception {
        for(;;){
            new ABC().a();
        }
    }
    public void a() throws Exception{
        Thread.sleep(1000);
        b();
    }
    public void b() throws Exception{
        Thread.sleep(2000);
        c();
    }
    public void c() throws Exception{
        Thread.sleep(3000);
    }
}
