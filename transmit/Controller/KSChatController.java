package transmit.Controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.ImageIcon;

import customexception.ResponseLineNotAbleExcetion;
import customexception.ServerSendChatMessageException;
import frame.ChatWindow;
import message.MessageModel;
import model.HandlerModel;
import tools.ObjectTool;
import tools.TransmitTool;
import tools.client.ThreadTools;
import transmit.Controller.Annotation.HandlerAnnotation.Handler;
import transmit.nio.SocketClientNIO;

public class KSChatController implements Controller{
    private String[] responseLineParameter;
    public KSChatController(HandlerModel handlerModel){
        this.responseLineParameter = handlerModel.getResponseLineParameter();
    }

    @Handler(responseType = "MessageModel")
    public void messageModelHandler(SocketChannel socketChannel) throws ClassNotFoundException, IOException{
    	MessageModel receiveModel = (MessageModel)TransmitTool.channelSteamToObjectForNIO(socketChannel);
//		MessageHead messageHead = receiveModel.getMessageHead();
		
		String requestMapKey = TransmitTool.getRequestMapKey(receiveModel);
		
		synchronized(SocketClientNIO.receiveMap) {		
			SocketClientNIO.receiveMap.put(requestMapKey, receiveModel);			
		}
		
		ThreadTools.notifyRequestThread(requestMapKey);
//		System.out.println("notify...");
    }

    @Handler(responseType = "Image")
    public void imageHandler(SocketChannel socketChannel) throws ResponseLineNotAbleExcetion, IOException{
    	if(responseLineParameter.length < 4) 
			throw new ResponseLineNotAbleExcetion("data is image, but responseLineArrays length < 4..");
		
		String[] responseExistJson = responseLineParameter[3].split(":");
		boolean existJson = false;
		try {				
			existJson = Boolean.valueOf(responseExistJson[1].trim());//equals"true"
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
		}
		
		if (existJson) {
			byte[] receiveByte = TransmitTool.channelSteamToByteArraysForNIO(socketChannel);
			String imageJson = new String(receiveByte, "UTF-8");
			
			String[] imageDescribe = imageJson.split(" ");
			System.out.println("imageJson: " + imageJson);
			if(imageDescribe.length < 2) 
				throw new ResponseLineNotAbleExcetion("imageDescribe length < 2..");
			//key: imageName : userID
			String imageMapKey = imageDescribe[0].split(":")[1].trim();
			String imageByteLength = imageDescribe[1].split(":")[1].trim();
			
			if (!ObjectTool.isInteger(imageByteLength))
				throw new ResponseLineNotAbleExcetion("imageByteLength is not Integer..");
			
			int imageSize = Integer.parseInt(imageByteLength);
			ByteBuffer buffer = ByteBuffer.allocate(imageSize);
			int readLengthSum = 0;
			ByteBuffer readByteBuffer;
			while(readLengthSum < imageSize){
				readByteBuffer = ByteBuffer.allocate(imageSize - readLengthSum);
				int readLength = socketChannel.read(readByteBuffer);
				readByteBuffer.flip();
				byte[] bytes = new byte[readLength];
				readByteBuffer.get(bytes);
				buffer.put(bytes);
				readLengthSum += readLength;
			}
			
			buffer.flip();
			byte[] imageByte = buffer.array();
//			File iFile = new File("G:/image/" + imageMapKey + ".png");
//			FileImageOutputStream fio = new FileImageOutputStream(iFile);
//			
//			fio.write(imageByte,0,imageByte.length);
//			fio.close();
			ImageIcon imageIcon = new ImageIcon(imageByte);
			synchronized(SocketClientNIO.receiveImageMap) {
				SocketClientNIO.receiveImageMap.put(imageMapKey, imageIcon);
			}
			
			ThreadTools.notifyRequestThread(imageMapKey);
			
		}else {
			//TODO
			
		}
    }
    
    @Handler(responseType = "ChatMessage")
    public void chatMessageHandler(SocketChannel socketChannel) throws ClassNotFoundException, IOException{
    	MessageModel receiveModel = (MessageModel) TransmitTool.channelSteamToObjectForNIO(socketChannel);
		
		try {
			ChatWindow.newsComing(receiveModel);
		} catch (ServerSendChatMessageException e) {
			
			e.printStackTrace();
		}
    }
}
