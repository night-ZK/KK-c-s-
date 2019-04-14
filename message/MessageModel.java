package message;

public class MessageModel implements MessageInterface{

	private static final long serialVersionUID = 1L;
	
	private MessageHead messageHead;
	private MessageContext messageContext;
	
	public MessageModel(MessageHead messageHead, MessageContext messageContext) {
		this.messageHead = messageHead;
		this.messageContext = messageContext;
	}
	
	public MessageHead getMessageHead() {
		return messageHead;
	}
	public void setMessageHead(MessageHead messageHead) {
		this.messageHead = messageHead;
	}
	public MessageContext getMessageContext() {
		return messageContext;
	}
	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

}
