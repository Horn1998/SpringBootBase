package com.horn.observer.Event;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:16
 *@Description
 *@Version 1.0
 */

import com.horn.observer.Event.listener.EventListener;
import com.horn.observer.LotteryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<Enum<EventType>, List<EventListener>> listeners = new HashMap<>();

    public EventManager(Enum<EventType>... options){
        for(Enum<EventType> option:options){
            this.listeners.put(option, new ArrayList<>());
        }
    }
    public enum EventType{
        MQ, Message
    }
    // 订阅
    public void subscribe(Enum<EventType> eventType, EventListener listener){
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }
    // 取消订阅
    public void unsubscribe(Enum<EventType> eventType, EventListener listener){
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }
    // 通知
    public void notify(Enum<EventType> eventType, LotteryResult lotteryResult){
        List<EventListener> users = listeners.get(eventType);
        for(EventListener listener:users){
            listener.doEvent(lotteryResult);
        }
    }
}
