package com.example.demo.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 18:49
 */
public class ScatteringAndGatheringTest {
    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        ByteBuffer [] byteBuffers =new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(5);
        SocketChannel accept = serverSocketChannel.accept();
        while(true) {
            accept.read(byteBuffers);
            Object [] bytes = Arrays.stream(byteBuffers).toArray();
            System.out.println(new String(bytes.toString()));
        }
    }
}
