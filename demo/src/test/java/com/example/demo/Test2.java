package com.example.demo;

import com.example.demo.nio.NioClient;
import com.example.demo.nio.NioServer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-02 10:30
 */
@SpringBootTest
public class Test2 {
    @Autowired
    NioServer nioServer;
    @Autowired
    NioClient nioClient;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @SneakyThrows
    @Test
    public void test() {
        nioServer.listen();
    }
    @SneakyThrows
    @Test
    public void test2() {
        threadPoolExecutor.submit(()->nioServer.listen());
        for(int i=1;i<8;i++) {
            Thread.sleep(1000);
            threadPoolExecutor.submit(()->{
                Socket socket= null;
                try {
                    socket = new Socket("127.0.0.1", 8888);
                    System.out.println(socket.getLocalSocketAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    OutputStream os = socket.getOutputStream();
                    os.write(Thread.currentThread().toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
