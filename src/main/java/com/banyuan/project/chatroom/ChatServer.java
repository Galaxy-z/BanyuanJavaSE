package com.banyuan.project.chatroom;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.banyuan.project.chatroom.RequestType.*;
import static com.banyuan.project.chatroom.ResponseType.*;

public class ChatServer {


    // UI
    private JFrame frame;
    private JTextField msgInputField;
    private JTextArea infoDisplayArea;
    private JTextPane onlineUserNumberPane;
    private JButton startServiceButton;
    private JButton stopServiceButton;
    private JButton msgSendButton;
    private JButton quitButton;
    private JComboBox toUserComboBox;
    private JDialog portSetDialog;
    // Information List
    private List<String> infoList;

    // 端口
    private int port;

    // 请求队列
    private LinkedBlockingQueue<Request> requestQueue;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {

            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatServer window = new ChatServer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ChatServer() {
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        infoList = new LinkedList<>();

        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(222, 184, 135));
        frame.setBackground(new Color(192, 192, 192));
        frame.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        frame.setTitle("服务端");
        frame.setBounds(100, 100, 516, 462);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocation(500, 200);

        JButton portSetButton = new JButton("端口设置");
        portSetButton.setForeground(new Color(100,149,237));
        portSetButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        portSetButton.setBounds(6, 6, 117, 39);
        frame.getContentPane().add(portSetButton);
        portSetButton.addActionListener(e -> {
            if (portSetDialog == null) {
                portSetDialog = new PortSet(frame);
            }
            portSetDialog.setVisible(true);
        });

        JLabel msgSendLabel = new JLabel("发送消息：");
        msgSendLabel.setBounds(18, 387, 65, 16);
        frame.getContentPane().add(msgSendLabel);

        msgInputField = new JTextField();
        msgInputField.setBounds(77, 382, 304, 26);
        frame.getContentPane().add(msgInputField);
        msgInputField.setColumns(10);
        msgInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if (k == KeyEvent.VK_ENTER)
                    msgSendButton.doClick();
            }
        });

        msgSendButton = new JButton("发送");
        msgSendButton.setBounds(393, 382, 117, 29);
        msgSendButton.setEnabled(false);
        frame.getContentPane().add(msgSendButton);
        msgSendButton.addActionListener(e -> {
            try {
                requestQueue.put(new Request("server", "server", SEND_SERVER_MSG));
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        infoDisplayArea = new JTextArea();
        infoDisplayArea.setBackground(new Color(253, 245, 230));
        infoDisplayArea.setBounds(6, 50, 485, 291);
        frame.getContentPane().add(infoDisplayArea);

        JScrollPane jsp = new JScrollPane();
        jsp.setBounds(6, 50, 504, 291);
        frame.getContentPane().add(jsp);
        jsp.setViewportView(infoDisplayArea);

        startServiceButton = new JButton("启动服务");
        startServiceButton.setForeground(new Color(244,164,96));
        startServiceButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        startServiceButton.setBounds(135, 6, 117, 39);
        frame.getContentPane().add(startServiceButton);
        startServiceButton.setEnabled(false);
        startServiceButton.addActionListener((e) -> {
            // 启动网络引擎线程
            new Thread(new NetEngine()).start();
            displayInfo("服务器启动中");
            startServiceButton.setEnabled(false);
            portSetButton.setEnabled(false);
            stopServiceButton.setEnabled(true);
        });

        stopServiceButton = new JButton("停止服务");
        stopServiceButton.setForeground(new Color(95,158,160));
        stopServiceButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        stopServiceButton.setBounds(264, 6, 117, 39);
        stopServiceButton.setEnabled(false);
        frame.getContentPane().add(stopServiceButton);
        stopServiceButton.addActionListener(e -> {
                    try {
                        requestQueue.put(new Request("server", "server", SHUTDOWN));
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    portSetButton.setEnabled(true);
                    startServiceButton.setEnabled(true);
                    msgSendButton.setEnabled(false);
                    stopServiceButton.setEnabled(false);
                }
        );

        quitButton = new JButton("退出");
        quitButton.setForeground(new Color(250, 128, 114));
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        quitButton.setBounds(393, 6, 117, 39);
        frame.getContentPane().add(quitButton);

        toUserComboBox = new JComboBox();
        toUserComboBox.setBounds(77, 353, 96, 27);
        frame.getContentPane().add(toUserComboBox);

        JLabel sendToLabel = new JLabel("发送至：");
        sendToLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        sendToLabel.setBounds(18, 357, 53, 16);
        frame.getContentPane().add(sendToLabel);

        onlineUserNumberPane = new JTextPane();
        onlineUserNumberPane.setEditable(false);
        onlineUserNumberPane.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        onlineUserNumberPane.setForeground(new Color(105, 105, 105));
        onlineUserNumberPane.setBackground(new Color(222, 184, 135));
        onlineUserNumberPane.setText("服务器未启动");
        onlineUserNumberPane.setBounds(6, 415, 70, 16);
        frame.getContentPane().add(onlineUserNumberPane);
    }

    // 端口设置窗口
    private class PortSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private JTextField textField;

        public PortSet(JFrame owner) {
            super(owner, "端口设置", true);
            setTitle("端口设置");
            setBounds(100, 100, 310, 195);
            setLocation(600, 300);
            setLocationRelativeTo(owner);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel("请输入服务器端口号：");
                lblNewLabel.setBounds(35, 56, 130, 16);
                contentPanel.add(lblNewLabel);
            }
            {
                textField = new JTextField();
                textField.setText("8888");
                textField.setBounds(170, 51, 109, 26);
                contentPanel.add(textField);
                textField.setColumns(10);
            }
            {
                JPanel buttonPane = new JPanel();
                buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                getContentPane().add(buttonPane, BorderLayout.SOUTH);
                {
                    JButton okButton = new JButton("OK");
                    okButton.setActionCommand("OK");
                    buttonPane.add(okButton);
                    getRootPane().setDefaultButton(okButton);
                    okButton.addActionListener(e -> {
                        port = Integer.parseInt(textField.getText());
                        displayInfo("端口设置为:" + port);
                        setVisible(false);
                        startServiceButton.setEnabled(true);
                    });
                }
                {
                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.setActionCommand("Cancel");
                    buttonPane.add(cancelButton);
                    cancelButton.addActionListener(e -> setVisible(false));
                }
            }
        }
    }

    // 在信息界面显示信息
    private synchronized void displayInfo(String info) {
        // 添加时间戳
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String time = sdf.format(date);
        // 向信息列表中添加信息
        infoList.add(time + " " + info);
        // 拼接消息并展示
        StringBuilder sb = new StringBuilder();
        for (String s : infoList) {
            sb.append(s).append("\n");
        }
        infoDisplayArea.setText(sb.toString());
        infoDisplayArea.setCaretPosition(infoDisplayArea.getText().length());
    }

    // 网络引擎(服务器主线程)
    private class NetEngine implements Runnable {

        // 用户-输出流映射表
        private ConcurrentHashMap<String, ObjectOutputStream> userOutMap;

        // handler线程池
        private ExecutorService handlerThreadPool;

        ServerSocket serverSocket;

        @Override
        public void run() {
            // 初始化请求队列
            initRequestQueue();
            // 初始化handler线程池
            initHandlerThreadPool();
            // 运行handler线程池
            runHandler();
            // 运行服务
            try {
                runService();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void initRequestQueue() {
            requestQueue = new LinkedBlockingQueue<>();
            displayInfo("请求队列初始化完成");
        }

        private void initHandlerThreadPool() {
            handlerThreadPool = Executors.newFixedThreadPool(5);
            displayInfo("请求处理线程池初始化完成");
        }

        private void runHandler() {
            // 这个线程会等待请求队列，从中取出请求并创建handler线程处理
            Runnable handlerExecution = () -> {
                while (true) {
                    try {
                        Request request = requestQueue.take();
                        Runnable handler = new Handler(request);
                        handlerThreadPool.execute(handler);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            };
            Thread handlerExecutionThread = new Thread(handlerExecution);
            handlerExecutionThread.start();
            displayInfo("请求处理线程池任务投送线程已启动");
        }

        // 启动服务
        private void runService() throws IOException {
            displayInfo("获取ip地址……");
            InetAddress addr = InetAddress.getLocalHost();
            displayInfo("本机局域网ip地址：" + addr.getHostAddress() + "，端口：" + port);
            // 初始化server socket，用户-输出流映射表
            serverSocket = new ServerSocket(port);
            userOutMap = new ConcurrentHashMap<>();
            displayInfo("服务器启动完成，等待用户连接……");
            onlineUserNumberPane.setText("0人在线");
            msgSendButton.setEnabled(true);

            while (true) {
                // 等待与客户端建立连接，初始化输入输出流

                Socket socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                // 这个线程实现用户首次登陆验证，并且登录成功才会对用户创建侦听线程
                new Thread(() -> {
                    // 等待客户端传入用户名
                    try {
                        // 首次登录
                        Request loginRequest = (Request) in.readObject();
                        System.out.println(loginRequest);
                        String userName = loginRequest.getFrom();
                        // 用户名重复
                        if (userOutMap.containsKey(userName)) {
                            // 响应REPEATED_USERNAME
                            synchronized (Handler.class) {
                                out.writeObject(new Response("server", null, REPEATED_USERNAME));
                            }
                        }
                        // 新用户名
                        else {
                            // 响应OK
                            synchronized (Handler.class) {
                                out.writeObject(new Response("server", null, OK));
                            }
                            // 存入用户名和输出流
                            userOutMap.put(userName, out);
                            // 请求刷新用户列表
                            requestQueue.put(new Request("server", "server", REFRESH_USER_LIST));
                            displayInfo(userName + "已登录");
                            // 启动侦听线程
                            new Thread(() -> {
                                while (true) {
                                    try {
                                        Request request = (Request) in.readObject();
                                        if (request != null) {
                                            System.out.println(request);
                                            requestQueue.put(request);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        if (e instanceof IOException | e instanceof EOFException) {
                                            // 发生异常，退出侦听
                                            break;
                                        }
                                    }
                                }
                                // 显示用户断开连接，移除用户
                                displayInfo(userName + "断开连接");
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    removeUser(userName);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }).start();

                        }
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();

            }
        }

        // 与用户断连，删除用户
        private void removeUser(String username) throws InterruptedException {
            if (userOutMap != null) {
                userOutMap.remove(username);
                requestQueue.put(new Request("server", "server", REFRESH_USER_LIST));
            }
        }

        // Handler 处理Request
        private class Handler implements Runnable {

            private Request request;

            public Handler(Request request) {
                this.request = request;
            }

            @Override
            public void run() {
                switch (request.getType()) {
                    // 刷新用户列表
                    case REFRESH_USER_LIST:
                        try {
                            notifyUserNumberChange();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 服务器发送信息
                    case SEND_SERVER_MSG:
                        try {
                            serverSendMsg();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 转发信息
                    case SEND_MSG:
                        try {
                            sendMsg(request);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 转发简单信息
                    case SEND_ASK_FILE_ACCEPT:
                    case ACCEPT_FILE:
                    case REFUSE_FILE:
                    case SEND_VERIFICATION_FAILED:
                        try {
                            sendSimpleResponse();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 转发文件数据包
                    case SEND_FILE_PACKAGE:
                        try {
                            sendFilePackage();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 用户注销
                    case LOGOUT:
                        try {
                            removeUser(request.getFrom());
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    case SHUTDOWN:
                        try {
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Collection<ObjectOutputStream> outs = userOutMap.values();
                        for (ObjectOutputStream out : outs) {
                            try {
                                synchronized (Handler.class) {
                                    out.writeObject(new Response("server", "public", SERVICE_SHUTDOWN));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        userOutMap = null;
                        handlerThreadPool = null;
                        toUserComboBox.removeAllItems();
                        onlineUserNumberPane.setText("服务器未启动");
                        displayInfo("已关闭服务");

                }
            }

            // 转发文件数据包
            private void sendFilePackage() throws IOException {
                String to = request.getTo();
                ObjectOutputStream out = userOutMap.get(to);
                Response response = new Response(request.getFrom(), to, FILE_PACKAGE, request.getText(), request.getContent());
                synchronized (Handler.class) {
                    out.writeObject(response);
                }
            }

            // 转发简单信息
            private void sendSimpleResponse() throws IOException {
                String from = request.getFrom();
                String to = request.getTo();
                String text = request.getText();
                RequestType requestType = request.getType();
                ResponseType responseType = null;
                // Request 转 Response
                switch (requestType) {
                    case SEND_ASK_FILE_ACCEPT:
                        responseType = ASK_FILE_ACCEPT;
                        break;
                    case ACCEPT_FILE:
                        responseType = TARGET_ACCEPT_FILE;
                        break;
                    case REFUSE_FILE:
                        responseType = TARGET_REFUSE_FILE;
                        break;
                    case SEND_VERIFICATION_FAILED:
                        responseType = VERIFICATION_FAILED;
                        break;

                }
                Response response = new Response(from, to, responseType, text);
                ObjectOutputStream out = userOutMap.get(to);
                synchronized (Handler.class) {
                    out.writeObject(response);
                }
            }

            // 服务器向外发送信息
            private void serverSendMsg() throws IOException {
                String to = (String) toUserComboBox.getSelectedItem();
                String msg = msgInputField.getText();
                if (msg.equals("")) {
                    displayInfo("消息不能为空");
                } else if (to == null) {
                    displayInfo("没有接受信息的用户！");
                } else {
                    sendMsg(new Request("server", to, SEND_MSG, "", !to.equals("所有人"), msg));
                }
                msgInputField.setText("");
            }

            // 转发信息 request -> response
            private void sendMsg(Request request) throws IOException {
                String from = request.getFrom();
                String to = request.getTo();
                String expression = request.getExpression();
                boolean whisper = request.isWhisper();
                String msg = request.getText();

                String s = (whisper ? "*悄悄话*" : "") +
                        (from.equals("server") ? "服务器" : from) +
                        (expression.equals("") ? "" : (expression + "地")) +
                        "对" + to + "说：" + msg;
                displayInfo(s);
                Response response = new Response(from, to, INCOMING_MSG, expression, whisper, msg);
                // 悄悄话单独发
                if (whisper) {
                    ObjectOutputStream out = userOutMap.get(to);
                    synchronized (Handler.class) {
                        out.writeObject(response);
                    }

                } else {
                    Collection<ObjectOutputStream> outs = userOutMap.values();
                    for (ObjectOutputStream out : outs) {
                        // 不向发来信息的用户发送
                        if (out != userOutMap.get(from)) {
                            synchronized (Handler.class) {
                                out.writeObject(response);
                            }
                        }
                    }
                }
            }

            // 提醒所有用户刷新用户列表
            private void notifyUserNumberChange() throws IOException {
                // 刷新服务端用户数量
                onlineUserNumberPane.setText(userOutMap.size() + "人在线");
                Collection<ObjectOutputStream> outs = userOutMap.values();
                Set<String> users = userOutMap.keySet();

                // 刷新服务端用户选取列表
                StringBuilder sb = new StringBuilder();
                toUserComboBox.removeAllItems();
                toUserComboBox.addItem("所有人");

                // 通过text字符串发送用户列表
                for (String user : users) {
                    toUserComboBox.addItem(user);
                    sb.append(user).append(",");
                }
                // 广播
                for (ObjectOutputStream out : outs) {
                    synchronized (Handler.class) {
                        out.writeObject(new Response("server", "all", NEW_USER_LIST, (sb.toString())));
                    }
                }

            }
        }
    }

}
