package com.banyuan.project.chatroom;



import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.banyuan.project.chatroom.RequestType.*;
import static com.banyuan.project.chatroom.ResponseType.*;

public class ChatClient {
    private final int MAX_PACKAGE_SIZE = 102400;

    private JFrame frame;
    private JTextField msgInputField;
    private JTextArea infoDisplayArea;
    private JTextPane onlineUserNumberPane;
    private JButton logInButton;
    private JButton userSetButton;
    private JButton logOutButton;
    private JButton msgSendButton;
    private JButton fileSendButton;
    private JButton connectSetButton;
    private JComboBox toUserComboBox;
    private JComboBox expressionComboBox;
    private JCheckBox whisperCheckBox;
    private JDialog userSetDialog;
    private JDialog connectSetDialog;

    private LinkedList<String> infoList;

    private String userName;
    private String ip;
    private int port;

    private File selectedFile;

    private LinkedBlockingQueue<Response> responseQueue;

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
        frame.setLocation(500, 300);

        userSetButton = new JButton("用户设置");
        userSetButton.setForeground(new Color(100, 149, 237));
        userSetButton.setBackground(new Color(255, 255, 255));
        userSetButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        userSetButton.setBounds(6, 6, 82, 35);
        frame.getContentPane().add(userSetButton);
        userSetButton.addActionListener(e -> {
            if (userSetDialog == null) {
                userSetDialog = new UserSet(frame);
            }
            userSetDialog.setVisible(true);
        });

        connectSetButton = new JButton("连接设置");
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
        logInButton.addActionListener(e -> {
            new Thread(new NetEngine()).start();
            userSetButton.setEnabled(false);
            connectSetButton.setEnabled(false);
        });

        JButton quitButton = new JButton("退出");
        quitButton.setForeground(new Color(250, 128, 114));
        quitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        quitButton.setBounds(362, 6, 82, 35);
        frame.getContentPane().add(quitButton);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        logOutButton = new JButton("注销");
        logOutButton.setForeground(new Color(95, 158, 160));
        logOutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        logOutButton.setBounds(272, 6, 60, 35);
        logOutButton.setEnabled(false);
        frame.getContentPane().add(logOutButton);
        logOutButton.addActionListener(e -> {
            try {
                responseQueue.put(new Response(userName, userName, SEND_LOGOUT));
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            logInButton.setEnabled(true);
            logOutButton.setEnabled(false);
            userSetButton.setEnabled(true);
            connectSetButton.setEnabled(true);
            msgSendButton.setEnabled(false);
            fileSendButton.setEnabled(false);
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
        msgInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if (k == KeyEvent.VK_ENTER)
                    msgSendButton.doClick();
            }
        });

        JLabel msgSendLabel = new JLabel("发送消息：");
        msgSendLabel.setBounds(6, 495, 65, 16);
        frame.getContentPane().add(msgSendLabel);

