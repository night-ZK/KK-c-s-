package frame;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tablebeans.User;

/**
 * 聊天窗口
 * @author zxk
 *
 */
public class ChatWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	//用于存放好友信息的List
	private static HashMap<Number, User> $friendUserInfoHashMap;
	//用于标识窗体个数
	private static int $index;
	//记录聊天对象id, 用于标识窗口对象
	private Number _index;
	
	static {
		$friendUserInfoHashMap = new HashMap<Number, User>();
		$index = 0;
	}
	
	/**
	 * 构造方法
	 * @param friendUserInfo 好友的用户信息
	 * @param _index 存放在List中的下标, 用于标识该类对象
	 */
	private ChatWindow(User friendUserInfo) {
		initCharWindow();
		//记录聊天对象id, 用于标识窗口对象
		_index = friendUserInfo.getId();
		//用户ID为key
		$friendUserInfoHashMap.put(_index, friendUserInfo);
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
		chatMessage_JPanel.setBackground(new Color(0, 100, 200));
		
		//用于显示聊天工具栏的JPanel
		JPanel chatTools_JPanel = new JPanel();
		chatTools_JPanel.setBounds(0, 370, chatwidth_Temp, 30);
		chatTools_JPanel.setBackground(new Color(100, 0, 160));
		
		//用于显示聊天输入JPanel
		JPanel messageEntry_JPanel = new JPanel();
		messageEntry_JPanel.setBounds(0, 400, chatwidth_Temp, this._height - 400);
		messageEntry_JPanel.setBackground(new Color(40, 200, 200));
		
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
	public static ChatWindow createChatWindow(User friendUserInfo) {
		if (friendUserInfo == null) {
			throw new IllegalArgumentException("argument is null ..");
		}
		if ($friendUserInfoHashMap.size() > 0) {
			//Map.Entry遍历map
			for (Map.Entry<Number, User> entry_element : $friendUserInfoHashMap.entrySet()) {
				if (entry_element.getKey().equals(
						friendUserInfo.getId())) {
					
					return null;
				}
			}
		}
		return new ChatWindow(friendUserInfo);
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
