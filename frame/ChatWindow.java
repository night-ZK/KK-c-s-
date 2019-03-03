package frame;

import java.awt.Color;
import java.util.ArrayList;

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
	private static ArrayList<User> $friendUserInfo;
	//�����List�е��±�, ���ڱ�ʶ�������
	private static int $index;
	
	static {
		$friendUserInfo = new ArrayList<User>();
		$index = 0;
	}
	
	/**
	 * ���췽��
	 * @param friendUserInfo ���ѵ��û���Ϣ
	 * @param index �����List�е��±�, ���ڱ�ʶ�������
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
