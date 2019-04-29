package transmit.sender;

import java.io.IOException;

import message.MessageModel;
import tools.TransmitTool;
import transmit.getter.Receive;

public class GetRequest extends Request{

	public GetRequest(MessageModel model) {
		super(model);
	}

	@Override
	protected void sendMessageHeader() {
		try {
			
			this.oos.writeObject(messageHead);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void sendMessageContext() {
		
	}

	@Override
	protected MessageModel getReplyMessageModel() {
		
		String key = TransmitTool.getRequestMapKey(this.messageHead);
		
		return Receive.receiveMap.get(key); 
	}

}
