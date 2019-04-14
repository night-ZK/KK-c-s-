package transmit;

import java.io.IOException;

import message.MessageContext;
import message.MessageHead;
import message.MessageModel;
import tools.TransmitTool;

public class GetRequest extends Request{

	public GetRequest(MessageModel model) {
		super(model);
	}

	@Override
	protected void sendMessageHead() {
		try {
			this.oos.writeObject(messageHead);
			
			this.oos.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void sendMessageContext() {
		
	}

	@Override
	protected void sendMessageModel() throws IOException {
		this.oos.writeObject(messageHead);
		
		this.oos.flush();
	}
	
	@Override
	protected MessageModel getReplyMessageModel() {
		
		String key = TransmitTool.getRequestMapKey(this.messageHead);
		
		return Receive.receiveMap.get(key); 
	}


}
