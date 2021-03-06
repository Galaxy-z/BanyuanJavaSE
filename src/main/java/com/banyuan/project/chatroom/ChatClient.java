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
        frame.setTitle("?????????");
        frame.getContentPane().setBackground(new Color(143, 188, 143));
        frame.getContentPane().setLayout(null);
        frame.setLocation(500, 300);

        userSetButton = new JButton("????????????");
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

        connectSetButton = new JButton("????????????");
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

        logInButton = new JButton("??????");
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

        JButton quitButton = new JButton("??????");
        quitButton.setForeground(new Color(250, 128, 114));
        quitButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        quitButton.setBounds(362, 6, 82, 35);
        frame.getContentPane().add(quitButton);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        logOutButton = new JButton("??????");
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

        JLabel msgSendLabel = new JLabel("???????????????");
        msgSendLabel.setBounds(6, 495, 65, 16);
        frame.getContentPane().add(msgSendLabel);

        msgSendButton = new JButton("??????");
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

        JLabel lblNewLabel_1 = new JLabel("????????????");
        lblNewLabel_1.setBounds(6, 467, 61, 16);
        frame.getContentPane().add(lblNewLabel_1);

        toUserComboBox = new JComboBox();
        toUserComboBox.setBounds(68, 463, 94, 27);
        frame.getContentPane().add(toUserComboBox);

        JLabel expressionLabel = new JLabel("?????????");
        expressionLabel.setBounds(163, 467, 44, 16);
        frame.getContentPane().add(expressionLabel);

        expressionComboBox = new JComboBox<String>();
        expressionComboBox.setBounds(193, 463, 77, 27);
        frame.getContentPane().add(expressionComboBox);
        expressionComboBox.addItem("");
        expressionComboBox.addItem("??????");
        expressionComboBox.addItem("??????");
        expressionComboBox.addItem("??????");
        expressionComboBox.addItem("??????");
        expressionComboBox.addItem("??????");
        expressionComboBox.addItem("??????");

        whisperCheckBox = new JCheckBox("?????????");
        whisperCheckBox.setBounds(266, 463, 71, 23);
        frame.getContentPane().add(whisperCheckBox);

        onlineUserNumberPane = new JTextPane();
        onlineUserNumberPane.setForeground(new Color(105, 105, 105));
        onlineUserNumberPane.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        onlineUserNumberPane.setText("??????????????????");
        onlineUserNumberPane.setBackground(new Color(143, 188, 143));
        onlineUserNumberPane.setBounds(6, 523, 65, 16);
        frame.getContentPane().add(onlineUserNumberPane);

        fileSendButton = new JButton("????????????");
        fileSendButton.setBounds(343, 462, 101, 29);
        frame.getContentPane().add(fileSendButton);
        frame.setBounds(500, 200, 450, 573);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileSendButton.setEnabled(false);
        fileSendButton.addActionListener(e -> {
            selectedFile = chooseFile();
            if (selectedFile == null) {
                displayInfo("???????????????");
            } else {
                try {
                    responseQueue.put(new Response(userName, userName, SEND_FILE_ACCEPT_REQUEST));
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
    }

    // ??????????????????
    private File chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("???????????????????????????");
        int ret = chooser.showOpenDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            displayInfo("??????????????????????????????\n         " + file.getAbsolutePath());
            displayInfo("????????????????????????");
            return file;
        }
        return null;
    }

    // ??????????????????
    private class UserSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private final JTextField userNameTextField;

        public UserSet(JFrame owner) {
            super(owner, "????????????", true);
            setBounds(100, 100, 293, 196);
            setLocationRelativeTo(owner);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel("?????????????????????");
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
                        displayInfo("?????????????????????" + userName);
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

    // ??????????????????
    private class ConnectionSet extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private final JTextField ipTextField;
        private final JTextField portTextField;

        public ConnectionSet(JFrame owner) {
            super(owner, "????????????", true);
            setBounds(100, 100, 306, 250);
            setLocationRelativeTo(owner);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel("??????????????????ip?????????");
                lblNewLabel.setBounds(24, 55, 159, 16);
                contentPanel.add(lblNewLabel);
            }
            {
                JLabel lblNewLabel = new JLabel("??????????????????????????????");
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
                        displayInfo("?????????ip????????????" + ip);
                        displayInfo("????????????????????????" + port);
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

    // ???????????????????????????
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

    // ????????????????????????
    private synchronized void displayInfo(String info, boolean refresh) {
        if (refresh) {

            infoList.removeLast();
        }
        displayInfo(info);
    }

    private class NetEngine implements Runnable {
        // handler?????????
        private ExecutorService handlerThreadPool;
        // ??????????????????????????????
        private ObjectOutputStream out;
        // ????????????????????????????????????
        private int displayTotal;

        private int displayPart;
        // ????????????????????????
        private File tempFileDir;
        // ??????????????????
        private File saveDirectory;
        // ????????????????????????
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
                    displayInfo("????????????????????????????????????????????????????????????");
                    userSetButton.setEnabled(true);
                    connectSetButton.setEnabled(true);
                }
                e.printStackTrace();
            }
        }

        private void initRequestQueue() {
            responseQueue = new LinkedBlockingQueue<>();
            displayInfo("???????????????????????????");
        }

        private void initHandlerThreadPool() {
            handlerThreadPool = Executors.newFixedThreadPool(2);
            displayInfo("????????????????????????????????????");
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
            displayInfo("????????????????????????????????????????????????");

        }

        private void connectServer() throws IOException, ClassNotFoundException {
            displayInfo("?????????????????????");
            Socket socket = new Socket(ip, port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            // ??????????????????
            displayInfo("???????????????");

                out.writeObject(new Request(userName, "server", LOGIN));

            // ??????????????????
            Response loginResponse = (Response) in.readObject();
            System.out.println(loginResponse);
            // ??????????????????????????????????????????????????????
            if (loginResponse.getType() == REPEATED_USERNAME) {
                displayInfo("????????????????????????????????????????????????");
                userName = null;
                logInButton.setEnabled(false);
                userSetButton.setEnabled(true);
                return;
            }
            displayInfo("????????????");
            frame.setTitle(userName);
            logInButton.setEnabled(false);
            logOutButton.setEnabled(true);
            msgSendButton.setEnabled(true);
            fileSendButton.setEnabled(true);

            // ??????????????????
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
                displayInfo("???????????????????????????");
                msgSendButton.setEnabled(false);
                fileSendButton.setEnabled(false);
                logInButton.setEnabled(true);
                logOutButton.setEnabled(false);
                toUserComboBox.removeAllItems();
                onlineUserNumberPane.setText("??????????????????");
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
                    // ??????????????????
                    case NEW_USER_LIST:
                        updateUserList();
                        break;
                    // ?????????????????????
                    case SEND_CLIENT_MSG:
                        try {
                            clientSendMsg();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    // ????????????
                    case INCOMING_MSG:
                        try {
                            displayAndSendMsg(response, false);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    // ??????????????????????????????
                    case SEND_FILE_ACCEPT_REQUEST:
                        try {
                            sendFileAcceptRequest();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    // ????????????????????????
                    case ASK_FILE_ACCEPT:
                        try {
                            confirmAcceptFile();
                            break;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    // ????????????????????????
                    case TARGET_REFUSE_FILE:
                        selectedFile = null;
                        displayInfo(response.getFrom() + "??????????????????");
                        break;
                    // ??????????????????
                    case TARGET_ACCEPT_FILE:
                        try {
                            verifyAcceptFile();
                            break;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    // ????????????
                    case VERIFICATION_FAILED:
                        displayInfo(response.getFrom() + "?????????????????????");
                        break;
                    // ?????????????????????
                    case CREATE_FILE_PACKAGE:
                        createFilePackage();
                        break;
                    // ???????????????
                    case FILE_PACKAGE:
                        try {
                            displayProcess();
                            writeTempFile();
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    // ????????????
                    case COMBINE_FILES:
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        displayInfo("?????????????????????");
                        combineFiles();
                        displayInfo("??????????????????");
                        break;
                    // ??????????????????
                    case SEND_LOGOUT:
                        try {
                            logOut();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    // ???????????????
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

            // ??????
            private void logOut() throws IOException {
                synchronized (Handler.class) {
                    out.writeObject(new Request(userName, "server", LOGOUT));
                }
                out.close();
                out = null;
                onlineUserNumberPane.setText("??????????????????");
            }

            // ??????????????????
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

            // ????????????
            private void combineFiles() {
                String fileName = response.getText();
                // ?????????????????????
                File fullFile = new File(saveDirectory, fileName);
                byte[] b = new byte[1024];
                // ???????????????????????????????????????
                for (int i = 1; i <= displayTotal; i++) {
                    String partFilePath = tempFileDir.getAbsolutePath() + File.separator + fileName + ".part" + i;
                    File partFile = new File(partFilePath);
                    displayInfo("???????????????"+i+"???????????????"+displayTotal+"???",true);
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

            // ???????????????
            private void displayProcess() {
                String[] args = response.getText().split(",");
                displayTotal = Integer.parseInt(args[2]);
                synchronized (Handler.class) {
                    displayPart++;
                }
                String process = "???????????????[";
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

            // ??????????????????????????????????????????
            private void writeTempFile() throws InterruptedException {
                String[] args = response.getText().split(",");
                String fileName = args[0];
                int part = Integer.parseInt(args[1]);
                int total = Integer.parseInt(args[2]);
                tempFileDir = new File(saveDirectory, "temp");

                if (!tempFileDir.exists()) {//????????????????????????
                    tempFileDir.mkdir();//???????????????
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

            // ?????????????????????
            private void createFilePackage() {
                // ?????????????????????
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
                        displayInfo("???" + part + "??????????????????????????????" + total + "???", true);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            // ????????????????????????
            private void createFileTransferTask() throws InterruptedException {
                // ????????????????????????????????????
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

            // ?????????????????????????????????
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

            // ???????????????????????????
            private void confirmAcceptFile() throws IOException, InterruptedException {
                String from = response.getFrom();
                String[] args = response.getText().split(",");
                String fileName = args[0];
                double fileSize = Integer.parseInt(args[1]);
                String sizeUnit;
                // ?????????????????????
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
                        from + "???????????????????????????" + fileName + "?????????????????????" +
                                displaySize + sizeUnit + "??????????????????",
                        "????????????", JOptionPane.YES_NO_OPTION);

                if (opt == JOptionPane.YES_OPTION) {
                    Thread.sleep(100);
                    JFileChooser dirChooser = new JFileChooser();
                    Thread.sleep(100);
                    dirChooser.setDialogTitle("???????????????????????????");
                    Thread.sleep(100);
                    dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    Thread.sleep(100);
                    int ret = dirChooser.showOpenDialog(frame);

                    // ????????????????????????
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        saveDirectory = dirChooser.getSelectedFile();
                        displayInfo("?????????????????????\n         " + saveDirectory.getAbsolutePath());
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

            // ????????????????????????
            private void sendFileAcceptRequest() throws IOException {
                fileTo = (String) toUserComboBox.getSelectedItem();
                if (fileTo.equals("?????????")) {
                    fileTo = null;
                    displayInfo("??????????????????????????????");
                } else {
                    String text = selectedFile.getName() + "," + selectedFile.length();
                    synchronized (Handler.class) {
                        out.writeObject(new Request(userName, fileTo, SEND_ASK_FILE_ACCEPT, text));
                    }
                }
            }

            // ???????????????????????????
            private void clientSendMsg() throws IOException {
                String toUser = (String) toUserComboBox.getSelectedItem();
                boolean whisper = whisperCheckBox.isSelected();
                String msg = msgInputField.getText();
                if (msg.trim().equals("")) {
                    displayInfo("?????????????????????");
                } else if (toUser.equals("?????????") && whisper) {
                    displayInfo("?????????????????????????????????");
                } else {

                    displayAndSendMsg(
                            new Response(userName, toUser, OUTGOING_MSG,
                                    (String) expressionComboBox.getSelectedItem(),
                                    whisperCheckBox.isSelected(), msg), true);

                }
                msgInputField.setText("");
            }

            // ??????????????????????????????
            private void displayAndSendMsg(Response response, boolean send) throws IOException {
                String from = response.getFrom();
                String to = response.getTo();
                String expression = response.getExpression();
                boolean whisper = response.isWhisper();
                String msg = response.getText();

                String tempFrom = from.equals(userName) ? "???" : from;

                String s = (whisper ? "*?????????*" : "") +
                        (tempFrom.equals("server") ? "?????????" : tempFrom) +
                        (expression.equals("") ? "" : (expression + "???")) +
                        "???" +
                        (to.equals(userName) ? "???" : to)
                        + "??????" + msg;
                displayInfo(s);

                if (send) {
                    synchronized (Handler.class) {
                        out.writeObject(new Request(from, to, SEND_MSG, expression, whisper, msg));
                    }
                }
            }

            // ??????????????????
            private void updateUserList() {
                // ???????????????
                String[] users = response.getText().split(",");
                onlineUserNumberPane.setText(users.length + "?????????");
                toUserComboBox.removeAllItems();
                toUserComboBox.addItem("?????????");
                for (String user : users) {
                    if (!user.equals(userName)) {
                        toUserComboBox.addItem(user);
                    }
                }
            }
        }

    }

}