        msgSendButton = new JButton("发送");
        msgSendButton.setBounds(343, 490, 101, 29);
        frame.getContentPane().add(msgSendButton);
        msgSendButton.setEnabled(false);
        msgSendButton.addActionListener(e -> {
            try {
                responseQueue.put(new Response(userName, userName, SEND_CLIENT_MSG));
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        JLabel lblNewLabel_1 = new JLabel("发送至：");
        lblNewLabel_1.setBounds(6, 467, 61, 16);
        frame.getContentPane().add(lblNewLabel_1);

        toUserComboBox = new JComboBox();
        toUserComboBox.setBounds(68, 463, 94, 27);
        frame.getContentPane().add(toUserComboBox);

        JLabel expressionLabel = new JLabel("表情：");
        expressionLabel.setBounds(163, 467, 44, 16);
        frame.getContentPane().add(expressionLabel);

        expressionComboBox = new JComboBox<String>();
        expressionComboBox.setBounds(193, 463, 77, 27);
        frame.getContentPane().add(expressionComboBox);
        expressionComboBox.addItem("");
        expressionComboBox.addItem("微笑");
        expressionComboBox.addItem("生气");
        expressionComboBox.addItem("疑惑");
        expressionComboBox.addItem("鄙视");
        expressionComboBox.addItem("潇洒");
        expressionComboBox.addItem("痛苦");

        whisperCheckBox = new JCheckBox("悄悄话");
        whisperCheckBox.setBounds(266, 463, 71, 23);
        frame.getContentPane().add(whisperCheckBox);

        onlineUserNumberPane = new JTextPane();
        onlineUserNumberPane.setForeground(new Color(105, 105, 105));
        onlineUserNumberPane.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        onlineUserNumberPane.setText("未连接服务器");
        onlineUserNumberPane.setBackground(new Color(143, 188, 143));
        onlineUserNumberPane.setBounds(6, 523, 65, 16);
        frame.getContentPane().add(onlineUserNumberPane);

        fileSendButton = new JButton("发送文件");
        fileSendButton.setBounds(343, 462, 101, 29);
        frame.getContentPane().add(fileSendButton);
        frame.setBounds(500, 200, 450, 573);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileSendButton.setEnabled(false);
        fileSendButton.addActionListener(e -> {
            selectedFile = chooseFile();
            if (selectedFile == null) {
                displayInfo("未选择文件");
            } else {
                try {
                    responseQueue.put(new Response(userName, userName, SEND_FILE_ACCEPT_REQUEST));
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
    }

    // 发送文件选择
    private File chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("请选择要发送的文件");
        int ret = chooser.showOpenDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            displayInfo("需要发送的文件路径：\n         " + file.getAbsolutePath());
            displayInfo("等待对方响应……");
            return file;
        }
        return null;
    }

    // 用户设置弹窗
    private class UserSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private final JTextField userNameTextField;

        public UserSet(JFrame owner) {
            super(owner, "用户设置", true);
            setBounds(100, 100, 293, 196);
            setLocationRelativeTo(owner);
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
                userNameTextField.setText("aaa");
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

    // 连接设置弹窗
    private class ConnectionSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private final JTextField ipTextField;
        private final JTextField portTextField;

        public ConnectionSet(JFrame owner) {
            super(owner, "连接设置", true);
            setBounds(100, 100, 306, 250);
            setLocationRelativeTo(owner);
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
        infoDisplayArea.setCaretPosition(infoDisplayArea.getText().length());
    }

    // 刷新最后一条消息
    private synchronized void displayInfo(String info, boolean refresh) {
        if (refresh) {

            infoList.removeLast();
        }
        displayInfo(info);
    }

    private class NetEngine implements Runnable {
        // handler线程池
        private ExecutorService handlerThreadPool;
        // 与服务器对接的输出流
        private ObjectOutputStream out;
        // 显示进度条需要的相关数据
        private int displayTotal;

        private int displayPart;
        // 临时文件存放目录
        private File tempFileDir;
        // 文件保存目录
        private File saveDirectory;
        // 文件发送对方名字
        private String fileTo;

        @Override
        public void run() {
            initRequestQueue();
            initHandlerThreadPool();
            runHandler();
            try {
                connectServer();
            } catch (IOException | ClassNotFoundException e) {
                if (e instanceof IOException) {
                    displayInfo("无法连接服务器，请等待服务器启动后重试！");
                }
                e.printStackTrace();
            }
        }

        private void initRequestQueue() {
            responseQueue = new LinkedBlockingQueue<>();
            displayInfo("响应队列初始化完成");
        }

        private void initHandlerThreadPool() {
            handlerThreadPool = Executors.newFixedThreadPool(2);
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
            displayInfo("连接服务器……");
            Socket socket = new Socket(ip, port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            // 发送登录消息
            displayInfo("登录中……");
            synchronized (Handler.class) {
                out.writeObject(new Request(userName, "server", LOGIN));
            }
            // 接受响应消息
            Response loginResponse = (Response) in.readObject();
            System.out.println(loginResponse);
            // 用户名重复重置用户名，返回结束该线程
            if (loginResponse.getType() == REPEATED_USERNAME) {
                displayInfo("用户名重复，请更换用户名重新登录");
                userName = null;
                logInButton.setEnabled(false);
                userSetButton.setEnabled(true);
                return;
            }
            displayInfo("登录成功");
            frame.setTitle(userName);
            logInButton.setEnabled(false);
            logOutButton.setEnabled(true);
            msgSendButton.setEnabled(true);
            fileSendButton.setEnabled(true);

            // 启动侦听线程
            new Thread(() -> {
                while (true) {
                    try {
                        Response response = (Response) in.readObject();
                        if (response != null) {
                            System.out.println(response);
                            responseQueue.put(response);
                        }
                    } catch (IOException | InterruptedException | ClassNotFoundException e) {
                        e.printStackTrace();
                        if (e instanceof IOException) {
                            break;
                        }
                    }
                }
                displayInfo("已与服务器断开连接");
                msgSendButton.setEnabled(false);
                fileSendButton.setEnabled(false);
                logInButton.setEnabled(true);
                logOutButton.setEnabled(false);
                toUserComboBox.removeAllItems();
                onlineUserNumberPane.setText("未连接服务器");
            }).start();
        }

        private class Handler implements Runnable {
            private final Response response;

            public Handler(Response response) {
                this.response = response;
            }

            @Override
            public void run() {
                switch (response.getType()) {
                    // 用户目录更新
                    case NEW_USER_LIST:
                        updateUserList();
                        break;
                    // 客户端发送信息
                    case SEND_CLIENT_MSG:
                        try {
                            clientSendMsg();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 有新消息
                    case INCOMING_MSG:
                        try {
                            displayAndSendMsg(response, false);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 发送文件接受询问请求
                    case SEND_FILE_ACCEPT_REQUEST:
                        try {
                            sendFileAcceptRequest();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 询问是否接收文件
                    case ASK_FILE_ACCEPT:
                        try {
                            confirmAcceptFile();
                            break;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 对方拒绝文件接收
                    case TARGET_REFUSE_FILE:
                        selectedFile = null;
                        displayInfo(response.getFrom() + "拒绝接收文件");
                        break;
                    // 对方接收文件
                    case TARGET_ACCEPT_FILE:
                        try {
                            verifyAcceptFile();
                            break;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 验证失败
                    case VERIFICATION_FAILED:
                        displayInfo(response.getFrom() + "取消了文件传输");
                        break;
                    // 创建文件数据包
                    case CREATE_FILE_PACKAGE:
                        createFilePackage();
                        break;
                    // 文件数据包
                    case FILE_PACKAGE:
                        try {
                            displayProcess();
                            writeTempFile();
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 合并文件
                    case COMBINE_FILES:
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        displayInfo("合并文件中……");
                        combineFiles();
                        displayInfo("文件下载完成");
                        break;

                    case SEND_LOGOUT:
                        try {
                            logOut();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    case SERVICE_SHUTDOWN:
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out = null;
                        userSetButton.setEnabled(true);
                        connectSetButton.setEnabled(true);
                        logOutButton.setEnabled(true);
                        msgSendButton.setEnabled(false);
                        fileSendButton.setEnabled(false);

                }

            }

            private void logOut() throws IOException {
                synchronized (Handler.class) {
                    out.writeObject(new Request(userName, "server", LOGOUT));
                }
                out.close();
                out = null;
                onlineUserNumberPane.setText("未连接服务器");
            }

            // 清理临时文件
            private void cleanTemp() {
                File[] tempfiles = tempFileDir.listFiles();
                for (File tempfile : tempfiles) {
                    tempfile.delete();
                }
                tempFileDir.delete();
                displayTotal = 0;
                displayPart = 0;
                tempFileDir = null;
                saveDirectory = null;

            }

            // 合并文件
            private void combineFiles() {
                String fileName = response.getText();
                // 完整文件的路径
                File fullFile = new File(saveDirectory, fileName);
                byte[] b = new byte[1024];
                // 依次读取临时并写入完整文件
                for (int i = 1; i <= displayTotal; i++) {
                    String partFilePath = tempFileDir.getAbsolutePath() + File.separator + fileName + ".part" + i;
                    File partFile = new File(partFilePath);
                    displayInfo("正在合并第"+i+"个文件，共"+displayTotal+"个",true);
                    try (BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(fullFile, true));
                         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(partFile))) {
                        int len;
                        while ((len = bis.read(b)) != -1) {
                            bos.write(b, 0, len);
                            bos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                cleanTemp();
            }

            // 显示进度条
            private void displayProcess() {
                String[] args = response.getText().split(",");
                displayTotal = Integer.parseInt(args[2]);
                synchronized (Handler.class) {
                    displayPart++;
                }
                String process = "下载进度：[";
                double ratio = (double) displayPart / displayTotal;
                int finishedNumber = (int) (ratio * 10);
                for (int i = 0; i < finishedNumber; i++) {
                    process += "#";
                }
                for (int i = 0; i < 10 - finishedNumber; i++) {
                    process += "-";
                }
                process += "] ";
                double percentage = ratio * 100;
                process += String.format("%.1f", percentage);
                process += "%";
                displayInfo(process, true);

            }

            // 将文件数据包数据写入临时文件
            private void writeTempFile() throws InterruptedException {
                String[] args = response.getText().split(",");
                String fileName = args[0];
                int part = Integer.parseInt(args[1]);
                int total = Integer.parseInt(args[2]);
                tempFileDir = new File(saveDirectory, "temp");

                if (!tempFileDir.exists()) {//如果文件夹不存在
                    tempFileDir.mkdir();//创建文件夹
                }
                File tempFile = new File(tempFileDir, fileName + ".part" + part);

                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                    bos.write(response.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (part == total) {
                    responseQueue.put(new Response(userName, userName, COMBINE_FILES, fileName));
                }

            }

            // 创建文件数据包
            private void createFilePackage() {
                // 读取数据并封装
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(selectedFile))) {
                    String[] args = response.getText().split(",");
                    int part = Integer.parseInt(args[1]);
                    int total = Integer.parseInt(args[2]);
                    int length = Integer.parseInt(args[3]);
                    byte[] data = new byte[length];
                    bis.skip((long) (part - 1) * MAX_PACKAGE_SIZE);
                    bis.read(data, 0, length);

                    synchronized (Handler.class) {
                        out.writeObject(new Request(userName, fileTo, SEND_FILE_PACKAGE, response.getText(), data));
                        displayInfo("第" + part + "个数据包发送完毕，共" + total + "个", true);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            // 创建文件传输任务
            private void createFileTransferTask() throws InterruptedException {
                // 计算数据包数量及数据长度
                int packageCount = (int) (selectedFile.length() / MAX_PACKAGE_SIZE + 1);
                int finalSize = (int) (selectedFile.length() - (packageCount - 1) * MAX_PACKAGE_SIZE);
                for (int i = 1; i <= packageCount; i++) {
                    if (i < packageCount) {
                        String text = selectedFile.getName() + "," + i + "," + packageCount + "," + MAX_PACKAGE_SIZE;
                        responseQueue.put(new Response(userName, userName, CREATE_FILE_PACKAGE, text));
                    } else {
                        String text = selectedFile.getName() + "," + i + "," + packageCount + "," + finalSize;
                        responseQueue.put(new Response(userName, userName, CREATE_FILE_PACKAGE, text));
                    }
                }
            }

            // 验证文件名字与对方名字
            private void verifyAcceptFile() throws IOException, InterruptedException {
                String from = response.getFrom();
                int incomingVerificationCode = (from + response.getText()).hashCode();
                int correntVerificationCode = (fileTo + selectedFile.getName()).hashCode();
                if (incomingVerificationCode != correntVerificationCode) {
                    synchronized (Handler.class) {
                        out.writeObject(new Request(userName, from, SEND_VERIFICATION_FAILED));
                    }
                } else {
                    createFileTransferTask();
                }

            }

            // 确认接收文件对话框
            private void confirmAcceptFile() throws IOException, InterruptedException {
                String from = response.getFrom();
                String[] args = response.getText().split(",");
                String fileName = args[0];
                double fileSize = Integer.parseInt(args[1]);
                String sizeUnit;
                // 自适应容量单位
                if (fileSize < 1024) {
                    sizeUnit = "byte";
                } else {
                    fileSize /= 1024;
                    if (fileSize < 1024) {
                        sizeUnit = "kB";
                    } else {
                        fileSize /= 1024;
                        sizeUnit = "MB";
                    }
                }
                String displaySize = String.format("%.1f", fileSize);

                int opt = JOptionPane.showConfirmDialog(frame,
                        from + "请求向你发送文件【" + fileName + "】，文件大小为" +
                                displaySize + sizeUnit + "，是否接收？",
                        "文件接收", JOptionPane.YES_NO_OPTION);

                if (opt == JOptionPane.YES_OPTION) {
                    Thread.sleep(100);
                    JFileChooser dirChooser = new JFileChooser();
                    Thread.sleep(100);
                    dirChooser.setDialogTitle("请选择文件保存目录");
                    Thread.sleep(100);
                    dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    Thread.sleep(100);
                    int ret = dirChooser.showOpenDialog(frame);

                    // 获取文件保存目录
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        saveDirectory = dirChooser.getSelectedFile();
                        displayInfo("文件保存目录：\n         " + saveDirectory.getAbsolutePath());
                        displayInfo("");
                        Request request = new Request(userName, from, ACCEPT_FILE, fileName);
                        synchronized (Handler.class) {
                            out.writeObject(request);
                        }
                    } else {
                        Request request = new Request(userName, from, REFUSE_FILE, fileName);
                        synchronized (Handler.class) {
                            out.writeObject(request);
                        }
                    }
                } else {
                    Request request = new Request(userName, from, REFUSE_FILE, fileName);
                    synchronized (Handler.class) {
                        out.writeObject(request);
                    }
                }
            }

            // 发送文件接收询问
            private void sendFileAcceptRequest() throws IOException {
                fileTo = (String) toUserComboBox.getSelectedItem();
                if (fileTo.equals("所有人")) {
                    fileTo = null;
                    displayInfo("不能给所有人发送文件");
                } else {
                    String text = selectedFile.getName() + "," + selectedFile.length();
                    synchronized (Handler.class) {
                        out.writeObject(new Request(userName, fileTo, SEND_ASK_FILE_ACCEPT, text));
                    }
                }
            }

            // 客户端向外发送信息
            private void clientSendMsg() throws IOException {
                String toUser = (String) toUserComboBox.getSelectedItem();
                boolean whisper = whisperCheckBox.isSelected();
                String msg = msgInputField.getText();
                if (msg.equals("")) {
                    displayInfo("消息不能为空！");
                } else if (toUser.equals("所有人") && whisper) {
                    displayInfo("不能向所有人发悄悄话！");
                } else {

                    displayAndSendMsg(
                            new Response(userName, toUser, OUTGOING_MSG,
                                    (String) expressionComboBox.getSelectedItem(),
                                    whisperCheckBox.isSelected(), msg), true);

                }
                msgInputField.setText("");
            }

            // 显示新信息，可选发送
            private void displayAndSendMsg(Response response, boolean send) throws IOException {
                String from = response.getFrom();
                String to = response.getTo();
                String expression = response.getExpression();
                boolean whisper = response.isWhisper();
                String msg = response.getText();

                String tempFrom = from.equals(userName) ? "你" : from;

                String s = (whisper ? "*悄悄话*" : "") +
                        (tempFrom.equals("server") ? "服务器" : tempFrom) +
                        (expression.equals("") ? "" : (expression + "地")) +
                        "对" +
                        (to.equals(userName) ? "你" : to)
                        + "说：" + msg;
                displayInfo(s);

                if (send) {
                    synchronized (Handler.class) {
                        out.writeObject(new Request(from, to, SEND_MSG, expression, whisper, msg));
                    }
                }
            }

            // 刷新用户列表
            private void updateUserList() {
                // 字符串解析
                String[] users = response.getText().split(",");
                onlineUserNumberPane.setText(users.length + "人在线");
                toUserComboBox.removeAllItems();
                toUserComboBox.addItem("所有人");
                for (String user : users) {
                    if (!user.equals(userName)) {
                        toUserComboBox.addItem(user);
                    }
                }
            }
        }

    }

}
