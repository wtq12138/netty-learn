package com.example.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-05 09:36
 */
@Slf4j
public class EchoClient {

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture future = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf= (ByteBuf) msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                }).connect("localhost", 8888);
        future.addListener((ChannelFutureListener) future1 -> {
            Channel channel = future1.channel();
            channel.closeFuture().addListener((ChannelFutureListener) future2 -> group.shutdownGracefully());
            Thread thread = new Thread(() -> {
                while(true) {
                    Scanner scanner = new Scanner(System.in);
                    String s=scanner.nextLine();
                    if(s.equals("q")) {
                        channel.close();
                        break;
                    }else {
                        channel.writeAndFlush(s);
                    }
                }
            });
            thread.start();
        });
    }

}
