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
 * 用于发送请求
 * @author zxk
 *
 */
public abstract class Request extends Sender{
	
	//保存请求的详细信息, 用于标识
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
				System.out.println("requestThread wait..");
				this.isWait = true;
				this.wait();
				replyModel = this.getReplyMessageModel();
				System.out.println("replyModel: " + replyModel);
			}
			
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
