package com.example.demo.groupchat;

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
 * @create: 2022-05-02 15:48
 */
@Slf4j
public class ChatClient {
    private final static String HOST="127.0.0.1";
    private final static int PORT =8889;
    private Selector selector;
    private SocketChannel socketChannel;
    public String username;


    @SneakyThrows
    public ChatClient() {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));
        username = socketChannel.getLocalAddress().toString();
    }
    @SneakyThrows
    public void sendMessage(String info) {
        info =username+":"+info;
        socketChannel.write(ByteBuffer.wrap(info.getBytes()));
    }

    public void readInfo() {
        int select = 0;
        try {
            select = selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(select>0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
                SelectionKey key =  iterator.next();
                if(key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    try {
                        channel.read(buffer);
                        buffer.flip();
                    } catch (IOException e) {
                        try {
                            log.debug("{}",channel.getLocalAddress()+"下线");
                            channel.close();
                            key.cancel();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    log.debug(new String(buffer.array()));
                }
                iterator.remove();
            }
        }
    }
}
