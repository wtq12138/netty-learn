package com.example.demo.conf;

import com.example.demo.conf.PoolConfProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 10:52
 */

@EnableConfigurationProperties(PoolConfProperties.class)
@Configuration
public class MyThreadPoolConf {
    @Bean
    public ThreadPoolExecutor myPool(PoolConfProperties conf) {
        return new ThreadPoolExecutor(
                conf.getCoreSize(),
                conf.getMaxSize(),
                conf.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
