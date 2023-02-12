package com.horn.observer;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 10:58
 *@Description
 *@Version 1.0
 */

import com.horn.observer.Event.EventManager;
import com.horn.observer.Event.listener.MQEventListener;
import com.horn.observer.Event.listener.MessageEventListener;

public abstract class LotteryService {
    private EventManager eventManager;

    public LotteryService(){
        eventManager = new EventManager(EventManager.EventType.MQ, EventManager.EventType.Message);
        eventManager.subscribe(EventManager.EventType.MQ, new MQEventListener());
        eventManager.subscribe(EventManager.EventType.Message, new MessageEventListener());
    }
    public LotteryResult draw(String uId){
        LotteryResult lotteryResult = doDraw(uId);
        eventManager.notify(EventManager.EventType.MQ, lotteryResult);
        eventManager.notify(EventManager.EventType.Message, lotteryResult);
        return lotteryResult;
    }

    protected abstract LotteryResult doDraw(String uId);
}
