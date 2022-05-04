package com.example.demo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 11:05
 */
@Data
@ConfigurationProperties(prefix = "conf.pool")
public class PoolConfProperties {

    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;
}
