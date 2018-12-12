package com.oracle.Chat;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.oracle.Chat.model.message;
import com.oracle.Chat.model.user;

public class loginFrame extends JFrame{
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket s;
	private JLabel UsernameLabel,PasswordLabel;
	private JTextField Username;
	private JPasswordField Password;
	private JRadioButton Autologin,RememberPassword;
	private JButton Login,Register;
	private MyMouseListener allmouselistener;
	private MyActionListener allactionlistener;
	private ImageIcon background;
	private JPanel imagePanel;
	
	public loginFrame() {
		try {
			s=new Socket("localhost",8888);
			out=new ObjectOutputStream(s.getOutputStream());
			in=new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(400,300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("光迅2018");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		allmouselistener=new MyMouseListener();
		allactionlistener=new MyActionListener();
		//设置背景
		background=new ImageIcon("picture/loginbackground.jpg");
		JLabel backgrounelabel=new JLabel(background);
		backgrounelabel.setBounds(0, 0, background.getIconWidth(),
			    background.getIconHeight());
		
		imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(null);
		this.getLayeredPane().add(backgrounelabel,new Integer(Integer.MIN_VALUE));
		
		initComponent();
		this.paintComponents(this.getGraphics());
	}
	
	public void initComponent() {
		Container cp=this.getContentPane();  
		((JPanel)cp).setOpaque(false);
		
		Font font=new Font("楷体", Font.BOLD, 23);
		
		UsernameLabel=new JLabel("光迅账户:");
		UsernameLabel.setBounds(80,90,150,30);
		UsernameLabel.setFont(font);
		UsernameLabel.setForeground(Color.WHITE);
		cp.add(UsernameLabel);
		
		Username=new JTextField("123");
		Username.addMouseListener(allmouselistener);
		Username.setBounds(200,90,120,25);
		Username.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					//获取界面登录信息
					String userzhanghu=Username.getText();
					String password=Password.getText();
					user loginuser=new user();
					loginuser.setZhanghu(userzhanghu);
					loginuser.setPassword(password);
					//将信息封装发送给服务器
					message loginmessage=new message();
					loginmessage.setFrom(loginuser);
					loginmessage.setType("login");
					try {
						out.writeObject(loginmessage);
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//获取服务器反馈
					try {
						message loginresult=(message)in.readObject();
						if(loginresult.getContent().equals("success")) {
//							System.out.println(loginresult.getFrom());
//							System.out.println(loginresult.getAllUser());
//							loginresult.getFrom().setStatus(true);
							MainFrame main=new MainFrame(loginresult.getFrom(),loginresult.getAllUser(),out,in);
							main.setVisible(true);
							loginFrame.this.dispose();
						}
						else if(loginresult.getContent().equals("false")){
							JOptionPane.showMessageDialog(loginFrame.this, "温馨提斯：登录失败！", "登录s结果", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		cp.add(Username);
		
		PasswordLabel=new JLabel("光迅密码:");
		PasswordLabel.setBounds(80,115,150,30);
		PasswordLabel.setFont(font);
		PasswordLabel.setForeground(Color.WHITE);
		cp.add(PasswordLabel);
		
		Password =new JPasswordField("123");
		Password.addMouseListener(allmouselistener);
		Password.setBounds(200,115,120,25);
		Password.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					//获取界面登录信息
					String userzhanghu=Username.getText();
					String password=Password.getText();
					user loginuser=new user();
					loginuser.setZhanghu(userzhanghu);
					loginuser.setPassword(password);
					//将信息封装发送给服务器
					message loginmessage=new message();
					loginmessage.setFrom(loginuser);
					loginmessage.setType("login");
					try {
						out.writeObject(loginmessage);
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//获取服务器反馈
					try {
						message loginresult=(message)in.readObject();
						if(loginresult.getContent().equals("success")) {
							System.out.println(loginresult.getFrom());
							System.out.println(loginresult.getAllUser());
							MainFrame main=new MainFrame(loginresult.getFrom(),loginresult.getAllUser(),out,in);
							main.setVisible(true);
							loginFrame.this.dispose();
						}
						else if(loginresult.getContent().equals("false")){
							JOptionPane.showMessageDialog(loginFrame.this, "温馨提斯：登录失败！", "登录结果", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		cp.add(Password);
		
		Autologin=new JRadioButton("自动登录");
		Autologin.addMouseListener(allmouselistener);
		Autologin.setBounds(100,145,80,25);
		Autologin.setContentAreaFilled(false);
		cp.add(Autologin);
		
		RememberPassword=new JRadioButton("记住密码");
		RememberPassword.addMouseListener(allmouselistener);
		RememberPassword.setBounds(200,145,80,25);
		RememberPassword.setContentAreaFilled(false);
		cp.add(RememberPassword);
		
		Register=new JButton("注册账户");
		Register.addMouseListener(allmouselistener);
		Register.addActionListener(allactionlistener);
		Register.setBounds(100,170,90,25);
		cp.add(Register);
		
		Login=new JButton("登录账户");
		Login.addMouseListener(allmouselistener);
		Login.addActionListener(allactionlistener);
		Login.setBounds(200,170,90,25);
		cp.add(Login);
		
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame frame = new loginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	class MyMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getSource()==Login) {
				Login.setForeground(Color.RED);
			}
			else if(e.getSource()==Register) {
				Register.setForeground(Color.RED);
			}
			else if(e.getSource()==Username) {
				Username.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
			else if(e.getSource()==Password) {
				Password.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
			else if(e.getSource()==Autologin) {
				Autologin.setForeground(Color.RED);
			}
			else if(e.getSource()==RememberPassword) {
				RememberPassword.setForeground(Color.RED);
			}
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if(e.getSource()==Login) {
				Login.setForeground(Color.BLACK);
			}
			else if(e.getSource()==Register) {
				Register.setForeground(Color.BLACK);
			}
			else if(e.getSource()==Username) {
				Username.setBorder(BorderFactory.createLineBorder(new Color(178, 178, 178)));
			}
			else if(e.getSource()==Password) {
				Password.setBorder(BorderFactory.createLineBorder(new Color(178, 178, 178)));
			}
			else if(e.getSource()==Autologin) {
				Autologin.setForeground(Color.BLACK);
			}
			else if(e.getSource()==RememberPassword) {
				RememberPassword.setForeground(Color.BLACK);
			}
		}
	}
	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==Login) {
				//获取界面登录信息
				String userzhanghu=Username.getText();
				String password=Password.getText();
				user loginuser=new user();
				loginuser.setZhanghu(userzhanghu);
				loginuser.setPassword(password);
				//将信息封装发送给服务器
				message loginmessage=new message();
				loginmessage.setFrom(loginuser);
				loginmessage.setType("login");
				try {
					out.writeObject(loginmessage);
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//获取服务器反馈
				try {
					message loginresult=(message)in.readObject();
					if(loginresult.getContent().equals("success")) {
						System.out.println(loginresult.getFrom());
						System.out.println(loginresult.getAllUser());
						MainFrame main=new MainFrame(loginresult.getFrom(),loginresult.getAllUser(),out,in);
						main.setVisible(true);
						loginFrame.this.dispose();
					}
					else if(loginresult.getContent().equals("false")){
						JOptionPane.showMessageDialog(loginFrame.this, "温馨提斯：登录失败！", "登录s结果", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else if(e.getSource()==Register) {
				RegisterFrame register=new RegisterFrame(in,out,loginFrame.this);
				loginFrame.this.setVisible(false);
			}
		}
		
	}
}
