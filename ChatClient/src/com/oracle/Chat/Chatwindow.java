package com.oracle.Chat;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import com.oracle.Chat.model.message;
import com.oracle.Chat.model.user;

public class Chatwindow extends JFrame {
	private JPanel contentPane;
	private user logineduesr;
	private user frienduser;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private JTextArea viewArea;
	private ImageIcon background;
	private JPanel imagePanel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		new Chatwindow();
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Chatwindow frame = new Chatwindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public JTextArea getViewArea() {
		return viewArea;
	}

	
	/**
	 * Create the frame.
	 */
	public Chatwindow(user loginuesr, user frienduser, ObjectOutputStream out, ObjectInputStream in) {
		this.logineduesr = loginuesr;
		this.frienduser = frienduser;
		this.out = out;
		this.in = in;

		this.setIconImage(Toolkit.getDefaultToolkit().createImage(frienduser.getTouxiangpath()));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 660, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);
		
		//设置背景图片
		background=new ImageIcon("picture/register.jpg");
		JLabel backgrounelabel=new JLabel(background);
		backgrounelabel.setBounds(0, 0, background.getIconWidth(),
			    background.getIconHeight());
		
		imagePanel=(JPanel)this.getContentPane();
		imagePanel.setLayout(null);
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(backgrounelabel,new Integer(Integer.MIN_VALUE));

		Container cp=this.getContentPane();  
		((JPanel)cp).setOpaque(false);
		// 让标题居中
		this.setFont(new Font("System", Font.PLAIN, 14));
		Font f = this.getFont();
		FontMetrics fm = this.getFontMetrics(f);
		int x = fm.stringWidth(frienduser.getUsername());
		int y = fm.stringWidth(" ");
		int z = this.getWidth() / 2 - (x / 2);
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		this.setTitle(pad + frienduser.getUsername());// 和XX聊天

		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(617, 0, 17, 215);
		scrollBar.setOpaque(false);
		contentPane.add(scrollBar);

		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(617, 233, 17, 122);
		scrollBar_1.setOpaque(false);
		contentPane.add(scrollBar_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 624, 227);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);

//		JTextArea  viewArea= new JTextArea(); //显示区
		viewArea = new JTextArea();
		viewArea.setEnabled(false);
		viewArea.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setViewportView(viewArea);

//		int height = 10;
//		Point p = new Point();
//		p.setLocation(0, viewArea.getLineCount() * height);
//		scrollPane.getViewport().setViewPosition(p);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 233, 624, 131);
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		contentPane.add(scrollPane_1);

		JTextArea writeArea = new JTextArea(); // 书写区
		scrollPane_1.setViewportView(writeArea);
		writeArea.setOpaque(false);
		writeArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					// 获取书写区的内容
					String sendmessage = writeArea.getText();
					// 将书写区清空
					writeArea.setText(null);
					writeArea.setCaretPosition(0);

					// 将内容显示到显示区
					viewArea.append(loginuesr.getUsername() + "\t" + new Date().toLocaleString() + "\r\n" + sendmessage
							+ "\r\n\r\n");
					
					// 封装信息
					message whillesend = new message();
					whillesend.setContent(sendmessage);
					whillesend.setFrom(loginuesr);
//					user touser=new user();
//					touser.setUsername(frienduser.getUsername());
					whillesend.setTo(frienduser);
					whillesend.setType("sendmessage");
					// 发送消息
					try {
						System.out.println(whillesend);
						out.writeObject(whillesend);
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ESCAPE) {
					Chatwindow.this.setVisible(false);
				}
			}
		});

		JButton btnNewButton = new JButton("关闭");
		btnNewButton.setBounds(400, 372, 93, 23);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Chatwindow.this.setVisible(false);
			}
		});
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("发送");
		btnNewButton_1.setBounds(515, 372, 93, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取书写区的内容
				String sendmessage = writeArea.getText();
				// 将书写区清空
				writeArea.setText(null);
				writeArea.setCaretPosition(0);

				// 将内容显示到显示区
				viewArea.append(loginuesr.getUsername() + "\t" + new Date().toLocaleString() + "\r\n" + sendmessage
						+ "\r\n\r\n");
				viewArea.setSelectionStart(viewArea.getText().length());
				// 封装信息
				message whillesend = new message();
				whillesend.setContent(sendmessage);
				whillesend.setFrom(loginuesr);
//				user touser=new user();
//				touser.setUsername(frienduser.getUsername());
				whillesend.setTo(frienduser);
				whillesend.setType("sendmessage");
				// 发送消息
				try {
					System.out.println(whillesend);
					out.writeObject(whillesend);
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		contentPane.add(btnNewButton_1);
		
		this.paintComponents(this.getGraphics());
	}

}
