package com.example.demo.nio;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 16:43
 */
public class FileChannelTest {
    @SneakyThrows
    public void write() {
        String str="hello";
        FileOutputStream fileOutputStream=new FileOutputStream("1.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
    @SneakyThrows
    public void read() {
        FileInputStream fileInputStream=new FileInputStream("F:\\资料\\网课\\尚硅谷\\netty\\demo\\1.txt");
        FileChannel channel=fileInputStream.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        channel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array()));
    }
    @SneakyThrows
    public void copy() {
        FileInputStream fileInputStream=new FileInputStream("F:\\资料\\网课\\尚硅谷\\netty\\demo\\1.txt");
        FileOutputStream fileOutputStream=new FileOutputStream("2.txt");
        FileChannel channel1=fileInputStream.getChannel();
        FileChannel channel2=fileOutputStream.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
//        channel1.read(byteBuffer);
//        byteBuffer.flip();
//        channel2.write(byteBuffer);
        channel2.transferFrom(channel1, 0, channel1.size());
    }
    @SneakyThrows
    public static void main(String[] args) {
        FileChannelTest test=new FileChannelTest();
        test.write();
        test.read();
        test.copy();
    }
}
