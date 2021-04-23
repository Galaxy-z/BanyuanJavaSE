package com.banyuan.project.chatroomtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner input = new Scanner(System.in);
            System.out.println("用户名：");
            String userName = input.nextLine();
            out.println("userName:" + userName);

            new Thread(() -> {
                while (true) {
                    out.println(userName + ":" + input.nextLine());
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    try {
                        String msg = in.readLine();
                        if (msg != null)
                            System.out.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
