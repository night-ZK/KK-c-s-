package frame.customComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import model.DrawStyleModel;

public class ChatMessageTextJPanel extends JTextPane{

	private static final long serialVersionUID = 1L;
	private String date;
	private String sender;
	private int senderType;//0:self 1:friend
	private JLabel timeLabel;
	
	private Color otherMessageColor;
	private Color otherMessageBorderColor; 
	private Color selfMessageColor;
	private Color selfMessageBorderColor;
	
//	private Graphics2D g2D;
	private DrawStyleModel drawStyleModel;
//	private HTMLDocument htmlDocument;
//	private int tempNum; 
	public ChatMessageTextJPanel(String date, String sender, int senderType) {
		super();
		this.setEditorKit(new WarpEditorKit());
//		htmlDocument = new HTMLDocument();
//		htmlDocument = (HTMLDocument)super.getDocument();
//		tempNum = 0;
		this.date = date;
		this.sender = sender;
		this.senderType = senderType;
		
		timeLabel = new JLabel(date + " for " + sender);
		
		otherMessageColor = new Color(188, 237, 245);
		otherMessageBorderColor = new Color(156, 205, 213); 
		selfMessageColor = new Color(230, 230, 230);
		selfMessageBorderColor = new Color(198, 198, 198);
		
		setEditable(false);
		setOpaque(false);
		
	}

	public ChatMessageTextJPanel() {
		super();
		this.setEditorKit(new WarpEditorKit());
//		htmlDocument = new HTMLDocument();
//		htmlDocument = (HTMLDocument)super.getDocument();
//		tempNum = 0;
	}
	
	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DrawStyleModel getDrawStyleModel() {
		return drawStyleModel;
	}

	public void setDrawStyleModel(DrawStyleModel drawStyleModel) {
		this.drawStyleModel = drawStyleModel;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (drawStyleModel != null) {			
			Graphics2D g2D = (Graphics2D)g;
			drawStyle(drawStyleModel, g2D);
//			Font tepmF = new Font("华文楷体", Font.BOLD, 18);
//			g.setFont(tepmF);
//			g.drawString("test", drawStyleModel.getX(), drawStyleModel.getY());
//			Font tepmF = new Font("华文楷体", Font.BOLD, 18);
//			g2D.setFont(tepmF);
//			g2D.drawString("华文楷体", 0, 0);
		}
		// 
		super.paintComponent(g);
	}
	
	private void drawStyle(DrawStyleModel drawStyleModel, Graphics2D g2D) {
		
		drawStyle(drawStyleModel.getX()
				, drawStyleModel.getY()
				, drawStyleModel.getWidth()
				, drawStyleModel.getHeight()
				, drawStyleModel.getArcWidth()
				, drawStyleModel.getArcHeight()
				, g2D);
	}
	
	public void drawStyle(int x, int y, int width, int height, int arcWidth, int arcHeight, Graphics2D g2D){
		
		if (g2D == null) {			
			System.err.println("g2D is null..");
			return;
		}
		
		boolean isSelf = senderType == 0;
		//反锯齿效果
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		String[] fontName = 
//				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		g2D.setColor(isSelf ? selfMessageColor : otherMessageColor);
//		g2D.fillRoundRect(point.x - 7, point.y - 7
//				, chatMessageInformation.getMessagePaintWidth() + 14
//				, chatMessageInformation.getMessagePaintHeight() + 14
//				, 10, 10);
		g2D.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);
		
		g2D.setColor(isSelf ? selfMessageBorderColor : otherMessageBorderColor);
		//边框
//		g2D.drawRoundRect(point.x - 7, point.y - 7
//				, chatMessageInformation.getMessagePaintWidth() + 14
//				, chatMessageInformation.getMessagePaintHeight() + 14
//				, 10, 10);
		g2D.drawRoundRect(0, 0, width - 1, height - 1, arcWidth, arcHeight);
		
	}


	private class WarpEditorKit extends StyledEditorKit{
		private static final long serialVersionUID = 1L;
		private ViewFactory defaultFactory = new WarpColumnFactory();
		@Override
		public ViewFactory getViewFactory() {
			return defaultFactory;
		}
		
	}
	
	private class WarpColumnFactory implements ViewFactory{

		@Override
		public View create(Element elem) {
			
			switch (elem.getName()) {
			
			case AbstractDocument.ContentElementName:
				return new WarpLabelView(elem);
			
			case AbstractDocument.ParagraphElementName:
				return new ParagraphView(elem);
			
			case AbstractDocument.SectionElementName:
				return new BoxView(elem, View.Y_AXIS);
				
			case StyleConstants.ComponentElementName:
				return new ComponentView(elem);
				
			case StyleConstants.IconElementName:
				return new IconView(elem);
			}
			
			return new LabelView(elem);
		}
		
	}
	
	private class WarpLabelView extends LabelView{

		public WarpLabelView(Element elem) {
			super(elem);
		}
		
		@Override
		public float getMinimumSpan(int axis) {
			switch (axis) {
			case View.X_AXIS:
				return 0;
			case View.Y_AXIS:
				return super.getMinimumSpan(axis);
			default:
				throw new IllegalArgumentException("Illagal Argument: " + axis);
			}
		}
		
	}
	
//	public void addTextForHTML(String html) {
//		try {
//			if (tempNum == 0) {
//				htmlDocument.setInnerHTML(htmlDocument.getDefaultRootElement(), html);
//				tempNum ++;
//			}else {
//				htmlDocument.insertBeforeEnd(htmlDocument.getDefaultRootElement(), html);
//				super.setCaretPosition(super.getDocument().getLength());
//			}
//		} catch (BadLocationException | IOException e) {
//			e.printStackTrace();
//		}
//	}
}
