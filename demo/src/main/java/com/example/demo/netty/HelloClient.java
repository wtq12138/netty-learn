package com.example.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-03 17:25
 */
@Slf4j
public class HelloClient {
    @SneakyThrows
    public static void main(String[] args) {
        ChannelFuture future = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8888));
//                future.sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                log.debug("{}",channel+"test");
                channel.writeAndFlush("hello2");
            }
        });
        Channel channel = future.channel();
        channel.writeAndFlush("hello2");
        System.out.println('1');
//        channel.writeAndFlush("hello3");
//        channel.writeAndFlush("hello4");
    }
}
