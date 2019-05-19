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
		senderTools.sendMessage(messageHead).sendDone();
	}

	@Override
	protected void sendMessageContext() throws IOException {
		senderTools.sendMessage(messageContext);
	}

}
