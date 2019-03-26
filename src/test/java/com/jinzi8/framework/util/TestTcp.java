package com.jinzi8.framework.util;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 博哥
 * @create 2019-03-19 10:34
 */
public class TestTcp {
    @Test
    public void client() throws Exception {
        Socket socket = new Socket("localhost", 7070);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        System.out.println("客户端发送请求前：");
        outputStream.write("客户端的请求".getBytes());
        System.out.println("客户端发送请求后：");
        outputStream.close();
        int length = 0;
        byte[] bytes = new byte[102400];
        System.out.println("客户端接收数据前：");
        length = inputStream.read(bytes);
         String s = new String(bytes, 0, length);
           System.out.println(s);
        System.out.println("客户端接收数据后：");
    }
    @Test
    public  void server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(7070);
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        System.out.println("服务端接收数据前：");
        length = inputStream.read(bytes);
            String s = new String(bytes, 0, length);
            System.out.println(s);
        System.out.println("服务端接收数据后：");
        OutputStream outputStream = accept.getOutputStream();
        System.out.println("服务端响应数据前：");
        outputStream.write("".getBytes());
        System.out.println("服务端响应数据后：");
    }
}
