package transmit;

import java.awt.Image;
import java.util.List;

import message.ChatMessages;
import message.MessageContext;
import message.MessageHead;
import message.MessageModel;
import model.UpdateInformation;
import tablebeans.User;
import tablejson.UserFriendsInformation;
import tools.SenderTools;

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
		messageHead.setRequestNO(SenderTools.getRequestNo());
		
		return new MessageModel(messageHead, null);
		
	}

	/**
	 * 获取好友ID, Integer类型List
	 * @param userID 当前用户ID
	 * @return
	 */
	public static MessageModel getFriendsIDMessageModel(int userID, String group) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(2);
		messageHead.setRequestDataType(List.class);
		
		String describe = "/getFriendsID?userID=" + userID + "&group=" + group;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		return new MessageModel(messageHead, null);
	}

	public static MessageModel getUserFriendInfoMessageModel(Integer friendID) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(3);
		messageHead.setRequestDataType(UserFriendsInformation.class);
		
		String describe = "/getUserFriendInfo?userID=" + friendID;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		return new MessageModel(messageHead, null);
	}

	public static MessageModel getUserFriendImageMessageModel(Integer friendID) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(4);
		messageHead.setRequestDataType(Image.class);
		
		String describe = "/getUserFriendImage?userID=" + friendID;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		return new MessageModel(messageHead, null);
	}
	
	public static MessageModel chatMessageModel(ChatMessages chatMessage) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(5);
		messageHead.setRequestDataType(ChatMessages.class);
		
		String describe = "/sendChatMessage";
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		messageHead.setHasMessageContext(true);
		return new MessageModel(messageHead, chatMessage);
	}

	public static MessageModel getUserFriendInfoListMessageModel(int userID, String group) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(6);
		messageHead.setRequestDataType(List.class);
		
		String describe = "/getFriendsInfoList?userID=" + userID + "&group=" + group;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		return new MessageModel(messageHead, null);
	}
	
	public static MessageModel getCloseMessageModel(int userId, List<Integer> friendsId) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(7);
		messageHead.setRequestDataType(String.class);
		
		String describe = "/" + userId;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		MessageContext messageContext = new MessageContext();
		messageContext.setObject(friendsId);
		return new MessageModel(messageHead, messageContext);
	}
	
	public static MessageModel getUpdateUserInfoMessageModel(UpdateInformation updateInformation) {
		MessageHead messageHead = new MessageHead();
		messageHead.setType(8);
		messageHead.setRequestDataType(String.class);
		
		String describe = "/updateUserInfo";
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setRequestNO(SenderTools.getRequestNo());
		MessageContext messageContext = new MessageContext();
		messageContext.setObject(updateInformation);
		return new MessageModel(messageHead, messageContext);
	}

}
