package transmit.sender;

import java.io.IOException;

import message.MessageContext;
import message.MessageHead;
import message.MessageModel;
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import tools.SenderTools;
import transmit.SocketClient;
import transmit.getter.Receive;

public abstract class Sender extends SocketClient{
	
	protected MessageHead messageHead;
	protected MessageContext messageContext;
	
	protected MessageModel model;
	
	protected SenderTools senderTools;
	public Sender(MessageModel model) {
		try {
			this.model = model;
			
			this.messageHead = model.getMessageHead();
			this.messageContext = model.getMessageContext();
			
			this.senderTools = new SenderTools(os);
			
			if(!ObjectTool.isRequestHeadDoable(messageHead))
				throw new NullPointerException("messageHead is no doable..");

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public Sender sendRequest(boolean isJoin) {
		Thread requestThread = new Thread(this);

		requestThread.start();
		
		try {
			if (isJoin) {	
				requestThread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public abstract void then();
	
	protected void sendMessageModel() throws IOException {
		this.sendMessageHeader();
		this.sendMessageContext();
		
	}
	
	protected abstract void sendMessageHeader() throws IOException;

	protected abstract void sendMessageContext() throws IOException;
	
}
