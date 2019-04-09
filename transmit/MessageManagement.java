package transmit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import frame.ChatWindow;
import message.ChatMessages;
import message.ErrorMessage;
import message.MessageHead;
import message.MessageInterface;
import tablebeans.User;

public class MessageManagement {


	public static ErrorMessage login(String userName, String password) {
		ErrorMessage errorMessage = null;
		MessageHead messageHead = new MessageHead();
		messageHead.setType(1);
		messageHead.setRequestDataType(User.class);
		//TODO encryption
		String describe = "/login?userName=" + userName + "&" + "password=" + password;
		messageHead.setRequestDescribe(describe);
		errorMessage = SendMessage.get(messageHead);
		return errorMessage;
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
