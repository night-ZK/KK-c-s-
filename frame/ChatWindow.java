package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import customexception.ServerSendChatMessageException;
import frame.customComponent.ChatJLabel;
import frame.customComponent.ChatMessageTextJPanel;
import frame.customComponent.FriendsListTree;
import message.ChatMessages;
import message.MessageHead;
import message.MessageModel;
import model.DrawStyleModel;
import tablejson.UserFriendsInformation;
import tools.ObjectTool;
import tools.TransmitTool;
import transmit.MessageManagement;

/**
 * ���촰��
 * @author zxk
 *
 */
public class ChatWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	//���ڴ�ź�����Ϣ��List
	private static HashMap<Integer, ChatWindow> $friendUserInfoHashMap;
//	private static HashMap<Integer, Object[]> $chatWindowLocationMap;
	//���ڱ�ʶ�������
	private static int $index;
	//��¼�������id, ���ڱ�ʶ���ڶ���
	private Integer _index;
	
	private Queue<ChatMessageTextJPanel> chatMessageTJPQueue;

	private static Color borderColor;
	private static Color toolsPanelColor;
	
	JPanel container_JPanel;
	
	JButton close_JButton;
	JButton min_JButton;
	JLabel move_JLabel;
	JLabel chatObject_JLabel;
	
	//������ʾ������JPanel
	JPanel tools_JPanel;
	int chatwidth_Temp;
	
	JScrollPane chatMessage_JScorllPane;
	
	JScrollPane sendChatMessage_JScorllPane;
	
	//������ʾ������Ϣ��JPanel
	JPanel showChatMessage_JPanel;
	
	ChatJLabel chatMessage_JLabel;

	//������
	JScrollBar jsBar;
	
	//������ʾ���칤������JPanel
	JPanel chatTools_JPanel;
	
	//������ʾ��������JPanel
	JPanel messageEntry_JPanel;
	
