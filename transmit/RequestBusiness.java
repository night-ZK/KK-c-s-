package transmit;

import java.util.ArrayList;
import java.util.List;

import message.ErrorMessage;
import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;

public class RequestBusiness extends GetRequest{

	public RequestBusiness(MessageModel model) {
		super(model);
	}

	public List<MessageInterface> login() {
		List<MessageInterface> resultList = new ArrayList<>();
		ErrorMessage errorMessage = null;
		
		MessageModel replyModel = this.getReplyMessageModel();
		
		MessageHead replyMessageHead = replyModel.getMessageHead();
		
		if (!replyMessageHead.getReplyRequestResult()) {
			errorMessage = new ErrorMessage(false, replyMessageHead.getReplyDescribe());
			resultList.add(errorMessage);
			return resultList;
		}
		
		errorMessage = new ErrorMessage(true, replyMessageHead.getReplyDescribe());
		resultList.add(errorMessage);
		resultList.add(replyModel.getMessageContext());
		
		return resultList;
	}
	
	public List<MessageInterface> getFrindID() {
		List<MessageInterface> resultList = new ArrayList<>();
		ErrorMessage errorMessage = null;
		
		MessageModel replyModel = this.getReplyMessageModel();
		
		MessageHead replyMessageHead = replyModel.getMessageHead();
		
		if (!replyMessageHead.getReplyRequestResult()) {
			errorMessage = new ErrorMessage(false, replyMessageHead.getReplyDescribe());
			resultList.add(errorMessage);
			return resultList;
		}
		
		errorMessage = new ErrorMessage(true, replyMessageHead.getReplyDescribe());
		resultList.add(errorMessage);
		resultList.add(replyModel.getMessageContext());
		
		return resultList;
	}
}



