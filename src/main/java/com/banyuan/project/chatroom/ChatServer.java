package com.banyuan.project.chatroom;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.banyuan.project.chatroom.RequestType.REFRESH_USER_NUMBER;
import static com.banyuan.project.chatroom.ResponseType.*;

public class ChatServer {

    // UI
    private JFrame frame;
    private JTextField msgInputField;
    private JTextArea infoDisplayArea;
    private JTextPane onlineUserNumberPane;
    private JButton startServiceButton;
    private JButton stopServiceButton;
    private JButton quitButton;
    private JDialog portSetDialog;
    // Information List
    private List<String> infoList;

    private int port;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocation(500, 200);

        JButton portSetButton = new JButton("端口设置");
        portSetButton.setForeground(new Color(205, 133, 63));
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

        JButton msgSendButton = new JButton("发送");
        msgSendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        msgSendButton.setBounds(393, 382, 117, 29);
        msgSendButton.setEnabled(false);
        frame.getContentPane().add(msgSendButton);

        infoDisplayArea = new JTextArea();
        infoDisplayArea.setBackground(new Color(253, 245, 230));
        infoDisplayArea.setBounds(6, 50, 485, 291);
        frame.getContentPane().add(infoDisplayArea);

        JScrollPane jsp = new JScrollPane();
        jsp.setBounds(6, 50, 504, 291);
        frame.getContentPane().add(jsp);
        jsp.setViewportView(infoDisplayArea);

        startServiceButton = new JButton("启动服务");
        startServiceButton.setForeground(new Color(60, 179, 113));
        startServiceButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        startServiceButton.setBounds(135, 6, 117, 39);
        frame.getContentPane().add(startServiceButton);
        startServiceButton.setEnabled(false);
        startServiceButton.addActionListener((e) -> {
            // 启动网络引擎线程
            new Thread(new NetEngine()).start();
            displayInfo("服务器启动中，端口号：" + port);
            startServiceButton.setEnabled(false);
            portSetButton.setEnabled(false);
            stopServiceButton.setEnabled(true);
        });

        stopServiceButton = new JButton("停止服务");
        stopServiceButton.setForeground(new Color(128, 128, 128));
        stopServiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        stopServiceButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        stopServiceButton.setBounds(264, 6, 117, 39);
        stopServiceButton.setEnabled(false);
        frame.getContentPane().add(stopServiceButton);

        quitButton = new JButton("退出");
        quitButton.setForeground(new Color(250, 128, 114));
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        quitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        quitButton.setBounds(393, 6, 117, 39);
        frame.getContentPane().add(quitButton);

        JComboBox toUserComboBox = new JComboBox();
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
        onlineUserNumberPane.setText("0人在线");
        onlineUserNumberPane.setBounds(6, 415, 53, 16);
        frame.getContentPane().add(onlineUserNumberPane);
    }

    private class PortSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private JTextField textField;

        public PortSet(JFrame owner) {
            super(owner, "端口设置", true);
            setTitle("端口设置");
            setBounds(100, 100, 310, 195);
            setLocation(600, 300);
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
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String time = sdf.format(date);

        infoList.add(time + " " + info);
        StringBuilder sb = new StringBuilder();
        for (String s : infoList) {
            sb.append(s).append("\n");
        }
        infoDisplayArea.setText(sb.toString());
    }

    private class NetEngine implements Runnable {

        // 用户-输出Map
        private ConcurrentHashMap<String, ObjectOutputStream> userOutMap;

        private LinkedBlockingQueue<Request> requestQueue;

        private ExecutorService handlerThreadPool;

        @Override
        public void run() {
            initRequestQueue();
            initHandlerThreadPool();
            runHandler();
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
            Runnable handlerExecution = () -> {
                while (true) {
                    try {
                        Request request = requestQueue.take();
                        Runnable handler = new Handler(request);
                        handlerThreadPool.execute(handler);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread handlerExecutionThread = new Thread(handlerExecution);
            handlerExecutionThread.start();
            displayInfo("请求处理线程池已启动");
        }

        private void runService() throws IOException {
            ServerSocket serverSocket = new ServerSocket(port);
            userOutMap = new ConcurrentHashMap<>();
            displayInfo("服务器启动完成，等待用户连接……");
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                new Thread(() -> {
                    // 等待客户端传入用户名
                    try {
                        // 首次登录
                        Request loginRequest = (Request) in.readObject();
                        System.out.println(loginRequest);
                        String userName = loginRequest.getFrom();
                        if (userOutMap.containsKey(userName)) {
                            out.writeObject(new Response("server", null, REPEATED_USERNAME));
                        } else {
                            out.writeObject(new Response("server", null, OK));
                            userOutMap.put(userName, out);
                            requestQueue.put(new Request("server", "server", REFRESH_USER_NUMBER));
                            displayInfo(userName + "已登录");
                            notifyUserNumberChange();
                            // 启动侦听线程
                            new Thread(() -> {
                                while (true) {
                                    try {
                                        Request request = (Request) in.readObject();
                                        if (request != null)
                                            requestQueue.put(request);
                                    } catch (IOException | InterruptedException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                        break;
                                    }
//                                    } finally {
//                                        displayInfo(userName + "断开连接");
//                                        removeUser(userName);
//                                        try {
//                                            notifyUserNumberChange();
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                        ok = false;
                                    displayInfo(userName + "断开连接");
                                    removeUser(userName);
                                    try {
                                        notifyUserNumberChange();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();
            }
        }

        private void removeUser(String username) {
            userOutMap.remove(username);
        }

        private void notifyUserNumberChange() throws IOException {
            onlineUserNumberPane.setText(userOutMap.size() + "人在线");

            Collection<ObjectOutputStream> outs = userOutMap.values();
            Set<String> users = userOutMap.keySet();

            StringBuilder sb = new StringBuilder();
            for (String user : users) {
                sb.append(user).append(",");
            }

            for (ObjectOutputStream out : outs) {
                out.writeObject(new Response("server", "public", NEW_USER_LIST, (sb.toString())));
            }

        }
    }

    private class Handler implements Runnable {

        private Request request;

        public Handler(Request request) {
            this.request = request;
        }

        @Override
        public void run() {

        }
    }
}
