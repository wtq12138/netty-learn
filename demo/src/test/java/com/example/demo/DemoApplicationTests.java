package com.example.demo;

import com.example.demo.bio.BioServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    BioServer bioServer;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Test
    void contextLoads() throws InterruptedException {
        threadPoolExecutor.submit(()->bioServer.listen());
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
