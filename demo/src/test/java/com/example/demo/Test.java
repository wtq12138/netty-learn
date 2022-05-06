package com.example.demo;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-03 15:46
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Test {
    public static void main(String[] args) throws IOException {
        ByteBuf byteBuf= ByteBufAllocator.DEFAULT.buffer();
        System.out.println(byteBuf);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<300;i++)
            sb.append('a');
        byteBuf.writeBytes(sb.toString().getBytes());
        System.out.println(byteBuf);
    }
}
