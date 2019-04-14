package transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import message.ErrorMessage;
import message.MessageContext;
import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;
import tools.ObjectTool;
import tools.TransmitTool;

/**
 * ���ڷ�������
 * @author zxk
 *
 */
public abstract class Request implements Runnable{
	
	//�����������ϸ��Ϣ, ���ڱ�ʶ
	public static Map<String, Runnable> requestMap = 
			new HashMap<String, Runnable>(); 
	
	private Socket socket;
	protected MessageHead messageHead;
	protected MessageContext messageContext;
	
	protected MessageModel model;
	
	protected OutputStream os;
	protected InputStream is;
	
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	
	protected Request(MessageModel model) {
		try {
			this.socket = SocketClient.getSocket();
			this.model = model;
			
			if(ObjectTool.isDoable(model))
				throw new NullPointerException("model is no doable..");

			this.messageHead = model.getMessageHead();
			this.messageContext = model.getMessageContext();
			
			if(ObjectTool.isDoable(messageHead))
				throw new NullPointerException("messageHead is no doable..");

			
			this.os = socket.getOutputStream();
			this.is = socket.getInputStream();
			
			this.oos = new ObjectOutputStream(os);
			this.ois = new ObjectInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void run() {
		
//		this.sendMessageHead();
//		this.sendMessageContext();
		
		
		try {
			this.sendMessageModel();
			
			String requestMapKey = TransmitTool.getRequestMapKey(this.messageHead);
			
			requestMap.put(requestMapKey, this);
			
			this.wait();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	protected abstract void sendMessageModel() throws IOException; 
	
	protected abstract MessageModel getReplyMessageModel();
	
	protected abstract void sendMessageHead();

	protected abstract void sendMessageContext();
//	
//	
//	
//	/**
//	 * ������Ϣͷ
//	 */
//	public abstract ErrorMessage sendMessageHead();
//	
//	/**
//	 * ������Ϣ����
//	 */
//	public abstract ErrorMessage sendMessageContext();
}
