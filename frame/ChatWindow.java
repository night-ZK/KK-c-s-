package frame;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tablebeans.User;

/**
 * ���촰��
 * @author zxk
 *
 */
public class ChatWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	//���ڴ�ź�����Ϣ��List
	private static HashMap<Number, User> $friendUserInfoHashMap;
	//���ڱ�ʶ�������
	private static int $index;
	//��¼�������id, ���ڱ�ʶ���ڶ���
	private Number _index;
	
	static {
		$friendUserInfoHashMap = new HashMap<Number, User>();
		$index = 0;
	}
	
	/**
	 * ���췽��
	 * @param friendUserInfo ���ѵ��û���Ϣ
	 * @param _index �����List�е��±�, ���ڱ�ʶ�������
	 */
	private ChatWindow(User friendUserInfo) {
		initCharWindow();
		//��¼�������id, ���ڱ�ʶ���ڶ���
		_index = friendUserInfo.getId();
		//�û�IDΪkey
		$friendUserInfoHashMap.put(_index, friendUserInfo);
		//���������1
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
		
		//������ʾ������JPanel
		JPanel tools_JPanel = new JPanel();
		int chatwidth_Temp = 480;
		tools_JPanel.setBounds(0, 30, chatwidth_Temp, 30);
		tools_JPanel.setBackground(new Color(100, 200, 100));
		
		//������ʾ������Ϣ��JPanel
		JPanel chatMessage_JPanel = new JPanel();
		chatMessage_JPanel.setBounds(0, 60, chatwidth_Temp, 310);
		chatMessage_JPanel.setBackground(new Color(0, 100, 200));
		
		//������ʾ���칤������JPanel
		JPanel chatTools_JPanel = new JPanel();
		chatTools_JPanel.setBounds(0, 370, chatwidth_Temp, 30);
		chatTools_JPanel.setBackground(new Color(100, 0, 160));
		
		//������ʾ��������JPanel
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
	 * @param friendUserInfo
	 * @return
	 */
	public static ChatWindow createChatWindow(User friendUserInfo) {
		if (friendUserInfo == null) {
			throw new IllegalArgumentException("argument is null ..");
		}
		if ($friendUserInfoHashMap.size() > 0) {
			//Map.Entry����map
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

	public void set_index(Number _index) {
		this._index = _index;
	}

//	public static void main(String[] args) {
//		ChatWindow.createChatWindow(null);
//	}
}
