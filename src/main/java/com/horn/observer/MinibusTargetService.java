package com.horn.observer;/*
 *@Author: horn
 *@DATE: 2023/1/13 0013 11:05
 *@Description
 *@Version 1.0
 */

public class MinibusTargetService {
    public String lottery(String uId){
        return Math.abs(uId.hashCode())%2==0? "恭喜你:".concat(uId):"再接再厉:".concat(uId);
    }
}
