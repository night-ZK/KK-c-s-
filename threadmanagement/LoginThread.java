package threadmanagement;

import java.io.IOException;

import message.MessageModel;
import tools.TransmitTool;

public class LoginThread extends Thread{
	private MessageModel messageModel;
	private MessageModel requestMessageModel;
	private LockModel lockModel;

	
	public LoginThread(MessageModel messageModel, MessageModel requestMessageModel, LockModel lockModel) {
		super();
		this.messageModel = messageModel;
		this.requestMessageModel = requestMessageModel;
		this.lockModel = lockModel;
	}

	public MessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}

	public MessageModel getRequestMessageModel() {
		return requestMessageModel;
	}

	public void setRequestMessageModel(MessageModel requestMessageModel) {
		this.requestMessageModel = requestMessageModel;
	}

	public LockModel getLockModel() {
		return lockModel;
	}

	public void setLockModel(LockModel lockModel) {
		this.lockModel = lockModel;
	}

	@Override
	public void run() {
		try {
//			Thread.sleep(1000);
			messageModel = TransmitTool.sendRequestMessageForNIOByBlock(requestMessageModel, lockModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
