package com.banyuan.project.chatroom;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.banyuan.project.chatroom.RequestType.LOGIN;
import static com.banyuan.project.chatroom.ResponseType.REPEATED_USERNAME;

public class ChatClient {

    private JFrame frame;
    private JTextField msgInputField;
    private JTextArea infoDisplayArea;
    private JTextPane onlineUserNumberPane;
    private JButton logInButton;
    private JButton userSetButton;
    private JDialog userSetDialog;
    private JDialog connectSetDialog;

    private List<String> infoList;

    private String userName;
    private String ip;
    private int port;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatClient window = new ChatClient();
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
    public ChatClient() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        infoList = new LinkedList<>();

        frame = new JFrame();
        frame.setTitle("客户端");
        frame.getContentPane().setBackground(new Color(143, 188, 143));
        frame.getContentPane().setLayout(null);

        userSetButton = new JButton("用户设置");
        userSetButton.setForeground(new Color(100, 149, 237));
        userSetButton.setBackground(new Color(255, 255, 255));
        userSetButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        userSetButton.setBounds(6, 6, 82, 35);
        frame.getContentPane().add(userSetButton);
        // TODO 用户设置
        userSetButton.addActionListener(e -> {
            if (userSetDialog == null) {
                userSetDialog = new UserSet(frame);
            }
            userSetDialog.setVisible(true);
        });

        JButton connectSetButton = new JButton("连接设置");
        connectSetButton.setForeground(new Color(188, 143, 143));
        connectSetButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        connectSetButton.setBounds(90, 6, 82, 35);
        frame.getContentPane().add(connectSetButton);
        connectSetButton.addActionListener(e -> {
            if (connectSetDialog == null) {
                connectSetDialog = new ConnectionSet(frame);
            }
            connectSetDialog.setVisible(true);
        });

        logInButton = new JButton("登录");
        logInButton.setForeground(new Color(219, 112, 147));
        logInButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        logInButton.setBounds(210, 6, 60, 35);
        logInButton.setEnabled(false);
        frame.getContentPane().add(logInButton);
        logInButton.addActionListener(e->{
            new Thread(new netEngine()).start();
            userSetButton.setEnabled(false);
            connectSetButton.setEnabled(false);
        });

        JButton quitButton = new JButton("退出");
        quitButton.setForeground(new Color(250, 128, 114));
        quitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        quitButton.setBounds(362, 6, 82, 35);
        frame.getContentPane().add(quitButton);

        JButton logOutButton = new JButton("注销");
        logOutButton.setForeground(new Color(95, 158, 160));
        logOutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        logOutButton.setBounds(272, 6, 60, 35);
        logOutButton.setEnabled(false);
        frame.getContentPane().add(logOutButton);
        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        infoDisplayArea = new JTextArea();
        infoDisplayArea.setBackground(new Color(240, 255, 240));
        infoDisplayArea.setBounds(6, 45, 438, 410);
        frame.getContentPane().add(infoDisplayArea);

        JScrollPane jsp = new JScrollPane();
        jsp.setBounds(6, 45, 438, 410);
        frame.getContentPane().add(jsp);
        jsp.setViewportView(infoDisplayArea);

        msgInputField = new JTextField();
        msgInputField.setBounds(68, 490, 272, 26);
        frame.getContentPane().add(msgInputField);
        msgInputField.setColumns(10);

        JLabel msgSendLabel = new JLabel("发送消息：");
        msgSendLabel.setBounds(6, 495, 65, 16);
        frame.getContentPane().add(msgSendLabel);

        JButton msgSendButton = new JButton("发送");
        msgSendButton.setBounds(343, 490, 101, 29);
        frame.getContentPane().add(msgSendButton);
        msgSendButton.setEnabled(false);

        JLabel lblNewLabel_1 = new JLabel("发送至：");
        lblNewLabel_1.setBounds(6, 467, 61, 16);
        frame.getContentPane().add(lblNewLabel_1);

        JComboBox toUserComboBox = new JComboBox();
        toUserComboBox.setBounds(68, 463, 82, 27);
        frame.getContentPane().add(toUserComboBox);

        JLabel expressionLabel = new JLabel("表情：");
        expressionLabel.setBounds(153, 467, 44, 16);
        frame.getContentPane().add(expressionLabel);

        JComboBox expressionComboBox = new JComboBox();
        expressionComboBox.setBounds(193, 463, 77, 27);
        frame.getContentPane().add(expressionComboBox);

        JCheckBox whisperCheckBox = new JCheckBox("悄悄话");
        whisperCheckBox.setBounds(266, 463, 71, 23);
        frame.getContentPane().add(whisperCheckBox);

        onlineUserNumberPane = new JTextPane();
        onlineUserNumberPane.setForeground(new Color(105, 105, 105));
        onlineUserNumberPane.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        onlineUserNumberPane.setText("3人在线");
        onlineUserNumberPane.setBackground(new Color(143, 188, 143));
        onlineUserNumberPane.setBounds(6, 523, 65, 16);
        frame.getContentPane().add(onlineUserNumberPane);

