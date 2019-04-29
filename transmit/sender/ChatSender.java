package transmit.sender;

import java.io.IOException;

import message.MessageModel;

public class ChatSender extends Sender{

	public ChatSender(MessageModel model) {
		super(model);
	}
	
	@Override
	public void run() {
		try {
			
			this.sendMessageModel();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void sendMessageHeader() throws IOException {
		this.oos.writeObject(messageHead);
	}

	@Override
	protected void sendMessageContext() throws IOException {
		this.oos.writeObject(messageContext);
	}

}
