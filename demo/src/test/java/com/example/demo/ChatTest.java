package com.example.demo;

import com.example.demo.groupchat.ChatClient;
import com.example.demo.groupchat.ChatServer;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-02 18:57
 */
@SpringBootTest
public class ChatTest {
//    @Autowired
//    ChatServer chatServer;
//    @Autowired
//    ChatClient chatClient;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @SneakyThrows
    @Test
    public void test() {
        ChatClient chatClient=new ChatClient();
        while(true) {
            threadPoolExecutor.submit(chatClient::readInfo);
            threadPoolExecutor.submit(()->chatClient.sendMessage("hello 我是2"));
        }
//            Thread.sleep(1000);
//            threadPoolExecutor.submit(()->{
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            });
    }
    @SneakyThrows
    @Test
    public void test1() {
        ChatClient chatClient=new ChatClient();
        while(true) {
            threadPoolExecutor.submit(chatClient::readInfo);
            threadPoolExecutor.submit(()->chatClient.sendMessage("hello 我是1"));
        }
//            Thread.sleep(1000);
//            threadPoolExecutor.submit(()->{
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            });
    }
    @SneakyThrows
    @Test
    public void test2() {
        ChatServer chatServer=new ChatServer();
        chatServer.listen();
    }
}
