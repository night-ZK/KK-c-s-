package transmit.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;

import org.dom4j.Element;

import customexception.ResponseLineNotAbleExcetion;
import frame.ClientLogin;
import message.MessageModel;
import parsefile.ParseXML;
import tools.ObjectTool;
import tools.TransmitTool;

public class SocketClientNIO extends Thread{
	
	private static ParseXML parseXML;
//	private static String serverID = "001";
	private static String host;
	private static int port;
	
	//保存接收的数据
	public static Map<String, MessageModel> receiveMap;
	
	//保存接收的数据
	//key = uid, value = userImage
	public static Map<String, ImageIcon> receiveImageMap;
	
//	static class SocketInstance{		
//		private static SocketClientNIO socketClientNIO = new SocketClientNIO();;
//	}
	
	private volatile static SocketClientNIO socketClientNIO;
	
//	public enum SocketInstance{
//		socketClientNIO;
//	}
	static {
		parseXML = ParseXML.createParseXML();
		Element element = parseXML.getServerXMLElement(ClientLogin.get_serverID());
		host = element.elementText("host-address");
		port = Integer.parseInt(element.elementText("port"));
		
		receiveMap = new HashMap<String, MessageModel>();
		receiveImageMap = new HashMap<String, ImageIcon>();
	}
	
	
	Selector selector;
	private SocketChannel socketChannel;
	SelectionKey selectionKey;
	
	public void resetServerInfo(String serverID) throws IOException {
		Element element = parseXML.getServerXMLElement(serverID);
		host = element.elementText("host-address");
		port = Integer.parseInt(element.elementText("port"));
		
		if (!ObjectTool.isNull(selectionKey)) selectionKey.cancel();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress(host, port));
		
		selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
	}
	
	private SocketClientNIO() {
		try {
			
			this.selector = Selector.open();
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress(host, port));
			
			selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SocketClientNIO createSocketClient() {
//		return SocketClientNIO.SocketInstance.socketClientNIO;
		//dcl
//		if (socketClientNIO == null) {
//			synchronized (SocketClientNIO.class) {
//				if (socketClientNIO == null) {
//					socketClientNIO = new SocketClientNIO();
//				}
//			}
//		}
		
		synchronized (SocketClientNIO.class) {
			if (socketClientNIO == null) {
				socketClientNIO = new SocketClientNIO();
			}
		}
		return socketClientNIO;
	}
	
	public void destroy() {
		try {
			selector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		socketClientNIO = null;
	}
	
	public SocketChannel getSocketChannel() {
		return socketChannel;
	}
	
	@Override
	public void run() {
		startSocketClient();
	}
	
	public void startSocketClient(){
		try{
			while(true){
				
//				if (isClose) return;
				
				//有事件则返回, 无则阻塞
				selector.select();
				
				Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
				while (iterator.hasNext()){
					SelectionKey selectionKey = (SelectionKey)iterator.next();
					iterator.remove();//删除已做处理的key
					if(selectionKey.isConnectable()){
						//是连接事件
						//所属key的服务器通道
						socketChannel = (SocketChannel) selectionKey.channel();
						
						if (socketChannel.isConnectionPending()) {
							socketChannel.finishConnect();
						}
						
						//该通道设置为非阻塞
						socketChannel.configureBlocking(false);
						
						//为该通道注册读数据的监听权限
						socketChannel.register(selector, SelectionKey.OP_READ);
						
						synchronized (socketClientNIO) {
							socketClientNIO.notify();
						}
						
						//TODO write data
					}else if(selectionKey.isReadable()){
						System.out.println("receive data..");
						
						byte[] byteArrays = null;
						//缓冲区转byte[]
						try {
							
							byteArrays = TransmitTool.channelSteamToByteArraysForNIO(socketChannel);
						} catch (IOException e) {
							socketChannel.socket().close();
							socketChannel.close();
							return;
						}
						
						try {
							String responseLine = new String(byteArrays, "UTF-8");
							System.out.println("responseLine: " + responseLine);
							if (ObjectTool.isNull(responseLine))
								return;
							
							if (responseLine.equals("state:close")) return;
							
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
							
							TransmitTool.handlByHandler(responseType[1]
									, responseLineArrays
									, socketChannel);
							
//							switch (responseType[1].trim()) {
//							case "MessageModel":
//								this.readReplyMessageModel(socketChannel);
//								break;
//								
//							case "Image":
//								if(responseLineArrays.length < 4) 
//									throw new ResponseLineNotAbleExcetion("data is image, but responseLineArrays length < 4..");
//								
//								String[] responseExistJson = responseLineArrays[3].split(":");
//								boolean existJson = false;
//								try {				
//									existJson = Boolean.valueOf(responseExistJson[1].trim());//equals"true"
//								} catch (Exception e) {
//									//TODO
//									e.printStackTrace();
//								}
//								
//								this.readReplyImage(socketChannel, existJson);
//								break;
//								
//							case "ChatMessage":
//								this.forwardChatMessage(socketChannel);
//								break;
//							default:
//								break;
//							}
							
						}catch (IOException e){
							e.printStackTrace();
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			socketClientNIO = null;
		}finally {
//			try {
//				socketChannel.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	
//	private void forwardChatMessage(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
//		
//		MessageModel receiveModel = (MessageModel) TransmitTool.channelSteamToObjectForNIO(socketChannel);
//		
//		try {
//			ChatWindow.newsComing(receiveModel);
//		} catch (ServerSendChatMessageException e) {
//			
//			e.printStackTrace();
//		}
//	}
	
//	private void readReplyImage(SocketChannel socketChannel, boolean existJson) throws IOException, ResponseLineNotAbleExcetion {
//		if (existJson) {
//			byte[] receiveByte = TransmitTool.channelSteamToByteArraysForNIO(socketChannel);
//			String imageJson = new String(receiveByte, "UTF-8");
//			
//			String[] imageDescribe = imageJson.split(" ");
//			System.out.println("imageJson: " + imageJson);
//			if(imageDescribe.length < 2) 
//				throw new ResponseLineNotAbleExcetion("imageDescribe length < 2..");
//			//key: imageName : userID
//			String imageMapKey = imageDescribe[0].split(":")[1].trim();
//			String imageByteLength = imageDescribe[1].split(":")[1].trim();
//			
//			if (!ObjectTool.isInteger(imageByteLength))
//				throw new ResponseLineNotAbleExcetion("imageByteLength is not Integer..");
//			
//			System.out.println("imageLength: " + imageByteLength);
//			int imageSize = Integer.parseInt(imageByteLength);
//			ByteBuffer buffer = ByteBuffer.allocate(imageSize);
////			int readLength = socketChannel.read(buffer);
////			System.out.println("imagereadLength: " + readLength);
//			int readLengthSum = 0;
//			ByteBuffer readByteBuffer;
//			while(readLengthSum < imageSize){
//				readByteBuffer = ByteBuffer.allocate(imageSize - readLengthSum);
//				int readLength = socketChannel.read(readByteBuffer);
//				readByteBuffer.flip();
//				byte[] bytes = new byte[readLength];
//				readByteBuffer.get(bytes);
//				buffer.put(bytes);
//				readLengthSum += readLength;
//			}
//			
//			buffer.flip();
//			byte[] imageByte = buffer.array();
////			File iFile = new File("G:/image/" + imageMapKey + ".png");
////			FileImageOutputStream fio = new FileImageOutputStream(iFile);
////			
////			fio.write(imageByte,0,imageByte.length);
////			fio.close();
//			ImageIcon imageIcon = new ImageIcon(imageByte);
//			synchronized(receiveImageMap) {
//				receiveImageMap.put(imageMapKey, imageIcon);
//			}
//			
//			ThreadTools.notifyRequestThread(imageMapKey);
//			
//		}else {
//			//TODO
//			
//		}
//	}
	
//	private void readReplyMessageModel(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
//		
//		MessageModel receiveModel = (MessageModel)TransmitTool.channelSteamToObjectForNIO(socketChannel);
////		MessageHead messageHead = receiveModel.getMessageHead();
//		
//		String requestMapKey = TransmitTool.getRequestMapKey(receiveModel);
//		
//		synchronized(receiveMap) {		
//			receiveMap.put(requestMapKey, receiveModel);			
//		}
//		
//		ThreadTools.notifyRequestThread(requestMapKey);
//		System.out.println("notify...");
//	}
	
	public void sendReuqest(MessageModel messageModel) throws IOException {
		
		ByteBuffer byteBuffer = TransmitTool.sendRequestForNIO(messageModel);
		socketChannel.write(byteBuffer);
	}
}
