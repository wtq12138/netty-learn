package com.example.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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
        EmbeddedChannel embeddedChannel=new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,0,4,0,0)
                , new LoggingHandler(LogLevel.DEBUG));
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        senMessage(buffer,"hello,world");
        senMessage(buffer,"hello");
        embeddedChannel.writeInbound(buffer);
    }

    private static void senMessage(ByteBuf buffer, String s) {
        byte[] bytes= s.getBytes();
        int len=bytes.length;
        buffer.writeInt(len);
        buffer.writeBytes(bytes);
    }
}
