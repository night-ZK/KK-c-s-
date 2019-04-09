package transmit;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import message.ErrorMessage;
import message.MessageContext;
import message.MessageHead;

/**
 * 用于发送请求
 * @author zxk
 *
 */
public abstract class SendMessage {
	
	public static ErrorMessage get(MessageHead messageHead) {
		ObjectOutputStream oos = null;
		ErrorMessage errerMessage = null;
		Socket socket = SocketClient.getSocket();
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(messageHead);
			oos.flush();
			errerMessage = new ErrorMessage(true, "Success Send.");
		} catch (IOException e) {
			e.printStackTrace();
			errerMessage = new ErrorMessage(false, e.getMessage());
		}finally {
//			System.out.println("socket wait close..");
			try {
				if (oos != null) {
					oos.close();
				}
//				socket.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
				errerMessage = new ErrorMessage(false, "oos close faile.");
			}
		}
		return errerMessage;
	}
//	
//	
//	
//	/**
//	 * 发送消息头
//	 */
//	public abstract ErrorMessage sendMessageHead();
//	
//	/**
//	 * 发送消息正文
//	 */
//	public abstract ErrorMessage sendMessageContext();
}
