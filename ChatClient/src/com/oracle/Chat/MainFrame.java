package com.oracle.Chat;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.oracle.Chat.model.message;
import com.oracle.Chat.model.user;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private user loginedUser;
	private List<user> allUsers;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public static HashMap<String, Chatwindow> allChatWindows;
	private HashMap<String, TeamChat> teamChatWindows;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有好友");
	private JTree tree = new JTree(root);
	private ImageIcon icon1;
	private JLabel touxiang, username;
	private ImageIcon background;
	private JPanel imagePanel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainFrame frame = new MainFrame();
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
	public MainFrame(user loginedUser, List<user> allUsers, ObjectOutputStream out, ObjectInputStream in) {
		this.allUsers = allUsers;
		this.loginedUser = loginedUser;
		this.out = out;
		this.in = in;
		allChatWindows = new HashMap<>();
		teamChatWindows = new HashMap<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 600);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);
		
		//设置背景图片
		background=new ImageIcon("picture/main3.jpg");
		JLabel backgrounelabel=new JLabel(background);
		backgrounelabel.setBounds(0, 0, background.getIconWidth(),
			    background.getIconHeight());
		
		imagePanel=(JPanel)this.getContentPane();
		imagePanel.setLayout(null);
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(backgrounelabel,new Integer(Integer.MIN_VALUE));
		
		Container cp=this.getContentPane();  
		((JPanel)cp).setOpaque(false);
		
		MainFrame.this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "退出系统", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					System.out.println(loginedUser.getZhanghu() + "下线");
					message exitmessage = new message();
					exitmessage.setFrom(loginedUser);
					exitmessage.setType("exitmessage");
					try {
						out.writeObject(exitmessage);
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
		});

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 284, 121);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setOpaque(false);

		icon1 = new ImageIcon(loginedUser.getTouxiangpath());
		touxiang = new JLabel(icon1);
		touxiang.setBounds(10, 10, 80, 80);
		touxiang.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		touxiang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modMessageFrame mod = new modMessageFrame(loginedUser, out, in);
				MainFrame.this.setVisible(false);
			}
		});
		panel.add(touxiang);

		Font font = new Font("楷体", Font.BOLD, 25);

		username = new JLabel(loginedUser.getUsername(), JLabel.CENTER);
		username.setBounds(96, 33, 200, 35);
		username.setFont(font);
		username.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modMessageFrame mod = new modMessageFrame(loginedUser, out, in);
				MainFrame.this.setVisible(false);
			}
		});
		panel.add(username);

		JPanel panel_1 = new JPanel(); // 下部分
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(0, 155, 284, 406);
		contentPane.add(panel_1);
		CardLayout card = new CardLayout(0, 0);
		panel_1.setLayout(card);
		panel_1.setVisible(true);
		panel_1.setOpaque(false);

		// 联系人页
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有好友");
//		JTree tree = new JTree(root);
		JScrollPane scroll = new JScrollPane(tree);
		scroll.getViewport().setOpaque(false);
		tree.setOpaque(false);
		scroll.setOpaque(false);
		// 为了能让所有的用户显示在好友列表中的tree中，我们需要遍历这个好友的集合，将集合中每个用户名字加入到jtree上
		for (int i = 0; i < allUsers.size(); i++) {
			if (!allUsers.get(i).getUsername().equals(loginedUser.getUsername())) {
				MutableTreeNode oneUser = new DefaultMutableTreeNode(allUsers.get(i).getUsername());
//				oneUser.setUserObject(allUsers.get(i));
				root.add(oneUser);
			}
		}
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == 1) {
					if (tree.getSelectionPath().getPathCount() == 2) {

						String username = tree.getSelectionPath().getLastPathComponent().toString();

						if (allChatWindows.containsKey(username)) {
							allChatWindows.get(username).setVisible(true);// 之前打开过这个好友的聊天窗口，此时只是从集合里把它拿出来然后调用setvisiable方法让这个窗口显示
						} else {
							System.out.println("正在尝试创建聊天窗口");
							for (int i = 0; i < allUsers.size(); i++) {
								if (allUsers.get(i).getUsername().equals(username)) {
									Chatwindow c = new Chatwindow(loginedUser, allUsers.get(i), out, in);
									c.setVisible(true);
									allChatWindows.put(username, c);// 如果是第一次打开，打开完之后将这个好友和窗口对应关系存入到集合里
								}
							}
						}
					}
				}
			}
		});

		JScrollPane lianxiren = new JScrollPane(tree);
		lianxiren.getViewport().setOpaque(false);
		lianxiren.setViewportView(tree);
		lianxiren.setOpaque(false);
		panel_1.add(lianxiren, "lianxiren");

		// 群聊页
		DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("所有群");
		JTree tree2 = new JTree(root2);
		tree2.setOpaque(false);
		MutableTreeNode oneteam = new DefaultMutableTreeNode("急！孙卫东在线征婚！");
		root2.add(oneteam);

		JScrollPane qunliao = new JScrollPane(tree2);
		qunliao.getViewport().setOpaque(false);
		qunliao.setViewportView(tree2);
		qunliao.setOpaque(false);
		panel_1.add(qunliao, "qunliao");
		tree2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == 1) {
					if (tree2.getSelectionPath().getPathCount() == 2) {

						String teamname = tree2.getSelectionPath().getLastPathComponent().toString();
//						System.out.println(teamname);
						if (teamChatWindows.containsKey(teamname)) {
							teamChatWindows.get(teamname).setVisible(true);// 之前打开过这个群聊的聊天窗口，此时只是从集合里把它拿出来然后调用setvisiable方法让这个窗口显示
						} else {
							System.out.println("正在尝试创建聊天窗口");
//							for (int i = 0; i < allUsers.size(); i++) {
//								if(allUsers.get(i).getUsername().equals(username)) {
							TeamChat c = new TeamChat(loginedUser, allUsers, teamname, out, in);
							c.setVisible(true);
							teamChatWindows.put(teamname, c);// 如果是第一次打开，打开完之后将这个好友和窗口对应关系存入到集合里
//								}
//							}
						}
					}
				}
			}
		});

		JLabel lblNewLabel_2 = new JLabel("联系人", JLabel.CENTER);
		lblNewLabel_2.setBounds(0, 122, 150, 35);
		lblNewLabel_2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(panel_1, "lianxiren");
			}
		});
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("群聊", JLabel.CENTER);
		lblNewLabel_4.setBounds(150, 122, 150, 35);
		lblNewLabel_4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(panel_1, "qunliao");
			}
		});
		contentPane.add(lblNewLabel_4);

		statrMessageReciverThread();
		this.paintComponents(this.getGraphics());
	}

	public void statrMessageReciverThread() {
		System.out.println("开始接收消息");
		class messageReciver extends Thread {
			@Override
			public void run() {
				while (true) {
					// 接收消息
					try {
						message recivermessage = (message) in.readObject();
						System.out.println("recivermessage" + recivermessage);
						if (recivermessage.getType().equals("sendmessage")) {
							if (allChatWindows.containsKey(recivermessage.getFrom().getUsername())) {
								allChatWindows.get(recivermessage.getFrom().getUsername()).setVisible(true); // 打开过此窗口，让次窗口再次显示
								allChatWindows.get(recivermessage.getFrom().getUsername()).getViewArea()
										.append(recivermessage.getFrom().getUsername() + "\t" + recivermessage.getTime()
												+ "\r\n" + recivermessage.getContent() + "\r\n\r\n");
								allChatWindows.get(recivermessage.getFrom().getUsername()).getViewArea()
										.setSelectionStart(allChatWindows.get(recivermessage.getFrom().getUsername())
												.getViewArea().getText().length());
							} else {
								Chatwindow c = new Chatwindow(loginedUser, recivermessage.getFrom(), out, in);
								c.setVisible(true);
								c.getViewArea().append(recivermessage.getFrom().getUsername() + "\t"
										+ recivermessage.getTime() + "\r\n" + recivermessage.getContent() + "\r\n\r\n");
								c.getViewArea().setSelectionStart(c.getViewArea().getText().length());
								allChatWindows.put(recivermessage.getFrom().getUsername(), c);// 如果是第一次打开，打开完之后将这个好友和窗口对应关系存入到集合里
							}
						} else if (recivermessage.getType().equals("teammessage")) {
							System.out.println(recivermessage.getFrom());
//							System.out.println(loginedUser);
							if (teamChatWindows.containsKey(recivermessage.getTo().getUsername())) {
								teamChatWindows.get(recivermessage.getTo().getUsername()).setVisible(true); // 打开过此窗口，让次窗口再次显示
								teamChatWindows.get(recivermessage.getTo().getUsername()).getViewArea()
										.append(recivermessage.getFrom().getUsername() + "\t" + recivermessage.getTime()
												+ "\r\n" + recivermessage.getContent() + "\r\n\r\n");
								teamChatWindows.get(recivermessage.getTo().getUsername()).getViewArea() // 显示到最下方
										.setSelectionStart(teamChatWindows.get(recivermessage.getTo().getUsername())
												.getViewArea().getText().length());
							} else {
								TeamChat c = new TeamChat(loginedUser, allUsers, recivermessage.getTo().getUsername(),
										out, in);
								c.setVisible(true);
								c.getViewArea().append(recivermessage.getFrom().getUsername() + "\t"
										+ recivermessage.getTime() + "\r\n" + recivermessage.getContent() + "\r\n\r\n");
								c.getViewArea().setSelectionStart(c.getViewArea().getText().length());
								teamChatWindows.put(recivermessage.getTo().getUsername(), c);// 如果是第一次打开，打开完之后将这个好友和窗口对应关系存入到集合里
							}
						} else if (recivermessage.getType().equals("oneLogin")) {
							// 接收别人登陆
//							List<user> allOnlineuser=recivermessage.getAllUser();
//							DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode) tree
//									.getLastSelectedPathComponent();
//							user nodeuser=(user)treeNode.getUserObject();
//							System.out.println(treeNode);
//							for(int i=0;i<allOnlineuser.size();i++) {
//								if(allOnlineuser.get(i).getZhanghu().equals(nodeuser.getZhanghu())) {
//									
//								}
//							}

						} else if (recivermessage.getType().equals("modusermessage")) {
							username.setText(recivermessage.getFrom().getUsername());
							ImageIcon icon2 = new ImageIcon(recivermessage.getFrom().getTouxiangpath());
							touxiang.setIcon(icon2);
							MainFrame.this.setVisible(true);
						} else if (recivermessage.getType().equals("updatemessage")) {
							System.out.println("有人更新资料啦");
							tree.updateUI();
						}

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		new messageReciver().start();
	}
}
