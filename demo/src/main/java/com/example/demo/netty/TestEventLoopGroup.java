package com.example.demo.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-04 09:32
 */
@Slf4j
public class TestEventLoopGroup {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup(2);
        System.out.println(eventLoopGroup.next());
        System.out.println(eventLoopGroup.next());
        System.out.println(eventLoopGroup.next());
        System.out.println(eventLoopGroup.next());
    }
}
