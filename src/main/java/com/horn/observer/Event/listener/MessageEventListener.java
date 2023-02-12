package com.horn.observer.Event.listener;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:09
 *@Description
 *@Version 1.0
 */


import com.horn.observer.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEventListener implements EventListener {

    private Logger logger = LoggerFactory.getLogger(MessageEventListener.class);
    @Override
    public void doEvent(LotteryResult lotteryResult) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("发送通知Message"+lotteryResult.getId()+lotteryResult.getMessage());
    }
}
