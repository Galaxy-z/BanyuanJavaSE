import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class Client {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
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
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(176, 224, 230));
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("用户设置");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(6, 6, 82, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("连接设置");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(90, 6, 82, 35);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("登录");
		btnNewButton_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnNewButton_1_1.setBounds(210, 6, 60, 35);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JButton btnNewButton_1_3 = new JButton("退出");
		btnNewButton_1_3.setForeground(new Color(250, 128, 114));
		btnNewButton_1_3.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnNewButton_1_3.setBounds(362, 6, 82, 35);
		frame.getContentPane().add(btnNewButton_1_3);
		
		JButton btnNewButton_1_1_1 = new JButton("注销");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnNewButton_1_1_1.setBounds(272, 6, 60, 35);
		frame.getContentPane().add(btnNewButton_1_1_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 45, 438, 393);
		frame.getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(68, 490, 272, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("发送消息：");
		lblNewLabel.setBounds(6, 495, 65, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("发送");
		btnNewButton_2.setBounds(343, 490, 101, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("发送至：");
		lblNewLabel_1.setBounds(6, 467, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(68, 463, 82, 27);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1_1 = new JLabel("心情：");
		lblNewLabel_1_1.setBounds(162, 467, 44, 16);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(199, 463, 71, 27);
		frame.getContentPane().add(comboBox_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("悄悄话");
		chckbxNewCheckBox.setBounds(266, 463, 71, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JTextPane textPane = new JTextPane();
		textPane.setForeground(new Color(105, 105, 105));
		textPane.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textPane.setText("3人在线");
		textPane.setBackground(new Color(176, 224, 230));
		textPane.setBounds(6, 523, 65, 16);
		frame.getContentPane().add(textPane);
		
		JButton btnNewButton_2_1 = new JButton("发送文件");
		btnNewButton_2_1.setBounds(343, 462, 101, 29);
		frame.getContentPane().add(btnNewButton_2_1);
		frame.setBounds(100, 100, 450, 573);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