        JButton sendFileBox = new JButton("发送文件");
        sendFileBox.setBounds(343, 462, 101, 29);
        frame.getContentPane().add(sendFileBox);
        frame.setBounds(100, 100, 450, 573);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sendFileBox.setEnabled(false);
    }

    private class UserSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private JTextField userNameTextField;

        public UserSet(JFrame owner) {
            super(owner, "用户设置", true);
            setBounds(100, 100, 293, 196);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel("请输入用户名：");
                lblNewLabel.setBounds(34, 56, 96, 16);
                contentPanel.add(lblNewLabel);
            }
            {
                userNameTextField = new JTextField();
                userNameTextField.setText("gg");
                userNameTextField.setBounds(126, 51, 130, 26);
                contentPanel.add(userNameTextField);
                userNameTextField.setColumns(10);
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
                        userName = userNameTextField.getText();
                        if (port != 0 && ip != null) {
                            logInButton.setEnabled(true);
                        }
                        displayInfo("用户名设置为：" + userName);
                        userSetDialog.setVisible(false);
                    });
                }
                {
                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.setActionCommand("Cancel");
                    buttonPane.add(cancelButton);
                    cancelButton.addActionListener(e -> userSetDialog.setVisible(false));
                }
            }
        }
    }

    private class ConnectionSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private JTextField ipTextField;
        private JTextField portTextField;

        public ConnectionSet(JFrame owner) {
            super(owner, "连接设置", true);
            setBounds(100, 100, 306, 250);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel("请输入服务器ip地址：");
                lblNewLabel.setBounds(24, 55, 159, 16);
                contentPanel.add(lblNewLabel);
            }
            {
                JLabel lblNewLabel = new JLabel("请输入服务器端口号：");
                lblNewLabel.setBounds(24, 106, 159, 16);
                contentPanel.add(lblNewLabel);
            }
            {
                ipTextField = new JTextField();
                ipTextField.setText("127.0.0.1");
                ipTextField.setBounds(154, 50, 130, 26);
                contentPanel.add(ipTextField);
                ipTextField.setColumns(10);
            }
            {
                portTextField = new JTextField();
                portTextField.setText("8888");
                portTextField.setColumns(10);
                portTextField.setBounds(154, 101, 130, 26);
                contentPanel.add(portTextField);
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
                        ip = ipTextField.getText();
                        port = Integer.parseInt(portTextField.getText());
                        if (userName != null) {
                            logInButton.setEnabled(true);
                        }
                        displayInfo("服务器ip设置为：" + ip);
                        displayInfo("连接端口设置为：" + port);
                        connectSetDialog.setVisible(false);
                    });
                }
                {
                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.setActionCommand("Cancel");
                    buttonPane.add(cancelButton);
                    cancelButton.addActionListener(e -> connectSetDialog.setVisible(false));
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

    private class netEngine implements Runnable {

        private Set<String> onlineUserSet;

        private LinkedBlockingQueue<Response> responseQueue;

        private ExecutorService handlerThreadPool;

        @Override
        public void run() {
            initRequestQueue();
            initHandlerThreadPool();
            runHandler();
            try {
                connectServer();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void initRequestQueue() {
            responseQueue = new LinkedBlockingQueue<>();
            displayInfo("响应队列初始化完成");
        }

        private void initHandlerThreadPool() {
            handlerThreadPool = Executors.newSingleThreadExecutor();
            displayInfo("响应处理线程池初始化完成");
        }

        private void runHandler() {
            Runnable handlerExecution = () -> {
                while (true) {
                    try {
                        Response response = responseQueue.take();
                        Runnable handler = new Handler(response);
                        handlerThreadPool.execute(handler);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread handleExecutionThread = new Thread(handlerExecution);
            handleExecutionThread.start();
            displayInfo("响应处理线程池已启动");

        }

        private void connectServer() throws IOException, ClassNotFoundException {
            Socket socket = new Socket(ip, port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            // 发送登录消息
            displayInfo("登录中……");
            out.writeObject(new Request(userName, "server", LOGIN));
            Response loginResponse = (Response) in.readObject();
            System.out.println(loginResponse);
            if (loginResponse.getType() == REPEATED_USERNAME) {
                displayInfo("用户名重复，请更换用户名重新登录");
                userName = null;
                logInButton.setEnabled(false);
                userSetButton.setEnabled(true);
                return;
            }
            displayInfo("登录成功");

            // 启动侦听线程
            new Thread(() -> {

                while (true) {
                    try {
                        Response response = (Response) in.readObject();
                        if (response != null)
                            responseQueue.put(response);
                    } catch (IOException | InterruptedException | ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
//                    finally {
//                        displayInfo("服务器已断开连接");
//                        ok = false;
//                    }
                    displayInfo("服务器已断开连接");
                }
            }).start();
        }
    }

    private class Handler implements Runnable {
        private Response response;

        public Handler(Response response) {
            this.response = response;
        }

        @Override
        public void run() {
            switch (response.getType()){
                case NEW_USER_LIST:
                    String[] users = response.getText().split(",");
                    onlineUserNumberPane.setText(users.length+"人在线");


            }

        }
    }
}
