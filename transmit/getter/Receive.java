package transmit.getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

import customexception.ResponseLineNotAbleExcetion;
import customexception.ServerSendChatMessageException;
import frame.ChatWindow;
import message.ChatMessages;
import message.MessageHead;
import message.MessageModel;
import threadmanagement.ThreadConsole;
import tools.GetterTools;
import tools.ObjectTool;
import tools.TransmitTool;
import tools.client.ThreadTools;
import transmit.SocketClient;

public class Receive implements Runnable{
	//保存接收的数据
	public static Map<String, MessageModel> receiveMap = 
			new HashMap<String, MessageModel>();

	//保存接收的数据
	//key = uid, value = userImage
	public static Map<String, ImageIcon> receiveImageMap = 
			new HashMap<String, ImageIcon>();

	private Socket socket;
	private static Receive receive;
	
	public static boolean isClose;
	
	private Receive(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		InputStream is = null;
		try {
			
			while(true) {
				
//				socket.setSoTimeout(10000);
				is = socket.getInputStream();

				if (!ObjectTool.isNull(is)) {					
					resolutionResponse(is);
				}
			}
			
		} catch (IOException e) {
			try {

				if(!ObjectTool.isNull(receive)) receive = null;
				startReceiveThread();
			} catch (RejectedExecutionException e2) {
				//TODO
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ResponseLineNotAbleExcetion e) {
			e.printStackTrace();
		} finally {
			try {
				if(!ObjectTool.isNull(is)) is.close();
				if(!ObjectTool.isNull(socket)) socket.close();
				System.out.println("this.socket is close: " + socket.isClosed());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void resolutionResponse(InputStream is) throws IOException, ResponseLineNotAbleExcetion, ClassNotFoundException {
		//format: "state:200 length:100 type:MessageModel"
		String responseLine = GetterTools.readResponseLine(is);
		if (ObjectTool.isNull(responseLine))
			return;
		System.out.println("responseLine: " + responseLine);
		
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

		if(!ObjectTool.isInteger(responseLength[1])) {			
			//responseLine
			System.err.println("responseLine: " + responseLine);
			System.err.println("requestLength: " + responseLength[1]);
			throw new ResponseLineNotAbleExcetion("responseLength type not Integer..");
		}
		
		
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
				existJson = Boolean.valueOf(responseExistJson[1].trim());//equals"true"
			} catch (Exception e) {
				//TODO
				e.printStackTrace();
			}
			
			this.readReplyImage(is, existJson, responseLength[1]);
			break;
			
		case "ChatMessage":
			this.forwardChatMessage(is);
			break;
		default:
			break;
		}
		
	}
	
	private void forwardChatMessage(InputStream is) throws IOException, ClassNotFoundException {
		
		MessageModel receiveModel = GetterTools.streamToObjectForClient(is);
		
		try {
			ChatWindow.newsComing(receiveModel);
		} catch (ServerSendChatMessageException e) {
			
			e.printStackTrace();
		}
	}

	private void readReplyImage(InputStream is, Boolean existJson, String responseLength) throws IOException, ResponseLineNotAbleExcetion {
		
		if (existJson) {
			String imageJson = GetterTools.readResponseLine(is);
			String[] imageDescribe = imageJson.split(" ");
			System.out.println("imageJson: " + imageJson);
			if(imageDescribe.length < 2) 
				throw new ResponseLineNotAbleExcetion("imageDescribe length < 2..");
			//key: imageName : userID
			String imageMapKey = imageDescribe[0].split(":")[1].trim();
			String imageByteLength = imageDescribe[1].split(":")[1].trim();
			
			if (!ObjectTool.isInteger(imageByteLength))
				throw new ResponseLineNotAbleExcetion("imageByteLength is not Integer..");
			
//			byte[] imageByte = GetterTools.readImageByteArraysInfoForBig(is, Integer.parseInt(imageByteLength));
			byte[] imageByte = GetterTools.readImageByteArraysInfo(is, Integer.parseInt(imageByteLength));
//			File iFile = new File("G:/image/" + imageMapKey + ".png");
//			FileImageOutputStream fio = new FileImageOutputStream(iFile);
//			
//			fio.write(imageByte,0,imageByte.length);
//			fio.close();
			String endflag = GetterTools.readResponseLine(is);
			System.out.println("endFlag: " + endflag);
			ImageIcon imageIcon = new ImageIcon(imageByte);
			synchronized(receiveImageMap) {
				receiveImageMap.put(imageMapKey, imageIcon);
			}
			
			ThreadTools.notifyRequestThread(imageMapKey);
			
		}else {
			//TODO
			
		}
	}
	
	/**
	 * 读取服务器响应的MessageModel
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readReplyMessageModel(InputStream is) throws ClassNotFoundException, IOException {
		
		MessageModel receiveModel = GetterTools.streamToObjectForClient(is);
		
//		MessageHead messageHead = receiveModel.getMessageHead();
			
		String requestMapKey = TransmitTool.getRequestMapKey(receiveModel);
		
		synchronized(receiveMap) {		
			receiveMap.put(requestMapKey, receiveModel);			
		}
		
		ThreadTools.notifyRequestThread(requestMapKey);
		System.out.println("notify...");
	}
	
	public synchronized static void startReceiveThread() {
		ThreadConsole.useThreadPool().execute(getReceive());
	}
	
	public synchronized static Receive getReceive() {
		if (ObjectTool.isNull(receive)) {
			receive = new Receive(SocketClient.getSocket());
		}
		return receive;
	}

	public Socket getSocket() {
		return socket;
	}
	
}
