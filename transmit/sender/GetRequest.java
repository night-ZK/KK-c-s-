package transmit.sender;

import java.io.IOException;
import java.io.ObjectOutputStream;

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
			senderTools.sendMessage(messageHead).sendDone();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void sendMessageContext() {
		
	}

	@Override
	public void then() {
		// TODO Auto-generated method stub
		
	}

}
