package frame;

import java.awt.Color;
import java.util.ArrayList;

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
	private static ArrayList<User> $friendUserInfo;
	//存放在List中的下标, 用于标识该类对象
	private static int $index;
	
	static {
		$friendUserInfo = new ArrayList<User>();
		$index = 0;
	}
	
	/**
	 * 构造方法
	 * @param friendUserInfo 好友的用户信息
	 * @param index 存放在List中的下标, 用于标识该类对象
	 */
	private ChatWindow(User friendUserInfo) {
		initCharWindow();
		$friendUserInfo.add(friendUserInfo);
		
		JPanel container_JPanel = (JPanel)this.getContentPane();
		
		JButton close_JButton = getCloseJButton();
		JLabel move_JLabel = getMoveJLabel(close_JButton.getWidth());
//		JLabel chatObject_JLabel = new JLabel("chatting with " + friendUserInfo.getUserNick() + ".. ");
		JLabel chatObject_JLabel = new JLabel("chatting with " + ".. ");
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
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setLayout(null);
	}
	
	public static ChatWindow createChatWindow(User friendUserInfo) {
		if (friendUserInfo == null) {
			throw new IllegalArgumentException("argument is null ..");
		}
		if ($friendUserInfo.size() > 0) {
			for (User user : $friendUserInfo) {
				if (user.getId().equals(friendUserInfo.getId())) {
					return null;
				}
			}
		}
		return new ChatWindow(friendUserInfo);
	}
	
	public static void main(String[] args) {
		ChatWindow.createChatWindow(null);
	}
}
