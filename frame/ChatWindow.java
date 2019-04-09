package frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import message.ChatMessages;
import message.MessageInterface;
import tablebeans.User;
import transmit.MessageManagement;

/**
 * 聊天窗口
 * @author zxk
 *
 */
public class ChatWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	//用于存放好友信息的List
	private static HashMap<Integer, ChatWindow> $friendUserInfoHashMap;
//	private static HashMap<Integer, Object[]> $chatWindowLocationMap;
	//用于标识窗体个数
	private static int $index;
	//记录聊天对象id, 用于标识窗口对象
	private Integer _index;
	
	static {
		$friendUserInfoHashMap = new HashMap<Integer, ChatWindow>();
//		$chatWindowLocationMap = new HashMap<Integer, Object[]>();
//		$chatWindowLocationMap.put(0, [null, new int[]])
		$index = 0;
	}
	
	/**
	 * 构造方法
	 * @param friendUserInfo 好友的用户信息
	 * @param _index 存放在List中的下标, 用于标识该类对象
	 */
	private ChatWindow(Integer friendID) {
		initCharWindow();
		//记录聊天对象id, 用于标识窗口对象
		_index = friendID.intValue();
		//用户ID为key
		$friendUserInfoHashMap.put(_index, this);
		//窗体个数加1
		$index++;
		
		JPanel container_JPanel = (JPanel)this.getContentPane();
		
		JButton close_JButton = getCloseJButton();
		JLabel move_JLabel = getMoveJLabel(close_JButton.getWidth());
		JLabel chatObject_JLabel = new JLabel("chatting with " 
				+ friendUserInfo.getUserNick() + ".. ");
		chatObject_JLabel.setBounds(10, 10, 200, 15);

		container_JPanel.add(close_JButton);
		container_JPanel.add(move_JLabel);
		container_JPanel.add(chatObject_JLabel);
		
		//用于显示功能栏JPanel
		JPanel tools_JPanel = new JPanel();
		int chatwidth_Temp = 480;
		tools_JPanel.setBounds(0, 30, chatwidth_Temp, 30);
		tools_JPanel.setBackground(new Color(100, 200, 100));
		
		//用于显示聊天消息的JPanel
		JPanel chatMessage_JPanel = new JPanel();
		chatMessage_JPanel.setBounds(0, 60, chatwidth_Temp, 310);
		chatMessage_JPanel.setLayout(null);
		chatMessage_JPanel.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 1));

		JScrollPane chatMessage_JScorllPane = new JScrollPane();
		chatMessage_JScorllPane.setBounds(0, 0, chatMessage_JPanel.getWidth(), chatMessage_JPanel.getHeight());
		
		DefaultTableModel defaultModel = new DefaultTableModel();
//		defaultModel.addRow(rowData);
		JLabel chatMessage_JLabel = new JLabel();
		Date currentTime = new Date();
		SimpleDateFormat chatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String messageContext = "<html>" + chatDate.format(currentTime) 
			+ "&nbsp;&nbsp;&nbsp;" + friendUserInfo.getUserNick() + "<br/>"
				+ "&nbsp;&nbsp;&nbsp;" + "hi~";
		chatMessage_JLabel.setText(messageContext);
		chatMessage_JLabel.setBounds(12, 12, chatMessage_JPanel.getWidth(), 60);
		Color tempColor = new Color(156, 156, 156);
		chatMessage_JLabel.setBorder(BorderFactory.createLineBorder(tempColor, 1));
		chatMessage_JPanel.add(chatMessage_JLabel);
		
		JTable chatMessage_JTable = new JTable(defaultModel);
		
		
		
//		chatMessage_JLabel.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 5, true));
		
		
		
		//用于显示聊天工具栏的JPanel
		JPanel chatTools_JPanel = new JPanel();
		chatTools_JPanel.setBounds(0, 370, chatwidth_Temp, 30);
		chatTools_JPanel.setBackground(new Color(100, 0, 160));
		
		//用于显示聊天输入JPanel
		JPanel messageEntry_JPanel = new JPanel();
		messageEntry_JPanel.setBounds(0, 400, chatwidth_Temp, this._height - 400);
//		messageEntry_JPanel.setBackground(new Color(40, 200, 200));
		messageEntry_JPanel.setLayout(null);
		messageEntry_JPanel.setBorder(BorderFactory.createLineBorder(tempColor, 1));
		
		JTextPane entry_JTextPane = new JTextPane();
		entry_JTextPane.setBounds(0, 0, messageEntry_JPanel.getWidth() - 1, messageEntry_JPanel.getHeight() - 40);
		entry_JTextPane.setAutoscrolls(true);
		messageEntry_JPanel.add(entry_JTextPane);
		
		JButton sendMessage_JButton = new JButton("send");
		sendMessage_JButton.setBounds(chatwidth_Temp - 90, entry_JTextPane.getHeight() + 5, 80, 20);
		messageEntry_JPanel.add(sendMessage_JButton);
		sendMessage_JButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String textPane_Message = entry_JTextPane.getText();
				System.out.println("textPane_Message: " + textPane_Message);
				if (!textPane_Message.equals("")) {
					//需要发送的消息不为空
					ChatMessages chatMessage = new ChatMessages();
					chatMessage.setSenderID(MainWindow.getSaveUserID().intValue());
					chatMessage.setGetterID(_index.intValue());
					chatMessage.setMessage(textPane_Message);					
					MessageInterface ms = MessageManagement.sendChatMessage(chatMessage);
					System.out.println("ms:" + ms);
					entry_JTextPane.setText("");
					//TODO handle "ms"
				}
			}
		});
		
		container_JPanel.add(tools_JPanel);
		container_JPanel.add(chatMessage_JPanel);
		container_JPanel.add(chatTools_JPanel);
		container_JPanel.add(messageEntry_JPanel);
		
		this.setVisible(true);
		
	}
	
	/**
	 * 初始化聊天窗口
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
	 * @param friendUserInfo
	 * @return
	 */
	public static ChatWindow createChatWindow(Integer friendID) {
		if (friendID == null) {
			throw new IllegalArgumentException("argument is null ..");
		}
		if ($friendUserInfoHashMap.size() > 0) {
			//Map.Entry遍历map
			for (Entry<Integer, ChatWindow> entry_element : $friendUserInfoHashMap.entrySet()) {
				if (entry_element.getKey().equals(
						friendID)) {
					// 获得焦点
					entry_element.getValue().requestFocus();
					return null;
				}
			}
		}
		return new ChatWindow(friendID);
	}
	
	/**
	 * 当双击的聊天对象已有聊天窗口时, 该聊天窗口重新获得焦点
	 */
	public void showChatWindow() {
		this.requestFocus();
	}

	/**
	 * 通过key删除元素
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

	public void set_index(Number _index) {
		this._index = _index;
	}

//	public static void main(String[] args) {
//		ChatWindow.createChatWindow(null);
//	}
}
