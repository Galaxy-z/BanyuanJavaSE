package com.banyuan.project.chatroomtest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    public static final int PORT = 8888;

    private LinkedBlockingQueue<String> taskQueue;
    private ExecutorService handler;

    private ConcurrentHashMap<String, PrintWriter> userSocketMap;

    public void runService() throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        userSocketMap = new ConcurrentHashMap<>();
        while (true) {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(() -> {
                // 等待客户端传入用户名 阻塞了
                try {
                    String[] firstMsg = in.readLine().split(":");
                    String userName = firstMsg[1];
                    userSocketMap.put(userName, out);
                    System.out.println(userName+"已登录");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 启动侦听线程
                new Thread(() -> {
                    while (true) {

                        try {
                            String content = in.readLine();
                            if (content != null)
                                taskQueue.put(content);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }).start();
        }
    }

    public void runHandler() {
        Runnable handlerExecution = () -> {
            while (true) {
                try {
                    String task = taskQueue.take();
                    Runnable handle = new Handle(task);
                    handler.execute(handle);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread handlerExecutionThread = new Thread(handlerExecution);
        handlerExecutionThread.start();
    }

    public void initHandler() {
        handler = Executors.newFixedThreadPool(5);
        System.out.println("任务处理线程池初始化完成……");
    }

    public void initTaskQueue() {
        taskQueue = new LinkedBlockingQueue<>();
        System.out.println("任务队列初始化完成……");
    }

    public void runServer() throws IOException, InterruptedException {
        initTaskQueue();
        initHandler();
        runHandler();
        runService();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.runServer();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Handle implements Runnable {

        private String task;

        public Handle(String task) {
            this.task = task;
            System.out.println("Handler初始化成功……");
        }

        @Override
        public void run() {
            String[] taskArgs = task.split(":");
            String from = taskArgs[0];
            String to = taskArgs[1];
            String content = taskArgs[2];
            System.out.println(task);
            if (to.equals("0")) {
                Collection<PrintWriter> userOuts = userSocketMap.values();
                for (PrintWriter userOut : userOuts) {
                    userOut.println(from + "对大家说：" + content);
                }
            } else {
                PrintWriter toOut = userSocketMap.get(to);
                toOut.println(from + "对你说" + content);
            }
        }
    }

}