//	JTextPane entry_JTextPane;
	
	ChatMessageTextJPanel entry_JTextPane;

	//���Ͱ�ť
	JButton sendMessage_JButton;
	//��Ƶ��ť
	JButton video_JButton;
	//����
	JButton voice_JButton;
	//��Ƭ
	JButton watchMovie_JButton;

	//����ͼƬ
	JButton image_JButton;
	//�ļ�
	JButton file_JButton;
	
	static {
		$friendUserInfoHashMap = new HashMap<Integer, ChatWindow>();
		$index = 0;
		borderColor = new Color(156, 156, 156);
		toolsPanelColor = new Color(200, 200, 200);
	}

	/**
	 * ���췽��
	 * @param friendUserInfo ���ѵ��û���Ϣ
	 * @param _index �����List�е��±�, ���ڱ�ʶ�������
	 */
	private ChatWindow(UserFriendsInformation friendUserInfo, ImageIcon icon) {
		initCharWindow(icon);
		//��¼�������id, ���ڱ�ʶ���ڶ���
		_index = friendUserInfo.getId().intValue();
		
		chatMessageTJPQueue = new ArrayDeque<>();
		chatwidth_Temp = 480;
		
		//���������1
		$index++;
		
		initHeader(friendUserInfo.getUserNick());
		
		initTools_JPanel();
		
		//��ʾ������Ϣ
		initChatMessagePanel();

		initChatToolsPanel();
		
		//������ʾ��������JPanel
		initMessageEntryPanel();
		
		container_JPanel.add(tools_JPanel);
		container_JPanel.add(chatMessage_JScorllPane);
		container_JPanel.add(chatTools_JPanel);
		container_JPanel.add(messageEntry_JPanel);
		
		//�û�IDΪkey
		$friendUserInfoHashMap.put(_index, this);
		this.setVisible(true);
	}

	private void initMessageEntryPanel() {
		//������ʾ��������JPanel
		messageEntry_JPanel = new JPanel();
		messageEntry_JPanel.setBounds(0, 400, chatwidth_Temp, this._height - 400);
		messageEntry_JPanel.setLayout(null);
		messageEntry_JPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
		
		initSendJScorPanel();
		
		initSendJButton();
	}

	private void initSendJButton() {
		//���Ͱ�ť
		sendMessage_JButton = new JButton("send");
		sendMessage_JButton.setBounds(chatwidth_Temp - 90, sendChatMessage_JScorllPane.getHeight() + 5, 80, 20);
		
		sendMessage_JButton.addMouseListener(new MouseAdapter() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				String textPane_Message = entry_JTextPane.getText();
				System.out.println("textPane_Message: " + textPane_Message);
				if (!textPane_Message.equals("")) {
					//��Ҫ���͵���Ϣ��Ϊ��
					ChatMessages chatMessage = new ChatMessages();
					chatMessage.setSenderID(MainWindow.getSaveUserID().intValue());
					chatMessage.setGetterID(_index.intValue());
					chatMessage.setMessage(textPane_Message);					
					MessageModel chatMessageModel = MessageManagement.chatMessageModel(chatMessage);
					try {
						TransmitTool.sendChatMessageForNIO(chatMessageModel);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					entry_JTextPane.setText("");

					String date = _dateFormat.format(
							new Date(chatMessageModel.getMessageHead().getRequestTime()));
					ChatMessageTextJPanel chatMessageTextJPanel = 
							new ChatMessageTextJPanel(date, getSaveUserNick(), 0);
					chatMessageTextJPanel.setText(textPane_Message);
					
					chatMessageTJPQueue.add(chatMessageTextJPanel);

					int cmtjpY = 24;
					int width = showChatMessage_JPanel.getWidth() - 100;
					for (ChatMessageTextJPanel cmtjp : chatMessageTJPQueue) {
						
						int height = cmtjp.getPreferredSize().height + 14; 
						//deprecation
						cmtjp.reshape(15, cmtjpY, width, height);
						height = cmtjp.getPreferredSize().height;
						//deprecation ����
						cmtjp.reshape(15, cmtjpY, width, height);
						
						int styleWidth = cmtjp.getPreferredSize().width;
						
						cmtjp.setDrawStyleModel(new DrawStyleModel(15
								, cmtjpY
								, styleWidth > width ? width : styleWidth
								, height
								, 10
								, 10));
						
//						cmtjp.drawStyle(15, cmtjpY, width, height, 10, 10);
						
						JLabel timeLabel = cmtjp.getTimeLabel();
						cmtjp.getTimeLabel().setBounds(10, cmtjpY - 14, width, 14);
						cmtjpY += (height + 34);
						showChatMessage_JPanel.add(timeLabel);
						showChatMessage_JPanel.add(cmtjp);
					}
					
					if (cmtjpY > chatMessage_JScorllPane.getHeight()) {						
						showChatMessage_JPanel.setPreferredSize(new Dimension(chatwidth_Temp, cmtjpY));
						showChatMessage_JPanel.updateUI();
						chatMessage_JScorllPane.getViewport().setViewPosition(new Point(0, jsBar.getMaximum()));
						
					}else {
						showChatMessage_JPanel.updateUI();
					}
				}

			}
		});
		
		messageEntry_JPanel.add(sendMessage_JButton);
	}

	private void initSendJScorPanel() {
		sendChatMessage_JScorllPane = new JScrollPane();
		sendChatMessage_JScorllPane.setBounds(0, 0
				, messageEntry_JPanel.getWidth() - 1
				, messageEntry_JPanel.getHeight() - 40);
		sendChatMessage_JScorllPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sendChatMessage_JScorllPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		entry_JTextPane = new ChatMessageTextJPanel();
		
		entry_JTextPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					entry_JTextPane.setText("");
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					sendMessage();
				}
			}
		});
		
		sendChatMessage_JScorllPane.setViewportView(entry_JTextPane);
		
		messageEntry_JPanel.add(sendChatMessage_JScorllPane);
	}

	private void initChatToolsPanel() {
		//������ʾ���칤������JPanel
		chatTools_JPanel = new JPanel();
		chatTools_JPanel.setBounds(0, 370, chatwidth_Temp, 30);
		chatTools_JPanel.setBackground(toolsPanelColor);
		chatTools_JPanel.setLayout(null);
		//����
		image_JButton = getToolsJButton("./resources/image/image_JButton.png");
		image_JButton.setBounds(5, 0, 30, 30);
		//�ļ�
		file_JButton = getToolsJButton("./resources/image/file_JButton.png");
		file_JButton.setBounds(image_JButton.getX() 
				+ image_JButton.getWidth() + 5, 0, 30, 30);
		chatTools_JPanel.add(image_JButton);
		chatTools_JPanel.add(file_JButton);
	}

	private JButton getToolsJButton(String imagePath) {
		JButton jButton = new JButton(new ImageIcon(imagePath));
		jButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new MessageWindow("@_@`", "Coming soon..", JRootPane.FRAME);
			}
		});
		return jButton;
	}

	private void initChatMessagePanel() {
		chatMessage_JScorllPane = new JScrollPane();
		chatMessage_JScorllPane.setBounds(0, 60, chatwidth_Temp, 300);
		chatMessage_JScorllPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatMessage_JScorllPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatMessage_JScorllPane.setSize(chatwidth_Temp, 300);
//		chatMessage_JScorllPane.setVerticalScrollBar(verticalScrollBar);
//		JScrollBar curJSB = new JScrollBar();
		//������ʾ������Ϣ��JPanel
		showChatMessage_JPanel = new JPanel();
		showChatMessage_JPanel.setBounds(0, 0, chatwidth_Temp, 300);
		showChatMessage_JPanel.setLayout(null);
		showChatMessage_JPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
		showChatMessage_JPanel.setBackground(new Color(250, 250, 250));
		chatMessage_JScorllPane.setViewportView(showChatMessage_JPanel);

		//������
		jsBar = chatMessage_JScorllPane.getVerticalScrollBar();
	}

	private void initHeader(String userNick) {
		container_JPanel = (JPanel)this.getContentPane();
		
		close_JButton = getCloseJButton();
		min_JButton = getMinJButton();
		move_JLabel = getMoveJLabel(close_JButton.getWidth() + min_JButton.getWidth());
		chatObject_JLabel = new JLabel("chatting with " 
				+ userNick + ".. ");
		chatObject_JLabel.setBounds(10, 10, 200, 15);

		container_JPanel.add(close_JButton);
		container_JPanel.add(min_JButton);
		container_JPanel.add(move_JLabel);
		container_JPanel.add(chatObject_JLabel);
	}

	private void initTools_JPanel() {
		//������ʾ������JPanel
		tools_JPanel = new JPanel();
		tools_JPanel.setBounds(0, 30, chatwidth_Temp, 30);
		tools_JPanel.setBackground(toolsPanelColor);
		tools_JPanel.setLayout(null);
		//��Ƶ��ť
		video_JButton = getToolsJButton("./resources/image/video_JButton.png");
		video_JButton.setBounds(5, 0, 30, 30);
		//����
		voice_JButton = getToolsJButton("./resources/image/voice_JButton.png");
		voice_JButton.setBounds(video_JButton.getX() 
				+ video_JButton.getWidth() + 5, 0, 30, 30);
		//��Ƭ
		watchMovie_JButton = getToolsJButton("./resources/image/watchMV_JButton.png");
		watchMovie_JButton.setBounds(voice_JButton.getX() 
				+ voice_JButton.getWidth() + 5, 0, 30, 30);
		
		tools_JPanel.add(video_JButton);
		tools_JPanel.add(voice_JButton);
		tools_JPanel.add(watchMovie_JButton);
	}
	@SuppressWarnings("deprecation")
	public void sendMessage() {
		String textPane_Message = entry_JTextPane.getText();
		System.out.println("textPane_Message: " + textPane_Message);
		if (!textPane_Message.equals("")) {
			//��Ҫ���͵���Ϣ��Ϊ��
			ChatMessages chatMessage = new ChatMessages();
			chatMessage.setSenderID(MainWindow.getSaveUserID().intValue());
			chatMessage.setGetterID(_index.intValue());
			chatMessage.setMessage(textPane_Message);					
			MessageModel chatMessageModel = MessageManagement.chatMessageModel(chatMessage);
			try {
				TransmitTool.sendChatMessageForNIO(chatMessageModel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			entry_JTextPane.setText("");

			String date = _dateFormat.format(
					new Date(chatMessageModel.getMessageHead().getRequestTime()));
			ChatMessageTextJPanel chatMessageTextJPanel = 
					new ChatMessageTextJPanel(date, getSaveUserNick(), 0);
			chatMessageTextJPanel.setText(textPane_Message);
			
			chatMessageTJPQueue.add(chatMessageTextJPanel);

			int cmtjpY = 24;
			int width = showChatMessage_JPanel.getWidth() - 100;
			for (ChatMessageTextJPanel cmtjp : chatMessageTJPQueue) {
				
				int height = cmtjp.getPreferredSize().height + 14; 
				//deprecation
				cmtjp.reshape(15, cmtjpY, width, height);
				height = cmtjp.getPreferredSize().height;
				//deprecation ����
				cmtjp.reshape(15, cmtjpY, width, height);
				
				int styleWidth = cmtjp.getPreferredSize().width;
				
				cmtjp.setDrawStyleModel(new DrawStyleModel(15
						, cmtjpY
						, styleWidth > width ? width : styleWidth
						, height
						, 10
						, 10));
				
//				cmtjp.drawStyle(15, cmtjpY, width, height, 10, 10);
				
				JLabel timeLabel = cmtjp.getTimeLabel();
				cmtjp.getTimeLabel().setBounds(10, cmtjpY - 14, width, 14);
				cmtjpY += (height + 34);
				showChatMessage_JPanel.add(timeLabel);
				showChatMessage_JPanel.add(cmtjp);
			}
			
			if (cmtjpY > chatMessage_JScorllPane.getHeight()) {						
				showChatMessage_JPanel.setPreferredSize(new Dimension(chatwidth_Temp, cmtjpY));
				showChatMessage_JPanel.updateUI();
				chatMessage_JScorllPane.getViewport().setViewPosition(new Point(0, jsBar.getMaximum()));
				
			}else {
				showChatMessage_JPanel.updateUI();
			}
		}

	}

	@SuppressWarnings("deprecation")
	public static void newsComing(MessageModel newsModel) throws ServerSendChatMessageException {
		ChatMessages chatMessages = (ChatMessages)newsModel.getMessageContext();
		
		if(ObjectTool.isNull(chatMessages)) 
			throw new ServerSendChatMessageException("server charmessage illegal..");
		
		Integer friendID = chatMessages.getSenderID();
		
		if(ObjectTool.isNull(friendID))
				throw new ServerSendChatMessageException("server charmessage illegal..");
		
		FriendsListTree friendsListTree = MainWindow.friendsListTreeMap.get(friendID);
		if (!ObjectTool.isNull(friendsListTree)) {
			//������Ϣ���ں����б���
			UserFriendsInformation ufi = friendsListTree.get_userFriendInfo();
			MessageHead newsHead = newsModel.getMessageHead();
//			ChatModel chatModel = new ChatModel(chatMessages.getMessage(), newsHead.getReplyTime().toString());
			ChatWindow chatWindow = createChatWindow(ufi, friendsListTree.get_userImageIcon());
			Date sendDate = new Date(newsHead.getRequestTime());
			
			String date = _dateFormat.format(sendDate);
			ChatMessageTextJPanel chatMessageTextJPanel = 
					new ChatMessageTextJPanel(date,ufi.getUserNick() , 1);
//			chatMessageTextJPanel.setEditable(false);
			chatMessageTextJPanel.setText(chatMessages.getMessage());
			
			chatWindow.chatMessageTJPQueue.add(chatMessageTextJPanel);

//			int conunt = 0;
			int cmtjpY = 24;
			int width = chatWindow.showChatMessage_JPanel.getWidth() - 100;
			for (ChatMessageTextJPanel cmtjp : chatWindow.chatMessageTJPQueue) {
				
				int height = cmtjp.getPreferredSize().height + 14; 
				//deprecation
				cmtjp.reshape(15, cmtjpY, width, height);
				height = cmtjp.getPreferredSize().height;
				//deprecation ����
				cmtjp.reshape(15, cmtjpY, width, height);
				
				int styleWidth = cmtjp.getPreferredSize().width;
				
				cmtjp.setDrawStyleModel(new DrawStyleModel(15
						, cmtjpY
						, styleWidth > width ? width : styleWidth
						, height
						, 10
						, 10));
				
//				cmtjp.drawStyle(15, cmtjpY, width, height, 10, 10);
				
				JLabel timeLabel = cmtjp.getTimeLabel();
				cmtjp.getTimeLabel().setBounds(10, cmtjpY - 14, width, 14);
				cmtjpY += (height + 34);
				chatWindow.showChatMessage_JPanel.add(timeLabel);
				chatWindow.showChatMessage_JPanel.add(cmtjp);
			}
			
			if (cmtjpY > chatWindow.chatMessage_JScorllPane.getHeight()) {						
				chatWindow.showChatMessage_JPanel.setPreferredSize(new Dimension(chatWindow.chatwidth_Temp, cmtjpY));
				chatWindow.showChatMessage_JPanel.updateUI();
				chatWindow.chatMessage_JScorllPane.getViewport().setViewPosition(new Point(0, chatWindow.jsBar.getMaximum()));
				
			}else {
				chatWindow.showChatMessage_JPanel.updateUI();
			}

			
//			ChatJLabel chatJLabel = new ChatJLabel(1, ufi.getUserNick(), chatMessages.getMessage(), sendDate);
//			chatWindow.chatMessageQueue.add(chatJLabel);
//			int conunt = 0;
//			for (ChatJLabel chatJlabel : chatWindow.chatMessageQueue) {
//				if (chatJlabel.getSendType() == 0) {
//					chatJlabel.setBounds(12,  conunt++ * 55, chatWindow.showChatMessage_JPanel.getWidth(), 55);
//				}else {
//					chatJlabel.setBounds(chatWindow.showChatMessage_JPanel.getWidth() - 180, conunt++ * 55, chatWindow.showChatMessage_JPanel.getWidth(), 55);
//				}
//				chatWindow.showChatMessage_JPanel.add(chatJlabel);
//			}
//			
//			System.out.println("queueSize: " + chatWindow.chatMessageQueue.size());
//			int height = chatWindow.chatMessageQueue.size() * 55;
//			if (height > chatWindow.chatMessage_JScorllPane.getHeight()) {						
//				chatWindow.showChatMessage_JPanel.setPreferredSize(new Dimension(chatWindow.chatwidth_Temp, height));
//				chatWindow.showChatMessage_JPanel.updateUI();
//				chatWindow.chatMessage_JScorllPane.getViewport().setViewPosition(new Point(0, chatWindow.jsBar.getMaximum()));
//				
//			}else {
//				chatWindow.showChatMessage_JPanel.updateUI();
//			}
		}
		
	}

	/**
	 * ��ʼ�����촰��
	 */
	private void initCharWindow(ImageIcon icon) {
		this._width(480);
		this._height(500);
		this.setSize(_width, _height);
//		this.setLocationRelativeTo(null);
		this.set_X($index * 20);
		this.set_y($index * 20);
		this.setLocation((int)_x, (int)_y);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setIconImage(icon.getImage());
	}
	
	/**
	 * 
	 * @param chatModel 
	 * @param friendUserInfo
	 * @return
	 */
	public static ChatWindow createChatWindow(UserFriendsInformation friendsInfo, ImageIcon icon) {
		if (ObjectTool.isNull(friendsInfo.getId())) {
			throw new IllegalArgumentException("argument is null ..");
		}
		
		if ($friendUserInfoHashMap.size() > 0) {
			Integer idKey = friendsInfo.getId().intValue();
			if($friendUserInfoHashMap.containsKey(idKey)) {
				ChatWindow chatWindow = $friendUserInfoHashMap.get(idKey);
				chatWindow.requestFocus();
				return chatWindow;
			}
		}
		
		return new ChatWindow(friendsInfo, icon);
	}
	
	/**
	 * ��˫������������������촰��ʱ, �����촰�����»�ý���
	 */
	public void showChatWindow() {
		this.requestFocus();
	}

	/**
	 * ͨ��keyɾ��Ԫ��
	 * @param key
	 */
	public void removeChatFriendsList(Number key) {
		$friendUserInfoHashMap.remove(key);
	}
	
	public static int get$index() {
		return $index;
	}

	public static void set$index(int $index) {
		ChatWindow.$index = $index;
	}

	public Number get_index() {
		return _index;
	}

	public void set_index(Integer _index) {
		this._index = _index;
	}
	
	public static Color getBorderColor() {
		return borderColor;
	}


	public static void setBorderColor(Color borderColor) {
		ChatWindow.borderColor = borderColor;
	}
	
}
