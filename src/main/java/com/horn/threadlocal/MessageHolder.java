package com.horn.threadlocal;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 19:24
 *@Description
 *@Version 1.0
 */

import java.util.ArrayList;
import java.util.List;


public class MessageHolder {

    private List<String> messages = new ArrayList<>();

    public static final ThreadLocal<MessageHolder> holder = new ThreadLocal(){
        @Override
        protected Object initialValue(){
            return new MessageHolder();
        }
    };

    public static void add(String message) {
        holder.get().messages.add(message);
    }

    public static List<String> clear(){
        List<String> result = holder.get().messages;
        holder.remove();
        System.err.println("size: " + holder.get().messages.size());
        return result;
    }

    public static void main(String[] args) throws InterruptedException {

        MessageHolder.add("1111");
        MessageHolder.add("2222");
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageHolder.add("3333");
                System.out.println(MessageHolder.holder.get().messages);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageHolder.add("4444");
                System.out.println(MessageHolder.holder.get().messages);
            }
        }).start();
        Thread.sleep(1000);
        List<String> result = MessageHolder.clear();
        System.err.println(result.toString());
    }

    /**
     public synchronized allmethod () {
     return 1,2 = m1();
     return 3,4 = m2(1, 2);
     return 5,6 = m3(3, 4);

     all return 123456
     }
     */


}
