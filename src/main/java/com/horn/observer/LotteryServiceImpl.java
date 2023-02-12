package com.horn.observer;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:00
 *@Description
 *@Version 1.0
 */

import java.util.Date;

public class LotteryServiceImpl extends LotteryService{

    private MinibusTargetService miniBusTargetService = new MinibusTargetService();

    @Override
    protected LotteryResult doDraw(String uId){

        //摇号
        String lottery = miniBusTargetService.lottery(uId);
        //结果
        return new LotteryResult(uId, lottery, new Date());
    }

}
