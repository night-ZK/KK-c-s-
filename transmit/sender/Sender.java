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
		Thread requestThread = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("test thread start..");
					Thread.sleep(5000);

					System.out.println("test thread end..");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		try {
			if (isJoin) {
				System.out.println("threadname: " + Thread.currentThread().getName());
				System.out.println("MainThread wait..");
				requestThread.join();
				System.out.println("MainThread wait done..");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ThreadConsole.useThreadPool().execute(requestThread);
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
