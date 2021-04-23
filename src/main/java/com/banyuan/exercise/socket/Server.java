package com.banyuan.exercise.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("服务器已启动，等待客户端连接……");

            Scanner input = new Scanner(System.in);

            ConcurrentHashMap<String, PrintWriter>
                    clientMap = new ConcurrentHashMap<>();

            new Thread(() -> {
                while (true) {
                    String s = input.nextLine();
                    for (PrintWriter out : clientMap.values()) {
                        out.println(s);
                    }

                }
            }).start();
            int index = 1;

            while (true) {
                Socket socket = ss.accept();
                System.out.println("客户端已连接上");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                clientMap.put("client-"+index++, out);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));



                new Thread(() -> {
                    while (true) {
                        try {
                            String msg = br.readLine();
                            System.out.println(msg);
                            //解析
                            String num = msg.substring(0,msg.indexOf("#"));
                            String content = msg.substring(msg.indexOf("#")+1);
                            if ("0".equals(num)){
                                for (PrintWriter printWriter : clientMap.values()) {
                                    out.println(printWriter);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
