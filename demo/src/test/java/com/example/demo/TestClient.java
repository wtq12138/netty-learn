package com.example.demo;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-03 15:46
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8888));
        sc.write(Charset.defaultCharset().encode("abc"));
        System.in.read();
//        sc.close();
    }
}
