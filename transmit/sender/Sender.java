package transmit.sender;

import java.io.IOException;

import message.MessageContext;
import message.MessageHead;
import message.MessageModel;
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import transmit.SocketClient;

public abstract class Sender extends SocketClient{
	
	protected MessageHead messageHead;
	protected MessageContext messageContext;
	
	protected MessageModel model;
	
	public Sender(MessageModel model) {
		try {
			this.model = model;
			
			this.messageHead = model.getMessageHead();
			this.messageContext = model.getMessageContext();
			
			if(!ObjectTool.isRequestHeadDoable(messageHead))
				throw new NullPointerException("messageHead is no doable..");

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public Thread sendRequest(boolean isJoin) {
		Thread requestThread = new Thread(this);
		ThreadConsole.useThreadPool().execute(requestThread);
		
		try {
			if (isJoin) requestThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return requestThread;
		
//		MessageModel replyModel = this.getReplyMessageModel();
//		
//		MessageHead replyMessageHead = replyModel.getMessageHead();
//		
//		if (!replyMessageHead.getReplyRequestResult()) {
//			throw new ConnectException("\"request data fail..\"");
//		}
//		
//		MessageContext messageContext = replyModel.getMessageContext();
//		
//		return messageContext.getObject();
//		if (messageContext.getObject() instanceof MessageHead) {
//		}
	}
	
	
	protected void sendMessageModel() throws IOException {
		this.sendMessageHeader();
		this.sendMessageContext();

		this.oos.flush();
		
		if(ObjectTool.isNull(this.oos)) this.oos.close();
		if(ObjectTool.isNull(this.os)) this.os.close();
		
	}
	
	protected abstract void sendMessageHeader() throws IOException;

	protected abstract void sendMessageContext() throws IOException;
	
}
