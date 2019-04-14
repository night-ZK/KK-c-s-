package transmit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import frame.ChatWindow;
import message.ChatMessages;
import message.ErrorMessage;
import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;
import tablebeans.User;
import threadmanagement.ThreadConsole;

public class MessageManagement {


	public static List<MessageInterface> login(String userName, String password) {
		List<MessageInterface> resultList = new ArrayList<>();
		ErrorMessage errorMessage = null;
		MessageHead messageHead = new MessageHead();
		messageHead.setType(1);
		messageHead.setRequestDataType(User.class);
		//TODO encryption
		String describe = "/login?userName=" + userName + "&" + "password=" + password;
		messageHead.setRequestDescribe(describe);
		messageHead.setRequestTime(System.currentTimeMillis());
		messageHead.setReplyTime(0L);
		messageHead.setReplyRequestResult(false);
		
		GetRequest getRequest = new GetRequest(new MessageModel(messageHead, null));
		MessageModel replyModel = getRequest.getReplyMessageModel();
		ThreadConsole.useThreadPool().execute(getRequest);
		
		MessageHead replyMessageHead = replyModel.getMessageHead();
		
		if (replyMessageHead.getReplyRequestResult()) {
			errorMessage = new ErrorMessage(false, replyMessageHead.getReplyDescribe());
			resultList.add(errorMessage);
			return resultList;
		}
		
		errorMessage = new ErrorMessage(true, replyMessageHead.getReplyDescribe());
		resultList.add(errorMessage);
		resultList.add(replyModel.getMessageContext());
		
		return resultList;
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
