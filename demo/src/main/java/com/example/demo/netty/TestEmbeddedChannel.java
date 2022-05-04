package com.example.demo.netty;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-04 16:19
 */
@Slf4j
public class TestEmbeddedChannel {
    public static void main(String[] args) {
        ChannelInboundHandlerAdapter c1=new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("1111");
                super.channelRead(ctx, msg);
            }
        };
        ChannelInboundHandlerAdapter c2=new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("2222");
                super.channelRead(ctx, msg);
            }
        };
        ChannelOutboundHandlerAdapter c3=new ChannelOutboundHandlerAdapter() {

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.debug("3333");
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter c4=new ChannelOutboundHandlerAdapter() {

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.debug("4444");
                super.write(ctx, msg, promise);
            }
        };
        EmbeddedChannel embeddedChannel=new EmbeddedChannel(c1,c2,c3,c4);
        embeddedChannel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hh".getBytes()));
        embeddedChannel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hh".getBytes()));
    }
}
