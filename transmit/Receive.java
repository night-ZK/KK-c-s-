package transmit;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import message.MessageContext;
import message.MessageHead;
import message.MessageModel;
import tools.ObjectTool;
import tools.TransmitTool;

public class Receive implements Runnable{
	//保存接收的数据
	public static Map<String, MessageModel> receiveMap = 
			new HashMap<String, MessageModel>();
	
	private Socket socket;
	protected MessageHead messageHead;
	protected MessageContext messageContext;
	protected OutputStream os;
	protected InputStream is;
	
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	
	public Receive() {
		try {
			this.socket = SocketClient.getSocket();
			
			this.os = socket.getOutputStream();
			this.is = socket.getInputStream();
			
			this.oos = new ObjectOutputStream(os);
			this.ois = new ObjectInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public synchronized void run() {
		while(true) {			
			try {
				MessageModel receiveModel = (MessageModel) this.ois.readObject();

				if (receiveModel == null) continue;
				
				this.messageHead = receiveModel.getMessageHead();
				
				String requestMapKey = TransmitTool.getRequestMapKey(this.messageHead);
				
				if (messageHead.getReplyDataType().isAssignableFrom(Image.class)) {
					
				}
				
				receiveMap.put(requestMapKey, receiveModel);
				
				Runnable requestThread = Request.requestMap.get(requestMapKey);
				
				if (requestThread != null) {
					requestThread.notify();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
			}
		}
	}
	
}
