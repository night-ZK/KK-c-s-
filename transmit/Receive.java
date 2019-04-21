package transmit;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import customexception.ResponseLineNotAbleExcetion;
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
				resolutionResponseLine();
				
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
				e.printStackTrace();
			}finally {
			}
		}
	}
	
	private void resolutionResponseLine() throws IOException, ResponseLineNotAbleExcetion {
		byte[] responseByte = this.readResponse();
		String responseLine = new String(responseByte, "UTF-8");
		
		String[] responseLineArrays = responseLine.split(" ");
		if (responseLineArrays.length < 3) 
			throw new ResponseLineNotAbleExcetion("ResponseLine length < 3..");
		
		
		String[] responseStateCode = responseLineArrays[0].split(":");
		if (responseStateCode.length < 2) 
			throw new ResponseLineNotAbleExcetion("responseStateCode length < 2..");
		
		switch (responseStateCode[1].trim()) {
		case "200":
			
			break;

			//TODO 
		case "403":
			
			break;
		default:
			break;
		}
		
		String[] responseLength = responseLineArrays[1].split(":");
		if (responseLength.length < 2)
			throw new ResponseLineNotAbleExcetion("responseLength length < 2..");

		String[] responseType = responseLineArrays[2].split(":");
		if (responseType.length < 2)
			throw new ResponseLineNotAbleExcetion("responseType length < 2..");
		switch (responseType[1].trim()) {
		case "MessageModel":
			
			break;

		case "Image":
			
			break;
		default:
			break;
		}
		
		
	}
	/**
	 * 读取响应消息
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private byte[] readResponse() throws IOException{
//		StringBuffer responseLine = new StringBuffer();
		
		//两个字节表示响应消息的长度
		int lengthFirst = is.read();
		int lengthEnd = is.read();
		int length = lengthFirst << 8 + lengthEnd;
		
		byte[] b = new byte[length];
		is.read(b);
//		responseLine.append(new String(b, "UTF-8"));
//		System.out.println("char: " + (char)b);
//		System.out.println("String: "+ new String(b, 0, length, "UTF-8"));
//		responseLine.append(new String(b, 0, length, "UTF-8"));
		
		
		return b;
	}
	
}
