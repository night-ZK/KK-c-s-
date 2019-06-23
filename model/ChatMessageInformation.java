package model;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.concurrent.ConcurrentLinkedQueue;

import message.ChatMessages;

public class ChatMessageInformation {
	
	private String message;
	private Font font;
	
	private Integer senderID;
	private Boolean isSelfSend;
	
	private int currentCompentWidth;
	
	private Graphics g;

	//第一个字的x y轴
	private Point point;
	
	private int fontHeight;
	private int fontWidth;
	
	private ConcurrentLinkedQueue<ChatMessages> chatMessageQueue;
	
	public ChatMessageInformation(String message
			, Font font
			, Integer senderID
			, Boolean isSelfSend
			, int currentCompentWidth
			, Graphics g) {
		this.message = message;
		this.font = font;
		this.senderID = senderID;
		this.isSelfSend = isSelfSend;
		this.currentCompentWidth = currentCompentWidth;
		this.g = g;
	}

	
	public Point getMessagePaintleftTop() {
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		fontHeight = fontMetrics.getHeight();
//		fontMetrics.getFontRenderContext().get
		int width = fontMetrics.stringWidth(message);
		int subCompentW = currentCompentWidth - 14;
		int i = width / subCompentW;
		int heiht = fontHeight + 7;
		if (i > 0) {
			fontWidth = subCompentW;
			heiht *= i + 7;
		}else {
			fontWidth = width;
		}
		
		
		
		
		return null;
	}

	public Font getFont() {
		return font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}


	public Integer getSenderID() {
		return senderID;
	}

	public Boolean getIsSelfSend() {
		return isSelfSend;
	}

	public void setIsSelfSend(Boolean isSelfSend) {
		this.isSelfSend = isSelfSend;
	}
	
	//所有字的宽度
	public int getMessagePaintWidth(){
		return 0;
	}

	//所有字的高度
	public int getMessagePaintHeight(){
		return 0;
	}
	
}
