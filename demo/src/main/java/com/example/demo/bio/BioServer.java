package com.example.demo.bio;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.ldap.SortKey;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 10:46
 */
    @Component
    public class BioServer {
        @Autowired
        private ThreadPoolExecutor pool;
        @SneakyThrows
        public void handle(Socket socket) {
            try{
                byte [] bytes=new byte[1024];
                InputStream is=socket.getInputStream();
                while(true) {
                    int num = is.read(bytes);
                    if(num==-1) {
                        break;
                    }else {
                        System.out.println(new String(bytes));
                    }
                }
            }finally {
                System.out.println("socket close");
                socket.close();
            }
        }

        @SneakyThrows
        public void listen() {
            ServerSocket serverSocket=new ServerSocket(8888);
            System.out.println(pool);
            System.out.println("server start");
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("client connect");
                pool.execute(() -> handle(socket));
            }
        }
    }
