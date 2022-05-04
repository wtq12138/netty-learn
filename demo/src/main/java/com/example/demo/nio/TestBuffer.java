package com.example.demo.nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-04-30 15:43
 */
public class TestBuffer {
    public static void main(String[] args) {
        IntBuffer buffer=IntBuffer.allocate(5);
        for(int i=0;i<buffer.capacity();i++) {
            buffer.put(i);
        }
        buffer.flip();
        while(buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
