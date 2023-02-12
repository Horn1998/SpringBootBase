package com.horn.observer.Event.listener;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:08
 *@Description
 *@Version 1.0
 */

import com.horn.observer.LotteryResult;

public interface EventListener {
    void doEvent(LotteryResult lotteryResult);
}
