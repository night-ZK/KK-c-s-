package transmit;

import java.awt.Image;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.List;

import message.ErrorMessage;
import message.MessageContext;
import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;
import tablebeans.User;
import tablejson.UserFriendsInformation;
import threadmanagement.ThreadConsole;

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
	
	
	
	
//	/**
//	 * 通过ChatMessage类发送聊天信息给服务器
//	 * @param message
//	 * @return 返回包含发送状态的message
//	 */
//	public static MessageInterface sendChatMessage(ChatMessages message) {
//		
//		ObjectOutputStream oos = null;
//		ErrorMessage errerMessage = null;
//		try {
//			oos = new ObjectOutputStream(SocketClient.getSocket().getOutputStream());
//			oos.writeObject(message);
//			oos.flush();
//			errerMessage = new ErrorMessage(true, "Success Send.");
//		} catch (IOException e) {
//			e.printStackTrace();
//			errerMessage = new ErrorMessage(false, e.getMessage());
//		}finally {
//			try {
//				if (oos != null) {
//					oos.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//				errerMessage = new ErrorMessage(false, "oos close faile.");
//			}
//		}
//		return errerMessage;
//	}
//

//	public static MessageInterface receiveMessage() {
//		ObjectInputStream ois = null;
//		ErrerMessage errerMessage = null;
//		try {
//			ois = new ObjectInputStream(SocketClient.getSocket().getInputStream());
//			ChatMessages messages = (ChatMessages)ois.readObject();
//			ChatWindow.createChatWindow(friendUserInfo);
//			errerMessage = new ErrerMessage(true, "Success Send.");
//		} catch (IOException e) {
//			e.printStackTrace();
//			errerMessage = new ErrerMessage(false, e.getMessage());
//		}finally {
//			try {
//				if (oos != null) {
//					oos.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//				errerMessage = new ErrerMessage(false, "oos close faile.");
//			}
//		}
//		return errerMessage;
//
//	}
}
