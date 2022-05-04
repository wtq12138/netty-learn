package com.example.demo.nio;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 10:47
 */

@Component
public class NioClient {
    @SneakyThrows
    public void connnect() {
        SocketChannel open = SocketChannel.open();
        open.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        if(!open.connect(inetSocketAddress)) {
                while(!open.finishConnect()) {
                    System.out.println("no blocking do");
                }
        }
        System.out.println("client connnect");
        String str="hello server";
        ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        open.write(wrap);
    }
}
