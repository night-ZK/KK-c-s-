package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import customexception.ServerSendChatMessageException;
import frame.customComponent.ChatJLabel;
import frame.customComponent.FriendsListTree;
import message.ChatMessages;
import message.MessageHead;
import message.MessageModel;
import model.ChatModel;
import tablejson.UserFriendsInformation;
import tools.ObjectTool;
import transmit.MessageManagement;
import transmit.sender.ChatSender;

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
	
	private Queue<ChatJLabel> chatMessageQueue;

	private static Color borderColor;
	
	JPanel container_JPanel;
	
	JButton close_JButton;
	JLabel move_JLabel;
	JLabel chatObject_JLabel;
	
	//������ʾ������JPanel
	JPanel tools_JPanel;
	int chatwidth_Temp;
	
	JScrollPane chatMessage_JScorllPane;
	//������ʾ������Ϣ��JPanel
	JPanel showChatMessage_JPanel;
	
	ChatJLabel chatMessage_JLabel;

	//������
	JScrollBar jsBar;
	
	//������ʾ���칤������JPanel
	JPanel chatTools_JPanel;
	
	//������ʾ��������JPanel
	JPanel messageEntry_JPanel;
	
	JTextPane entry_JTextPane;

	//���Ͱ�ť
	JButton sendMessage_JButton;
	
	static {
		$friendUserInfoHashMap = new HashMap<Integer, ChatWindow>();
		$index = 0;
		borderColor = new Color(156, 156, 156);
	}

	/**
	 * ���췽��
	 * @param friendUserInfo ���ѵ��û���Ϣ
	 * @param _index �����List�е��±�, ���ڱ�ʶ�������
	 */
	private ChatWindow(UserFriendsInformation friendUserInfo) {
		initCharWindow();
		//��¼�������id, ���ڱ�ʶ���ڶ���
		_index = friendUserInfo.getId().intValue();
		
		chatMessageQueue = new ArrayDeque<>();
		//���������1
		$index++;
		
		container_JPanel = (JPanel)this.getContentPane();
		
		close_JButton = getCloseJButton();
		move_JLabel = getMoveJLabel(close_JButton.getWidth());
		chatObject_JLabel = new JLabel("chatting with " 
				+ friendUserInfo.getUserNick() + ".. ");
		chatObject_JLabel.setBounds(10, 10, 200, 15);

		container_JPanel.add(close_JButton);
		container_JPanel.add(move_JLabel);
		container_JPanel.add(chatObject_JLabel);
		
		//������ʾ������JPanel
		tools_JPanel = new JPanel();
		chatwidth_Temp = 480;
		tools_JPanel.setBounds(0, 30, chatwidth_Temp, 30);
		tools_JPanel.setBackground(new Color(100, 200, 100));
		
		chatMessage_JScorllPane = new JScrollPane();
		chatMessage_JScorllPane.setBounds(0, 60, chatwidth_Temp, 300);
//		chatMessage_JScorllPane.setAutoscrolls(true);
		chatMessage_JScorllPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatMessage_JScorllPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatMessage_JScorllPane.setSize(chatwidth_Temp, 300);
//		chatMessage_JScorllPane.doLayout();
//		chatMessage_JScorllPane.add(showChatMessage_JTextPane);
		
		//������ʾ������Ϣ��JPanel
		showChatMessage_JPanel = new JPanel();
		showChatMessage_JPanel.setBounds(0, 0, chatwidth_Temp, 300);
		showChatMessage_JPanel.setLayout(null);
		showChatMessage_JPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
		showChatMessage_JPanel.setPreferredSize(new Dimension(chatwidth_Temp, 300));
		
//		chatMessage_JLabel = new ChatJLabel(0, friendUserInfo.getUserNick(), "test");
//		chatMessage_JLabel.setBounds(12, 10, showChatMessage_JPanel.getWidth(), 55);
//		chatMessageQueue.add(chatMessage_JLabel);
//		
//		showChatMessage_JPanel.add(chatMessage_JLabel);
		
		chatMessage_JScorllPane.setViewportView(showChatMessage_JPanel);

		//������
		jsBar = chatMessage_JScorllPane.getVerticalScrollBar();
		
		//������ʾ���칤������JPanel
		chatTools_JPanel = new JPanel();
		chatTools_JPanel.setBounds(0, 370, chatwidth_Temp, 30);
		chatTools_JPanel.setBackground(new Color(100, 0, 160));
		
		//������ʾ��������JPanel
		messageEntry_JPanel = new JPanel();
		messageEntry_JPanel.setBounds(0, 400, chatwidth_Temp, this._height - 400);
		messageEntry_JPanel.setLayout(null);
		messageEntry_JPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
		
		entry_JTextPane = new JTextPane();
		entry_JTextPane.setBounds(0, 0, messageEntry_JPanel.getWidth() - 1, messageEntry_JPanel.getHeight() - 40);
		entry_JTextPane.setAutoscrolls(true);
		messageEntry_JPanel.add(entry_JTextPane);

		//���Ͱ�ť
		sendMessage_JButton = new JButton("send");
		sendMessage_JButton.setBounds(chatwidth_Temp - 90, entry_JTextPane.getHeight() + 5, 80, 20);
		messageEntry_JPanel.add(sendMessage_JButton);
		
		sendMessage_JButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String textPane_Message = entry_JTextPane.getText();
				System.out.println("textPane_Message: " + textPane_Message);
				if (!textPane_Message.equals("")) {
					//��Ҫ���͵���Ϣ��Ϊ��
					ChatMessages chatMessage = new ChatMessages();
					chatMessage.setSenderID(MainWindow.getSaveUserID().intValue());
					chatMessage.setGetterID(_index.intValue());
					chatMessage.setMessage(textPane_Message);					
					MessageModel chatMessageModel = MessageManagement.chatMessageModel(chatMessage);
					ChatSender chatSender = new ChatSender(chatMessageModel);
					chatSender.sendRequest(false);
					
					entry_JTextPane.setText("");
					
					chatMessageQueue.add(new ChatJLabel(0, getSaveUserNick(), textPane_Message));
					int conunt = 0;
					for (ChatJLabel chatJlabel : chatMessageQueue) {
						if (chatJlabel.getSendType() == 0) {
							chatJlabel.setBounds(12,  conunt++ * 55, showChatMessage_JPanel.getWidth(), 55);
						}else {
							chatJlabel.setBounds(showChatMessage_JPanel.getWidth() - 180, conunt++ * 55, showChatMessage_JPanel.getWidth(), 55);
						}
						showChatMessage_JPanel.add(chatJlabel);
					}
					
					System.out.println("queueSize: " + chatMessageQueue.size());
					int height = chatMessageQueue.size() * 55;
					if (height > chatMessage_JScorllPane.getHeight()) {						
						showChatMessage_JPanel.setPreferredSize(new Dimension(chatwidth_Temp, height));
						showChatMessage_JPanel.updateUI();
						chatMessage_JScorllPane.getViewport().setViewPosition(new Point(0, jsBar.getMaximum()));
						
					}else {
						showChatMessage_JPanel.updateUI();
					}
				}
			}
		});
		
		container_JPanel.add(tools_JPanel);
		container_JPanel.add(chatMessage_JScorllPane);
		container_JPanel.add(chatTools_JPanel);
		container_JPanel.add(messageEntry_JPanel);
		
		//�û�IDΪkey
		$friendUserInfoHashMap.put(_index, this);
		this.setVisible(true);
	}
	

	/**
	 * ��ʼ�����촰��
	 */
	private void initCharWindow() {
		this._width(600);
		this._height(500);
		this.setSize(_width, _height);
//		this.setLocationRelativeTo(null);
		this.set_X($index * 20);
		this.set_y($index * 20);
		this.setLocation((int)_x, (int)_y);
		this.setUndecorated(true);
		this.setLayout(null);
	}
	
	/**
	 * 
	 * @param chatModel 
	 * @param friendUserInfo
	 * @return
	 */
	public static ChatWindow createChatWindow(UserFriendsInformation friendsInfo) {
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
		
		return new ChatWindow(friendsInfo);
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
			ChatWindow chatWindow = createChatWindow(ufi);
			Date sendDate = new Date(newsHead.getRequestTime());
			ChatJLabel chatJLabel = new ChatJLabel(1, ufi.getUserNick(), chatMessages.getMessage(), sendDate);
			chatWindow.chatMessageQueue.add(chatJLabel);
			int conunt = 0;
			for (ChatJLabel chatJlabel : chatWindow.chatMessageQueue) {
				if (chatJlabel.getSendType() == 0) {
					chatJlabel.setBounds(12,  conunt++ * 55, chatWindow.showChatMessage_JPanel.getWidth(), 55);
				}else {
					chatJlabel.setBounds(chatWindow.showChatMessage_JPanel.getWidth() - 180, conunt++ * 55, chatWindow.showChatMessage_JPanel.getWidth(), 55);
				}
				chatWindow.showChatMessage_JPanel.add(chatJlabel);
			}
			
			System.out.println("queueSize: " + chatWindow.chatMessageQueue.size());
			int height = chatWindow.chatMessageQueue.size() * 55;
			if (height > chatWindow.chatMessage_JScorllPane.getHeight()) {						
				chatWindow.showChatMessage_JPanel.setPreferredSize(new Dimension(chatWindow.chatwidth_Temp, height));
				chatWindow.showChatMessage_JPanel.updateUI();
				chatWindow.chatMessage_JScorllPane.getViewport().setViewPosition(new Point(0, chatWindow.jsBar.getMaximum()));
				
			}else {
				chatWindow.showChatMessage_JPanel.updateUI();
			}
		}
		
	}

	
	public static Color getBorderColor() {
		return borderColor;
	}


	public static void setBorderColor(Color borderColor) {
		ChatWindow.borderColor = borderColor;
	}
	
//	public static void main(String[] args) {
//		ChatWindow.createChatWindow(null);
//	}
}
