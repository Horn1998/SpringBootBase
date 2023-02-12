package com.horn.observer;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:03
 *@Description
 *@Version 1.0
 */

import lombok.Data;

import java.util.Date;

@Data
public class LotteryResult {
    String id;
    String message;
    String date;
    public LotteryResult(String uId, String lottery, Date _date){
        id = uId; message = lottery; date = _date.toString();
    }
}
