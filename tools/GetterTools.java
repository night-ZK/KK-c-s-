package tools;

import java.io.IOException;
import java.io.InputStream;

import message.MessageContext;
import message.MessageHead;
import message.MessageModel;

public class GetterTools extends TransmitTool{
	
	/**
	 * 使用InputStream读取socket输入流中信息, 返回byte[]
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] readByteArraysInfo(InputStream is) throws IOException{
		//两个字节表示响应消息的长度
		int lengthFirst = is.read();
		
		if (lengthFirst == -1) {
			return null;
		}
		
		int lengthEnd = is.read();
		int length = (lengthFirst << 8) + lengthEnd;
		
		byte[] responseLineByte = new byte[length];
		int readLength = 0;
		while(readLength < length) {
			readLength += is.read(responseLineByte, readLength, length - readLength);	
		}
		return responseLineByte;
	}
	
	/**
	 * 使用InputStream读取socket输入流中信息, 返回byte[]--image用
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] readImageByteArraysInfo(InputStream is, int byteLength) throws IOException{
		byte[] imageByte = new byte[byteLength];
		int readLength = 0;
		while (byteLength > readLength) {			
			readLength += is.read(imageByte, readLength, byteLength - readLength);	
		}
		return imageByte;
	}
	
	/**
	 * 使用InputStream读取socket输入流中信息, 返回byte[]--image用,
	 * 分割图片传输数据--停用
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static synchronized byte[] readImageByteArraysInfoForBig(InputStream is, int byteLength) throws IOException{
		int splitLength = 10240;
		int p = byteLength / splitLength;
		byte[] imageByte = new byte[byteLength];
		byte[] subImageByte;
		if (p >= 1) {
			for(int i=0; i<p; i++) {
				subImageByte = new byte[splitLength];
				is.read(subImageByte);
				System.arraycopy(subImageByte, 0, imageByte, i*splitLength, splitLength);
			}
			subImageByte = new byte[byteLength-p*splitLength];
			is.read(subImageByte, 0, byteLength-p*splitLength);
			System.arraycopy(subImageByte, 0, imageByte, p*splitLength, subImageByte.length);
			return imageByte;
		}
		is.read(imageByte);	
		return imageByte;
	}

	/**
	 * 读取响应行
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readResponseLine(InputStream is) throws IOException{
		byte[] responseLineByte = readByteArraysInfo(is);
		
		if (ObjectTool.isNull(responseLineByte)) {
			return null;
		}
		
		return new String(responseLineByte, "UTF-8");
	}
	
	/**
	 * 将IO流中的byte[]转换成对象--client用
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static MessageModel streamToObjectForClient(InputStream is) throws IOException, ClassNotFoundException {
		byte[] requestBytes = readByteArraysInfo(is);
		
		if (ObjectTool.isNull(requestBytes)) {
			return null;
		}
		MessageModel messageModel = (MessageModel)byteArraysToObject(requestBytes);
		
		return messageModel;
	}
	
	/**
	 * 将IO流中的byte[]转换成对象--service用
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static MessageModel streamToObject(InputStream is) throws IOException, ClassNotFoundException {
		byte[] requestBytes = readByteArraysInfo(is);
		
		if (ObjectTool.isNull(requestBytes)) {
			return null;
		}
		
		MessageHead messageHead = (MessageHead)byteArraysToObject(requestBytes);
		
		System.out.println("messageHead: " + messageHead);
		MessageContext messageContext = null;
		
		if (!ObjectTool.isEmpty(messageHead) && messageHead.isHasMessageContext()) {
			requestBytes = GetterTools.readByteArraysInfo(is);
			messageContext = (MessageContext)TransmitTool.byteArraysToObject(requestBytes);
		}
		return new MessageModel(messageHead, messageContext);
	}
	
}
