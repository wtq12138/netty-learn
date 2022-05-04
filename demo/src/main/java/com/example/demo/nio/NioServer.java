package com.example.demo.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-02 10:03
 */
@Slf4j
//@Component
public class NioServer {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    @SneakyThrows
    public NioServer() {
        //获取channel
        serverSocketChannel=ServerSocketChannel.open();
        //获取selector
        selector=Selector.open();
        //绑定socket
        serverSocketChannel.bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);
        //注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
    @SneakyThrows
    public void listen() {
        while(true) {
            if(selector.select(1000)==0) {
                System.out.println("no connect");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            for (; iterator.hasNext(); ) {
                SelectionKey next =  iterator.next();
                if(next.isAcceptable()) {
                    System.out.println("connect");
                    SocketChannel socketChannel=serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(next.isReadable()) {
                    SocketChannel channel = null;
                    try {
                        channel = (SocketChannel)next.channel();
                        channel.configureBlocking(false);
                        ByteBuffer buffer=(ByteBuffer) next.attachment();
                        channel.read(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                    }catch (IOException e) {
                        try {
                            log.debug(channel.getRemoteAddress()+"下线");
                            channel.close();
                            next.cancel();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                iterator.remove();
            }
        }
    }
}
