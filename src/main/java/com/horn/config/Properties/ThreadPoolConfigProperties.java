package com.horn.config.Properties;/*
 *@Author: horn
 *@DATE: 2023/1/12 0012 11:27
 *@Description
 *@Version 1.0
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="gulimall.thread")
@Component
@Data
public class ThreadPoolConfigProperties {

    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;

}
