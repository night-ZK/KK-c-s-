package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import customexception.RequestParameterExcetion;
import message.MessageHead;
import message.MessageModel;
import model.HandlerModel;
import threadmanagement.LockModel;
import threadmanagement.ThreadConsole;
import transmit.Controller.Controller;
import transmit.Controller.KSChatController;
import transmit.Controller.Annotation.HandlerAnnotation.Handler;
import transmit.getter.Receive;
import transmit.nio.SocketClientNIO;

public class TransmitTool {
	//requestLock
	private static Map<String, LockModel> lockModel =
			new HashMap<>();
	
	/**
	 * 获取requestMap的key值
	 * @param messageHead
	 * @return
	 */
	public static String getRequestMapKey(MessageModel messageModel) {
		MessageHead messageHead = messageModel.getMessageHead();
		Integer headType = messageHead.getType();
		Long sendTime = messageHead.getRequestTime();
		Integer requestNo = messageHead.getRequestNO();
		return "Type: " + headType + ", sendTime: " + sendTime
				+ ", requestNo: " + requestNo; 
		
	}
	
	/**
	 * 对象转化成byte[]
	 * @param o
	 * @return
	 * @throws IOException
	 */
	public static byte[] ObjectToByteArrays(Object o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		
		byte[] messageModelByteArrays = baos.toByteArray();
		
		if(!ObjectTool.isNull(oos)) oos.close();
		if(!ObjectTool.isNull(baos)) baos.close();
		
		return messageModelByteArrays;
	}
	
	/**
	 * 通过发送规则发送request
	 * 规则: 4个字节作为消息长度
	 * @param requestModelByteArrays
	 * @return
	 */
	public static ByteBuffer sendRequestForNIOByRule(byte[] requestModelByteArrays) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(requestModelByteArrays.length + 4);
//		System.out.println("length: " + requestModelByteArrays.length);
		byteBuffer.put((byte)(requestModelByteArrays.length >> 24));
		byteBuffer.put((byte)(requestModelByteArrays.length >> 16));
		byteBuffer.put((byte)(requestModelByteArrays.length >> 8));
		byteBuffer.put((byte)requestModelByteArrays.length);
		
