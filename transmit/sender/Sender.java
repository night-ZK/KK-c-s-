package transmit.sender;

import java.io.IOException;

import message.MessageContext;
import message.MessageHead;
import message.MessageModel;
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import transmit.SocketClient;
import transmit.getter.Receive;

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

	public void sendRequest(boolean isJoin) {
		Thread requestThread = new Thread(this);

		requestThread.start();
		
		try {
			
			Runnable round = () ->{
				
				while(true) {
					synchronized(this) {						
						if (this.isWait) {							
							Thread receiveThread = new Thread(new Receive(this.socket));
							receiveThread.start();
							break;
						}
					}
				}
			};
			
			Thread roundThread = new Thread(round);
			
			roundThread.start();
			if (isJoin) {	
				System.out.println("MainThread wait..");
				requestThread.join();
				System.out.println("MainThread wait done..");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		ThreadConsole.useThreadPool().execute(requestThread);
//		return requestThread;
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	protected void sendMessageModel() throws IOException {
		this.sendMessageHeader();
		this.sendMessageContext();

		this.socket.shutdownOutput();
		
//		if(ObjectTool.isNull(this.oos)) this.oos.close();
//		if(ObjectTool.isNull(this.os)) this.os.close();
		
	}
	
	protected abstract void sendMessageHeader() throws IOException;

	protected abstract void sendMessageContext() throws IOException;
	
}
