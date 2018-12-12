package com.oracle.Chat;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.oracle.Chat.model.message;
import com.oracle.Chat.model.user;

public class modMessageFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private user loginuser;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ImageIcon background;
	private JPanel imagePanel;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					modMessageFrame frame = new modMessageFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public modMessageFrame(user logineduser,ObjectOutputStream out, ObjectInputStream in) {
		this.loginuser=logineduser;
		this.out=out;
		this.in=in;
		
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		//设置背景图片
		background=new ImageIcon("picture/main.jpg");
		JLabel backgrounelabel=new JLabel(background);
		backgrounelabel.setBounds(0, 0, background.getIconWidth(),
			    background.getIconHeight());
		
		imagePanel=(JPanel)this.getContentPane();
		imagePanel.setLayout(null);
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(backgrounelabel,new Integer(Integer.MIN_VALUE));

		Container cp=this.getContentPane();  
		((JPanel)cp).setOpaque(false);
		
		JLabel label = new JLabel("光迅名称:");
		label.setBounds(21, 44, 66, 15);
		contentPane.add(label);
		
		textField = new JTextField();  //光迅名称
		textField.setBounds(97, 41, 100, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField(); //光迅密码
		passwordField.setBounds(97, 87, 100, 21);
		contentPane.add(passwordField);
		
		JLabel label_1 = new JLabel("光迅密码:");
		label_1.setBounds(21, 90, 66, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("更改头像:");
		label_2.setBounds(274, 10, 78, 15);
		contentPane.add(label_2);
		
		JComboBox touxiang = new JComboBox();
		touxiang.setBounds(252, 35, 100, 80);
		ImageIcon icon1 = new ImageIcon("touxiang/1.jpg");
		ImageIcon icon2 = new ImageIcon("touxiang/2.jpg");
		ImageIcon icon3 = new ImageIcon("touxiang/3.jpg");
		ImageIcon icon4 = new ImageIcon("touxiang/4.jpg");
		ImageIcon icon5 = new ImageIcon("touxiang/5.jpg");
		ImageIcon icon6 = new ImageIcon("touxiang/6.jpg");
		ImageIcon icon7 = new ImageIcon("touxiang/7.jpg");
		ImageIcon icon8 = new ImageIcon("touxiang/8.jpg");
		touxiang.addItem(icon1);
		touxiang.addItem(icon2);
		touxiang.addItem(icon3);
		touxiang.addItem(icon4);
		touxiang.addItem(icon5);
		touxiang.addItem(icon6);
		touxiang.addItem(icon7);
		touxiang.addItem(icon8);
		contentPane.add(touxiang);
		
		JButton button = new JButton("确认修改");
		button.setBounds(160, 156, 105, 23);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=textField.getText();
				String password=passwordField.getText();
				String touxiangpath=(String)touxiang.getSelectedItem().toString();
				user moduser=new user();
				moduser.setUsername(username);
				moduser.setPassword(password);
				moduser.setTouxiangpath(touxiangpath);
				moduser.setZhanghu(logineduser.getZhanghu());
				message modmessage=new message();
				System.out.println(moduser);
				modmessage.setTo(moduser);
				modmessage.setType("modusermessage");
				try {
					out.writeObject(modmessage);
					out.flush();
					modMessageFrame.this.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(button);
		
		
	}
}
