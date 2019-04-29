package transmit;

import java.awt.Image;
import java.util.List;

import message.ChatMessages;
import message.MessageHead;
import message.MessageModel;
import tablebeans.User;
import tablejson.UserFriendsInformation;

public class MessageManagement {

	/**
	 * 登录请求
	 * @param userName
	 * @param password
	 * @return
	 */
	public static MessageModel loginMessageModel(String userName, String password) {
		
		MessageHead messageHead = new MessageHead();
		messageHead.setType(1);
		messageHead.setRequestDataType(User.class);
		//TODO encryption
		String describe = "/login?userName=" + userName + "&" + "password=" + password;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		
		return new MessageModel(messageHead, null);
		
	}

	/**
	 * 获取好友ID
	 * @param userID 当前用户ID
	 * @return
	 */
	public static MessageModel getFrindIDMessageModel(int userID) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(2);
		messageHead.setRequestDataType(List.class);
		
		String describe = "/getFrindID?userID=" + userID;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		
		return new MessageModel(messageHead, null);
	}

	public static MessageModel getUserFriendInfoMessageModel(Integer friendID) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(3);
		messageHead.setRequestDataType(UserFriendsInformation.class);
		
		String describe = "/getUserFriendInfo?userID=" + friendID;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		
		return new MessageModel(messageHead, null);
	}

	public static MessageModel getUserFriendImageMessageModel(Integer friendID) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(4);
		messageHead.setRequestDataType(Image.class);
		
		String describe = "/getUserFriendImage?userID=" + friendID;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		
		return new MessageModel(messageHead, null);
	}
	
	public static MessageModel chatMessageModel(ChatMessages chatMessage) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(5);
		messageHead.setRequestDataType(Object.class);
		
		String describe = "/sendChatMessage";
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		
		return new MessageModel(messageHead, chatMessage);
	}

}
