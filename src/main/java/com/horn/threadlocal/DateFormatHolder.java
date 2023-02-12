package com.horn.threadlocal;/*
 *@Author: horn
 *@DATE: 2023/1/15 0015 18:51
 *@Description
 *@Version 1.0
 */

import java.text.SimpleDateFormat;

public class DateFormatHolder {
    private final static ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>();

    public static void add(SimpleDateFormat format){DATE_FORMAT_THREAD_LOCAL.set(format);}

    public static SimpleDateFormat get(){return DATE_FORMAT_THREAD_LOCAL.get();}

    public static void remove(){DATE_FORMAT_THREAD_LOCAL.remove();}
}
