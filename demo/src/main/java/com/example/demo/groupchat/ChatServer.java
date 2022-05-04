package com.example.demo.groupchat;

import lombok.SneakyThrows;
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
 * @create: 2022-05-02 15:48
 */
public class ChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;

    @SneakyThrows
    public ChatServer() {
        selector=Selector.open();
        listenChannel=ServerSocketChannel.open();
        listenChannel.bind(new InetSocketAddress(8889));
        listenChannel.configureBlocking(false);
        listenChannel.register(selector, SelectionKey.OP_ACCEPT,ByteBuffer.allocate(1024));
    }

    @SneakyThrows
    public void listen() {
        while(true) {
            if(selector.select(1000)==0) {
                System.out.println("no user connect");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
                SelectionKey key =  iterator.next();
                if(key.isAcceptable()) {
                    SocketChannel accept = listenChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    System.out.println(accept.getRemoteAddress()+"  up");
                }else if(key.isReadable()) {
                    read(key);
                }else if(key.isWritable()) {

                }
                iterator.remove();
            }
        }
    }
    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        int read = 0;
        try {
            read = channel.read(buffer);
            if(read>0) {
                String msg=new String(buffer.array());
                buffer.flip();
                System.out.println("from 客户端:"+msg);
                dispatcher(msg,channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getLocalAddress()+"离线");
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    @SneakyThrows
    private void dispatcher(String message,SocketChannel self) {
        Set<SelectionKey> keys = selector.keys();
        for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext(); ) {
            SelectionKey key = iterator.next();
            Channel channel = key.channel();
            if( channel instanceof SocketChannel&&channel!=self) {
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                ((SocketChannel) channel).write(buffer);
            }
        }

    }
}
