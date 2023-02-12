package com.horn.observer.Event.listener;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:13
 *@Description
 *@Version 1.0
 */

import com.horn.observer.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQEventListener implements EventListener {
    private Logger logger = LoggerFactory.getLogger(MQEventListener.class);
    @Override
    public void doEvent(LotteryResult lotteryResult) {
        logger.info("发送通知MQ"+lotteryResult.getId()+lotteryResult.getMessage());
    }
}
