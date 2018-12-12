package com.oracle.Chat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.oracle.Chat.model.message;
import com.oracle.Chat.model.user;

public class RegisterFrame extends JFrame {
	private loginFrame loginfram;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private JLabel hengfu, usernamelabel, zhanghulabel, passwordlabel, repasswordlabel, touxianglabel;
	private JTextField uesername, zhanghu;
	private JPasswordField password, repassword;
	private JButton check, register, touxiangbutton;
	private JComboBox touxiang; // 定义下拉框
	private MyMouseListener allmouseentry;
	private static boolean registerstatues = false;
	private ImageIcon background;
	private JPanel imagePanel;
	
	public RegisterFrame(ObjectInputStream in, ObjectOutputStream out, loginFrame loginfram) {
		this.loginfram = loginfram;
		this.in = in;
		this.out = out;
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("光迅2018");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		allmouseentry = new MyMouseListener();
		
		//设置背景
		background=new ImageIcon("picture/register2.jpg");
		JLabel backgrounelabel=new JLabel(background);
		backgrounelabel.setBounds(0, 0, background.getIconWidth(),
			    background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(null);
		this.getLayeredPane().add(backgrounelabel,new Integer(Integer.MIN_VALUE));
		
		Font font = new Font("楷体", Font.BOLD, 25);
		Font font2 = new Font("宋体", Font.BOLD, 22);

		hengfu = new JLabel("欢迎注册光迅，请填写一下信息：");
		hengfu.setBounds(80, 20, 400, 30);
		hengfu.setFont(font);
		hengfu.setForeground(Color.BLUE);
		this.add(hengfu);

		usernamelabel = new JLabel("光迅名称:");
		usernamelabel.setBounds(80, 80, 120, 25);
		usernamelabel.setFont(font2);
		usernamelabel.setForeground(Color.GRAY);
		this.add(usernamelabel);

		uesername = new JTextField("zxg");
		uesername.setBounds(200, 80, 120, 25);
		uesername.addMouseListener(allmouseentry);
		this.add(uesername);

		zhanghulabel = new JLabel("光迅账户:");
		zhanghulabel.setBounds(80, 120, 120, 25);
		zhanghulabel.setFont(font2);
		zhanghulabel.setForeground(Color.GRAY);
		this.add(zhanghulabel);

		zhanghu = new JTextField("123");
		zhanghu.setBounds(200, 120, 120, 25);
		zhanghu.addMouseListener(allmouseentry);
		this.add(zhanghu);

		passwordlabel = new JLabel("光迅密码:");
		passwordlabel.setBounds(80, 160, 120, 25);
		passwordlabel.setFont(font2);
		passwordlabel.setForeground(Color.WHITE);
		this.add(passwordlabel);

		password = new JPasswordField("123");
		password.setBounds(200, 160, 120, 25);
		password.addMouseListener(allmouseentry);
		this.add(password);

		repasswordlabel = new JLabel("确认密码:");
		repasswordlabel.setBounds(80, 200, 120, 25);
		repasswordlabel.setFont(font2);
		repasswordlabel.setForeground(Color.WHITE);
		this.add(repasswordlabel);

		repassword = new JPasswordField("123");
		repassword.setBounds(200, 200, 120, 25);
		repassword.addMouseListener(allmouseentry);
		this.add(repassword);

		check = new JButton("检查当前账户是否可用");
		check.setBounds(120, 240, 170, 25);
		check.addMouseListener(allmouseentry);
		check.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					String userzhanghu = zhanghu.getText();
					String username=uesername.getText();
					String userpassword=password.getText();
					String touxiangpath=(String)touxiang.getSelectedItem().toString();
					System.out.println(touxiangpath);
					user register = new user();
					register.setZhanghu(userzhanghu);
					register.setUsername(username);
					register.setPassword(userpassword);
					register.setTouxiangpath(touxiangpath);
					System.out.println(userzhanghu);

					message registermessage = new message();
					registermessage.setFrom(register);
					registermessage.setType("register");

					// 将注册信息发往服务器
					try {
						System.out.println(registermessage);
						out.writeObject(registermessage);
						out.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// 从服务器获取注册结果
					try {
						message registerresult = (message) in.readObject();
						System.out.println("等待注册结果");
						if (registerresult.getContent().equals("success")) {
							int yourChoide = JOptionPane.showConfirmDialog(RegisterFrame.this, "注册成功，是否立即登陆", "注册结果",
									JOptionPane.INFORMATION_MESSAGE);
							if (yourChoide == 0) {
								loginfram.setVisible(true);
								RegisterFrame.this.dispose();
							}
						}
						else if(registerresult.getContent().equals("false")){
							JOptionPane.showMessageDialog(RegisterFrame.this, "温馨提斯：注册失败！", "注册结果", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
		});
		this.add(check);

//		register = new JButton("注册");
//		register.setBounds(260, 240, 60, 25);
//		register.addMouseListener(allmouseentry);
//		this.add(register);

		touxianglabel = new JLabel("您可以在这里选择默认头像:");
		touxianglabel.setBounds(340, 80, 360, 25);
		this.add(touxianglabel);

		touxiang = new JComboBox<>();
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

		touxiang.setBounds(360, 140, 100, 80);
		this.add(touxiang);

		this.paintComponents(this.getGraphics());
	}

	class MyMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == uesername) {
				uesername.setBorder(BorderFactory.createLineBorder(Color.RED));
			} else if (e.getSource() == zhanghu) {
				zhanghu.setBorder(BorderFactory.createLineBorder(Color.RED));
			} else if (e.getSource() == password) {
				password.setBorder(BorderFactory.createLineBorder(Color.RED));
			} else if (e.getSource() == repassword) {
				repassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			} else if (e.getSource() == check) {
				check.setForeground(Color.RED);
			} else if (e.getSource() == register) {
				register.setForeground(Color.RED);
			} else if (e.getSource() == touxiangbutton) {
				touxiangbutton.setForeground(Color.RED);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == uesername) {
				uesername.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			} else if (e.getSource() == zhanghu) {
				zhanghu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			} else if (e.getSource() == password) {
				password.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			} else if (e.getSource() == repassword) {
				repassword.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			} else if (e.getSource() == check) {
				check.setForeground(Color.BLACK);
			} else if (e.getSource() == register) {
				register.setForeground(Color.BLACK);
			} else if (e.getSource() == touxiangbutton) {
				touxiangbutton.setForeground(Color.BLACK);
			}
		}
	}

}
