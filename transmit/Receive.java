package transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

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

	//保存接收的数据
	//key = uid, value = userImage
	public static Map<String, ImageIcon> receiveImageMap = 
			new HashMap<String, ImageIcon>();
		
	private static Socket socket;
//	protected static OutputStream os;
//	protected static InputStream is;
	
//	protected static ObjectOutputStream oos;
//	protected static ObjectInputStream ois;

	protected MessageHead messageHead;
	protected MessageContext messageContext;
	
	static {
		socket = SocketClient.getSocket();
//		try {
			
//			os = socket.getOutputStream();
//			is = socket.getInputStream();
//			
//			oos = new ObjectOutputStream(os);
//			ois = new ObjectInputStream(is);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	@Override
	public void run() {
		while(true) {			
			InputStream is = null;
			try {
				is = socket.getInputStream();
				resolutionResponseLine(is);
								
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ResponseLineNotAbleExcetion e) {
				e.printStackTrace();
			}finally {
					try {
						if(is != null) is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	private void resolutionResponseLine(InputStream is) throws IOException, ResponseLineNotAbleExcetion, ClassNotFoundException {
		byte[] responseLineByte = this.readResponse(is);
		String responseLine = new String(responseLineByte, "UTF-8");
		
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

		if(ObjectTool.isInteger(responseLength[1])) 
			throw new ResponseLineNotAbleExcetion("responseLength type not Integer..");
		
		
		String[] responseType = responseLineArrays[2].split(":");
		if (responseType.length < 2)
			throw new ResponseLineNotAbleExcetion("responseType length < 2..");
		switch (responseType[1].trim()) {
		case "MessageModel":
			this.readReplyMessageModel(is);
			break;

		case "Image":
			if(responseLineArrays.length < 4) 
				throw new ResponseLineNotAbleExcetion("data is image, but responseLineArrays length < 4..");
			
			String[] responseExistJson = responseLineArrays[3].split(":");
			boolean existJson = false;
			try {				
				existJson = Boolean.valueOf(responseExistJson[1].trim());
			} catch (Exception e) {
				//TODO
				e.printStackTrace();
			}
			
			this.readReplyImage(is, existJson, responseLength[1]);
			break;
		default:
			break;
		}
	}
	
	private void readReplyImage(InputStream is, Boolean existJson, String responseLength) throws IOException, ResponseLineNotAbleExcetion {
		
		byte[] readByte = new byte[Integer.parseInt(responseLength)];
		if (existJson) {
			is.read(readByte);
			String imageJson = new String(readByte);
			String[] imageDescribe = imageJson.split(" ");
			
			if(imageDescribe.length < 2) 
				throw new ResponseLineNotAbleExcetion("imageDescribe length < 2..");
			
			String imageMapKey = imageDescribe[0].split(":")[1].trim();
			String imageByteLength = imageDescribe[1].split(":")[1].trim();
			
			if (!ObjectTool.isInteger(imageByteLength))
				throw new ResponseLineNotAbleExcetion("imageByteLength is not Integer..");
			
			byte[] imageByte = new byte[Integer.parseInt(imageByteLength)];
			is.read(imageByte);
			
			ImageIcon imageIcon = new ImageIcon(imageByte);
			synchronized(receiveImageMap) {
				receiveImageMap.put(imageMapKey, imageIcon);
			}
			
			notifyRequestThread(imageMapKey);
			
		}else {
			//TODO
			
//			is.read(readByte);
//			ImageIcon imageIcon = new ImageIcon(readByte);
		}
	}
	
	/**
	 * 唤醒等待资源的线程
	 * @param requestMapKey
	 */
	private void notifyRequestThread(String requestMapKey) {
		Runnable requestThread = Request.requestMap.get(requestMapKey);
		if (requestThread != null) {
			
			synchronized (requestThread) {
				requestThread.notify();
			}
		}
	}

	/**
	 * 读取服务器响应的MessageModel
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readReplyMessageModel(InputStream is) throws ClassNotFoundException, IOException {
		ObjectInputStream ois = new ObjectInputStream(is);
		MessageModel receiveModel = (MessageModel) ois.readObject();
		
//		if (receiveModel == null) continue;
		
		MessageHead messageHead = receiveModel.getMessageHead();
			
		String requestMapKey = TransmitTool.getRequestMapKey(messageHead);
		
		synchronized(receiveMap) {		
			receiveMap.put(requestMapKey, receiveModel);			
		}
		
		notifyRequestThread(requestMapKey);
	}
	
	/**
	 * 读取响应消息
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private byte[] readResponse(InputStream is) throws IOException{
//		StringBuffer responseLine = new StringBuffer();
		
		//两个字节表示响应消息的长度
		int lengthFirst = is.read();
		int lengthEnd = is.read();
		int length = lengthFirst << 8 + lengthEnd;
		
		byte[] responseLineByte = new byte[length];
		is.read(responseLineByte);
//		responseLine.append(new String(b, "UTF-8"));
//		System.out.println("char: " + (char)b);
//		System.out.println("String: "+ new String(b, 0, length, "UTF-8"));
//		responseLine.append(new String(b, 0, length, "UTF-8"));
		
		return responseLineByte;
	}
	
}
