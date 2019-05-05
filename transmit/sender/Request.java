package transmit.sender;

import java.io.IOException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import message.ErrorMessage;
import message.MessageContext;
import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;
import tools.ObjectTool;
import tools.TransmitTool;

/**
 * ���ڷ�������
 * @author zxk
 *
 */
public abstract class Request extends Sender{
	
	//�����������ϸ��Ϣ, ���ڱ�ʶ
	public static Map<String, Runnable> requestMap = 
			new HashMap<String, Runnable>(); 
	
	protected MessageContext replyMessageContext;
	
	protected Request(MessageModel model) {
		super(model);
	}
	
	@Override
	public void run() {
		
		try {
			
			this.sendMessageModel();
			
			String requestMapKey = TransmitTool.getRequestMapKey(this.messageHead);
			
			System.out.println("requestMapKey:" + requestMapKey);
			
			requestMap.put(requestMapKey, this);
			
			MessageModel replyModel = null;
			System.out.println("synchronized..");
			synchronized(this) {					
				this.wait();
				replyModel = this.getReplyMessageModel();
			}
//			while(ObjectTool.isEmpty(replyModel)) {
//			}
			
			if(ObjectTool.isEmpty(replyModel))
				//TODO
//				throw new
				System.err.println("replyModel is null..");
			
			MessageHead replyMessageHead = replyModel.getMessageHead();
			
			if (!replyMessageHead.getReplyRequestResult()) {
				throw new ConnectException("\"request data fail..\"");
			}
			
			this.replyMessageContext = replyModel.getMessageContext();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<MessageInterface> resolutionResultModel() {
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
	
	public MessageContext getReplyMessageContext() {
		return replyMessageContext;
	}
	
	protected abstract MessageModel getReplyMessageModel();

}