		byteBuffer.put(requestModelByteArrays);
		byteBuffer.flip();
		return byteBuffer;
	}
	
	public static ByteBuffer sendRequestForNIO(MessageModel messageModel) throws IOException {
		byte[] bs = ObjectToByteArrays(messageModel);
		return sendRequestForNIOByRule(bs);
	}
	
	/**
	 * 对象转化成byte[]
	 * @param o
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static Object byteArraysToObject(byte[] bs) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object o = ois.readObject();
		
		if(!ObjectTool.isNull(ois)) ois.close();
		if(!ObjectTool.isNull(bais)) bais.close();
		
		return o;
	}
	
	/**
	 * 流转对象 -- nio用
	 * @param o
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static Object channelSteamToObjectForNIO(SocketChannel socketChannel) throws IOException, ClassNotFoundException {

		byte[] b = channelSteamToByteArraysForNIO(socketChannel);
		return byteArraysToObject(b);
	}
	
	/**
	 * 流转化成byte[] -- nio用
	 * @param o
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static byte[] channelSteamToByteArraysForNIO(SocketChannel socketChannel) throws BufferUnderflowException,IOException {
		//读取缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		//从通道中读数据到缓冲区
		socketChannel.read(byteBuffer);
		byteBuffer.flip();
		int length = byteBuffer.getInt();
		System.out.println("length(byteBuffer.getInt()): " + length );
		byteBuffer = ByteBuffer.allocate(length);
		int readLengthSum = 0;
		ByteBuffer readByteBuffer;
		while(readLengthSum < length){
			readByteBuffer = ByteBuffer.allocate(length - readLengthSum);
			int readLength = socketChannel.read(readByteBuffer);
			readByteBuffer.flip();
			byte[] bytes = new byte[readLength];
			readByteBuffer.get(bytes);
			byteBuffer.put(bytes);
			readLengthSum += readLength;
		}
		byteBuffer.flip();
		
		return byteBuffer.array();
	}
	
	/**
	 * 对象转json
	 * @param ob
	 * @return
	 */
	public static String objectToJson(Object ob) {
		String json = "class: " + ob.getClass().getName() + ";" 
				+ ob.toString();
		return json;
	}
	
	/**
	 * json转对象
	 * @param ob
	 * @return
	 */
	public static Object jsonToObject(String json) {
		String[] rs = json.split(";");
		String className = rs[0].split(":")[1].trim();
		try {
			Class<?> cl = Class.forName(className);
			Object o = cl.newInstance();
			String[] fileds = getFields(rs[1]);		
			for(String filed : fileds) {
				String[] kv = filed.split("=");
				String needField = kv[0].trim();
				Field field = cl.getDeclaredField(needField);
				Class<?> fieldType = field.getType();
				field.setAccessible(true);
				field.set(o, fieldType.cast(kv[0]));
			}
			return o;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置对象属性
	 * @param o
	 * @param subJson
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static Object setObject(Class<?> cl, String subJson) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Object o = cl.newInstance();
		String[] fileds = getFields(subJson);		
		for(String filed : fileds) {
			String[] kv = filed.split("=");
			String needField = kv[0].trim();
			Field field = cl.getDeclaredField(needField);
			Class<?> fieldType = field.getType();
			field.setAccessible(true);
			field.set(o, fieldType.cast(kv[0]));
		}
		return o;
		
//		Map<String, String> filedMap = new HashMap<>();
		
//		for (Entry<String, String> fm : filedMap.entrySet()) {
//			String needField = fm.getKey();
//			Field field = cl.getDeclaredField(needField);
//			field.setAccessible(true);
//			field.set(o, fm.getValue());	
//		}		
	}

	/**
	 * 获得表对象中的所有字段, 通过“,”拆分, 并返回一个String类型的数组
	 * @param row 表对象
	 * @return 字段数组
	 */
	public static String[] getFields(String subJson){
		String needRowString = subJson.substring(subJson.indexOf("[")+1, subJson.indexOf("]"));
		return needRowString.split(",");
	}

	public static Map<String, String> analysisRequestParameters(String subRequest) throws RequestParameterExcetion {
		Map<String, String> parametersMap = new HashMap<>();
		String[] parameters = subRequest.split("&");
		
		//TODO 检测parameter中是否包含"时间戳-type:"(拆分用特殊字符&), 包含则替换相应字符
		
		for (String parameter : parameters) {
			String[] pArrays = parameter.split("=");
			
			if (pArrays.length != 2) 
				throw new RequestParameterExcetion("request parameter set error..");
			
			//TODO 检测parameter中是否包含"时间戳-type:"(拆分用特殊字符=), 包含则替换相应字符
			parametersMap.put(pArrays[0].trim(), pArrays[1]);
			
		}
		
		return parametersMap;
	}

	public static Map<String, LockModel> getLockModel() {
		return lockModel;
	}
	
	public static MessageModel getReplyMessageModel(MessageModel requestMessageModel) {
		return Receive.receiveMap.get(getRequestMapKey(requestMessageModel)); 
	}
	
	public static MessageModel getReplyMessageModelForNIO(MessageModel requestMessageModel) {
		return SocketClientNIO.receiveMap.get(getRequestMapKey(requestMessageModel)); 
	}
	
	public static ImageIcon getReplyImageForNIO(String imageKey) {
		return SocketClientNIO.receiveImageMap.get(imageKey); 
	}
	
	
	public static MessageModel sendRequestMessageForNIOByBlock(MessageModel requestMessageModel, LockModel lockModel) throws IOException {
		SocketClientNIO socketClientNIO = SocketClientNIO.createSocketClient();

		if(!socketClientNIO.isAlive()) {			
			socketClientNIO.start();
			synchronized (socketClientNIO) {
				try {
					socketClientNIO.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				System.out.println("succeed connect..");
			}
		}
		
		String lockKey = TransmitTool.getRequestMapKey(requestMessageModel);
		
		TransmitTool.getLockModel().put(lockKey, lockModel);
		

		synchronized (socketClientNIO) {			
			socketClientNIO.sendReuqest(requestMessageModel);
		}
		
		ThreadConsole.blockThread(lockModel, socketClientNIO);
		
		return TransmitTool.getReplyMessageModelForNIO(requestMessageModel);
	}
	
	public static ImageIcon sendImageRequestMessageForNIOByBlock(MessageModel requestMessageModel
			, LockModel lockModel, String imageKey) throws IOException {
		SocketClientNIO socketClientNIO = SocketClientNIO.createSocketClient();

		if(!socketClientNIO.isAlive())
			socketClientNIO.start();
		
//		String lockKey = TransmitTool.getRequestMapKey(requestMessageModel);
		
		TransmitTool.getLockModel().put(imageKey, lockModel);
		
		synchronized (socketClientNIO) {			
			socketClientNIO.sendReuqest(requestMessageModel);
		}
		
		ThreadConsole.blockThread(lockModel, socketClientNIO);
		
		return TransmitTool.getReplyImageForNIO(imageKey);
	}
	
	/**
	 * 不阻塞
	 * @param requestMessageModel
	 * @throws IOException
	 */
	public static void sendMessageForNIO(MessageModel requestMessageModel) throws IOException {

		SocketClientNIO socketClientNIO = SocketClientNIO.createSocketClient();
		if(!socketClientNIO.isAlive()) {			
			socketClientNIO.start();
			synchronized (socketClientNIO) {
				try {
					socketClientNIO.wait();
					System.out.println("succeed connect..");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		synchronized (socketClientNIO) {			
			socketClientNIO.sendReuqest(requestMessageModel);
		}
		
	}

	public static void handlByHandler(String responseType
			, String[] responseLineArrays
			, SocketChannel socketChannel) throws IllegalAccessException 
											, IllegalArgumentException
											, InvocationTargetException
											, InstantiationException
											, NoSuchMethodException
											, SecurityException {
		Class<? extends Controller> cls = KSChatController.class;
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			Handler handler = method.getAnnotation(Handler.class);
			if (handler.responseType().equals(responseType.trim())) {
//				System.out.println("Class: " + String[].class + ",String.c: " + String.class);
				Constructor<? extends Controller> constructor = cls.getConstructor(HandlerModel.class);
				HandlerModel handlerModel = new HandlerModel();
				handlerModel.setResponseLineParameter(responseLineArrays);
				method.invoke(constructor.newInstance(handlerModel), socketChannel);
				return;
			}
		}
	}
	    
}
