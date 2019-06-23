package frame.customComponent;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

import frame.ChatWindow;

public class ChatJLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat dateFormat;
	private static Color tempColor;
	private Date date;
	
	// 0: self 1: friend
	private int sendType;
//	private String sender;
//	private String nick;
//	private String chatMassage;
	private String messageContext;
	
	static {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tempColor = ChatWindow.getBorderColor();
	}
	
	/**
	 * 
	 * @param sendType 发送者类型
	 * @param nick 发送者昵称
	 * @param chatMessage 发送的消息
	 */
	public ChatJLabel(int sendType, String nick, String chatMessage) {
		this.sendType = sendType;
//		this.nick = nick;
//		this.chatMassage = chatMessage;
		this.date = new Date();
		
		this.messageContext = "<html>" + dateFormat.format(date) 
			+ "&nbsp;&nbsp;&nbsp;" + nick + "<br/>"
			+ "&nbsp;&nbsp;&nbsp;" + chatMessage;
		
		initChatJLable();
	}
	
	public ChatJLabel(int sendType, String nick, String chatMessage, Date date) {
		this.sendType = sendType;
//		this.nick = nick;
//		this.chatMassage = chatMessage;
		this.date = date;
		
		this.messageContext = "<html>" + dateFormat.format(date) 
			+ "&nbsp;&nbsp;&nbsp;" + nick + "<br/>"
			+ "&nbsp;&nbsp;&nbsp;" + chatMessage;
		
		initChatJLable();
	}
	
	private void initChatJLable() {
		this.setText(messageContext);
//		this.setBorder(BorderFactory.createLineBorder(tempColor, 1));
	}

	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(SimpleDateFormat dateFormat) {
		ChatJLabel.dateFormat = dateFormat;
	}

	public static Color getTempColor() {
		return tempColor;
	}

	public static void setTempColor(Color tempColor) {
		ChatJLabel.tempColor = tempColor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
}
