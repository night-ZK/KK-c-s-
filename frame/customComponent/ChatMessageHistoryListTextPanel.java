package frame.customComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import model.ChatMessageInformation;

public class ChatMessageHistoryListTextPanel extends ChatMessageTextJPanel{
	
	private static final long serialVersionUID = 1L;
	private ConcurrentLinkedQueue<ChatMessageInformation> chatMessageInfoQueue;
	private ConcurrentHashMap<Integer, Image> senderImageIconMap;
	private Color otherMessageColor;
	private Color otherMessageBorderColor; 
	private Color selfMessageColor;
	private Color selfMessageBorderColor;
	
	public ChatMessageHistoryListTextPanel() {
		otherMessageColor = new Color(188, 237, 245);
		otherMessageBorderColor = new Color(156, 205, 213); 
		selfMessageColor = new Color(230, 230, 230);
		selfMessageBorderColor = new Color(198, 198, 198);
		
		setEditable(false);
		setOpaque(false);
	}

//	@Override
//	protected void paintComponent(Graphics g) {
//		Graphics2D g2D = (Graphics2D)g;
//		//反锯齿效果
//		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		if (chatMessageInfoQueue != null) {
//			Iterator<ChatMessageInformation> iterator = chatMessageInfoQueue.iterator();
//			while(iterator.hasNext()) {
//				ChatMessageInformation chatMessageInformation = iterator.next();
//				Point point = chatMessageInformation.getMessagePaintleftTop();
//				
//				if (point != null && senderImageIconMap != null) {
//					//绘制消息发送者头像
//					Image image = senderImageIconMap.get(chatMessageInformation.getSenderID());
//					if (image != null) {
//						if (chatMessageInformation.getIsSelfSend()) {
//
//							g2D.drawImage(image, 9, point.y - 25, null);
//						}else {
//							g2D.drawImage(image
//									, this.getWidth() - image.getWidth(null) - 9
//									, point.y - 25, null);
//						}
//					}
//					
//					if (chatMessageInformation.getIsSelfSend()) {
//						g2D.setColor(selfMessageColor);
//						g2D.fillRoundRect(point.x - 7, point.y - 7
//								, chatMessageInformation.getMessagePaintWidth() + 14
//								, chatMessageInformation.getMessagePaintHeight() + 14
//								, 10, 10);
//						
//						g2D.setColor(selfMessageBorderColor);
//						//边框
//						g2D.drawRoundRect(point.x - 7, point.y - 7
//								, chatMessageInformation.getMessagePaintWidth() + 14
//								, chatMessageInformation.getMessagePaintHeight() + 14
//								, 10, 10);
//						
//						//TODO 箭头
//					}else {
//						g2D.setColor(otherMessageColor);
//						g2D.fillRoundRect(point.x - 7, point.y - 7
//								, chatMessageInformation.getMessagePaintWidth() + 14
//								, chatMessageInformation.getMessagePaintHeight() + 14
//								, 10, 10);
//						
//						g2D.setColor(otherMessageBorderColor);
//						//边框
//						g2D.drawRoundRect(point.x - 7, point.y - 7
//								, chatMessageInformation.getMessagePaintWidth() + 14
//								, chatMessageInformation.getMessagePaintHeight() + 14
//								, 10, 10);
//						
//						//TODO 箭头
//					}
//					
//				}
//			}
//		}
//		
//		super.paintComponent(g);
//	}

//	public ConcurrentLinkedQueue<ChatMessageInformation> getChatMessageInfoQueue() {
//		return chatMessageInfoQueue;
//	}

//	public void setChatMessageInfoQueue(ConcurrentLinkedQueue<ChatMessageInformation> chatMessageInfoQueue) {
//		this.chatMessageInfoQueue = chatMessageInfoQueue;
//	}
//
//	public ConcurrentHashMap<Integer, Image> getSenderImageIconMap() {
//		return senderImageIconMap;
//	}
//
//	public void setSenderImageIconMap(ConcurrentHashMap<Integer, Image> senderImageIconMap) {
//		this.senderImageIconMap = senderImageIconMap;
//	}
	
	
	
}
