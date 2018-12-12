package com.oracle.Chat;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.oracle.Chat.model.message;
import com.oracle.Chat.model.user;
import com.oracle.Chat.MainFrame.*;
import javax.swing.JScrollBar;

public class TeamChat extends JFrame {

	private JPanel contentPane;
	private user logineduser;
	private List<user> allusers;
	private String teamName;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private JTextArea viewArea;
	private ImageIcon background;
	private JPanel imagePanel;

	public JTextArea getViewArea() {
		return viewArea;
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TeamChat frame = new TeamChat();
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
	public TeamChat(user loginuser, List<user> alluser, String teamname, ObjectOutputStream out, ObjectInputStream in) {
		this.logineduser = loginuser;
		this.allusers = alluser;
		this.teamName = teamname;
		this.out = out;
		this.in = in;
		this.viewArea = new JTextArea();
		

		// ���ñ���ͼƬ
		background = new ImageIcon("picture/register.jpg");
		JLabel backgrounelabel = new JLabel(background);
		backgrounelabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());

		imagePanel = (JPanel) this.getContentPane();
		imagePanel.setLayout(null);
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(backgrounelabel, new Integer(Integer.MIN_VALUE));

		Container cp = this.getContentPane();
		((JPanel) cp).setOpaque(false);

		// �ñ������
		this.setFont(new Font("System", Font.PLAIN, 14));
		Font f = this.getFont();
		FontMetrics fm = this.getFontMetrics(f);
		int x = fm.stringWidth(teamname);
		int y = fm.stringWidth(" ");
		int z = this.getWidth() / 2 - (x / 2);
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		this.setTitle(pad + teamname);// Ⱥ������

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setOpaque(false);
		scrollBar_1.setBounds(434, 235, 17, 143);
		contentPane.add(scrollBar_1);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setOpaque(false);
		scrollBar.setBounds(432, 0, 17, 225);
		contentPane.add(scrollBar);
		scrollPane.setBounds(0, 0, 451, 225);
		contentPane.add(scrollPane);

//		JTextArea viewArea = new JTextArea(); //��ʾ��
		viewArea.setEnabled(false);
		viewArea.setOpaque(false);
		scrollPane.setViewportView(viewArea);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		scrollPane_1.setBounds(0, 235, 450, 145);
		contentPane.add(scrollPane_1);

		JTextArea writeArea = new JTextArea(); // ��д��
		writeArea.setOpaque(false);
		scrollPane_1.setViewportView(writeArea);
		writeArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					// ��ȡ��д��������
					String sendmessage = writeArea.getText();
					// ����д�����
					writeArea.setText(null);
					writeArea.setCaretPosition(0);

					// ��������ʾ����ʾ��
					viewArea.append(logineduser.getUsername() + "\t" + new Date().toLocaleString() + "\r\n"
							+ sendmessage + "\r\n\r\n");

					// ��װ��Ϣ
					message whillesend = new message();
					whillesend.setContent(sendmessage);
					whillesend.setFrom(logineduser);
//					user touser=new user();
//					touser.setUsername(frienduser.getUsername());
					user team = new user();
					team.setUsername(teamname);
					whillesend.setTo(team);
					whillesend.setType("teammessage");
					// ������Ϣ
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
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					TeamChat.this.setVisible(false);
				}
			}
		});

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setOpaque(false);
		scrollPane_2.getViewport().setOpaque(false);
		scrollPane_2.setBounds(458, 0, 176, 411);
		contentPane.add(scrollPane_2);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(teamname); // �Ҳ��Ա��Ϣ
		JTree tree = new JTree(root);
		tree.setOpaque(false);
		scrollPane_2.setViewportView(tree);
		for (int i = 0; i < alluser.size(); i++) {
			MutableTreeNode oneUser = new DefaultMutableTreeNode(alluser.get(i).getUsername());
			root.add(oneUser);
		}
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == 1) {
					if (tree.getSelectionPath().getPathCount() == 2) {

						String username = tree.getSelectionPath().getLastPathComponent().toString();

						if (MainFrame.allChatWindows.containsKey(username)) {
							MainFrame.allChatWindows.get(username).setVisible(true);// ֮ǰ�򿪹�������ѵ����촰�ڣ���ʱֻ�ǴӼ���������ó���Ȼ�����setvisiable���������������ʾ
						} else {
							System.out.println("���ڳ��Դ������촰��");
							for (int i = 0; i < allusers.size(); i++) {
								if (allusers.get(i).getUsername().equals(username)) {
									Chatwindow c = new Chatwindow(logineduser, allusers.get(i), out, in);
									c.setVisible(true);
									MainFrame.allChatWindows.put(username, c);// ����ǵ�һ�δ򿪣�����֮��������Ѻʹ��ڶ�Ӧ��ϵ���뵽������
								}
							}
						}
					}
				}
			}
		});

		JButton btnNewButton = new JButton("�ر�");
		btnNewButton.setBounds(202, 390, 93, 23);
		btnNewButton.setOpaque(false);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TeamChat.this.setVisible(false);
			}
		});
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("����");
		btnNewButton_1.setBounds(329, 390, 93, 23);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ȡ��д��������
				String sendmessage = writeArea.getText();
				// ����д�����
				writeArea.setText(null);
				writeArea.setCaretPosition(0);

				// ��������ʾ����ʾ��
				viewArea.append(logineduser.getUsername() + "\t" + new Date().toLocaleString() + "\r\n" + sendmessage
						+ "\r\n\r\n");
				viewArea.setSelectionStart(viewArea.getText().length());
				// ��װ��Ϣ
				message whillesend = new message();
				whillesend.setContent(sendmessage);
				whillesend.setFrom(logineduser);
//				user touser=new user();
//				touser.setUsername(frienduser.getUsername());
//				whillesend.setTo(frienduser);
				user team = new user();
				team.setUsername(teamname);
				whillesend.setTo(team);
				whillesend.setType("teammessage");
				// ������Ϣ
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

		this.paintAll(this.getGraphics());
	}
}
