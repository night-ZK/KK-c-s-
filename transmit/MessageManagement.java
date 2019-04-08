package transmit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import frame.ChatWindow;
import information.ChatMessages;
import information.ErrerMessage;
import information.MessageInterface;

public class MessageManagement {
	
	/**
	 * 通过ChatMessage类发送聊天信息给服务器
	 * @param message
	 * @return 返回包含发送状态的message
	 */
	public static MessageInterface sendChatMessage(ChatMessages message) {
		
		ObjectOutputStream oos = null;
		ErrerMessage errerMessage = null;
		try {
			oos = new ObjectOutputStream(SocketClient.getSocket().getOutputStream());
			oos.writeObject(message);
			oos.flush();
			errerMessage = new ErrerMessage(true, "Success Send.");
		} catch (IOException e) {
			e.printStackTrace();
			errerMessage = new ErrerMessage(false, e.getMessage());
		}finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				errerMessage = new ErrerMessage(false, "oos close faile.");
			}
		}
		return errerMessage;
	}


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
