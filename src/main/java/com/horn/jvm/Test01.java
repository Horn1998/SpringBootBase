package com.horn.jvm;/*
 *@Author: horn
 *@DATE: 2023/1/14 0014 15:26
 *@Description
 *@Version 1.0
 */

import java.util.ArrayList;
import java.util.List;

public class Test01 {
    class User{
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i ){
        new User(i,"name"+i);
    }

    public static void main(String[] args) {
        Test01 t = new Test01();
        long s1 = System.currentTimeMillis();
        for (int i =0;i<10000000;i++) t.alloc(i);
        long s2 = System.currentTimeMillis();
        System.out.println(s2-s1);
    }

}



class T02 {
    public static void main(String[] args) {
        byte [] b = new byte[1024];
    }
}

class T03 {

    public static void main(String[] args) {

        printMemoryInfo();

        byte [] b = new byte[1024*1024];

        System.out.println("--------");

        printMemoryInfo();

    }

    private static void printMemoryInfo() {
        System.out.println("total:"+Runtime.getRuntime().totalMemory());
        System.out.println("free:"+Runtime.getRuntime().freeMemory());
    }

}

class T04 {

    public static void main(String[] args) {
        List<Object> lists =new ArrayList<>();

        for(int i=0;i<100000000;i++){
            lists.add(new byte[1024*1024]);
            System.out.println("total:"+Runtime.getRuntime().totalMemory());
            System.out.println("free:"+Runtime.getRuntime().freeMemory());
        }


    }
}

class T05 {

    static int count=0;

    static void r(){
        count++;

        r();
    }

    public static void main(String[] args) {
        try {
            r();
            System.out.println("total:"+Runtime.getRuntime().totalMemory());
            System.out.println("free:"+Runtime.getRuntime().freeMemory());

        }catch (Throwable r){
            System.out.println(count);
            r.printStackTrace(); //OOM  StackOverFlow
        }

    }

}