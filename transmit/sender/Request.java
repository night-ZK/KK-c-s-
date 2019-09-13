package transmit.sender;

import java.io.IOException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import message.ErrorMessage;
import message.MessageContext;
import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;
import tools.ObjectTool;
import tools.TransmitTool;
import transmit.getter.Receive;

/**
 * 用于发送请求
 * @author zxk
 *
 */
public abstract class Request extends Sender{
	
	//保存请求的详细信息, 用于标识
	public static Map<String, Runnable> requestMap = 
			new HashMap<String, Runnable>(); 
	//请求采番号
	protected static Integer requestNo = 0;
	
	AtomicInteger requestNO_Atomic = new AtomicInteger(0);
	
	protected MessageContext replyMessageContext = null;
	
	
	protected Request(MessageModel model) {
		super(model);
	}
	
	@Override
	public void run() {
		
		try {
			
			String requestMapKey = this.getRequestMapKey();
			
			synchronized (requestMap) {				
				requestMap.put(requestMapKey, this);
			}
			
			System.out.println("requestMapKey:" + requestMapKey);
			
			this.sendMessageModel();
			
			System.out.println("synchronized..");
			synchronized(this) {					
				System.out.println("requestThread wait..");
				this.isWait = true;
				this.wait();
			}
			
			this.isWait = false;
			this.hasRepley = true;
			
			this.setReplyModel();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			System.out.println("notify done...requestNo: " + requestNo);
		}
		
	}
	
	/**
	 * TODO
	 * @throws ConnectException
	 */
	protected void setReplyModel() throws ConnectException {
		
		MessageModel replyModel = this.getReplyMessageModel();
		if(ObjectTool.isEmpty(replyModel))
			//TODO
//			throw new
			System.err.println("replyModel is null..");
		
		MessageHead replyMessageHead = replyModel.getMessageHead();
		
		if (!replyMessageHead.getReplyRequestResult()) {
			throw new ConnectException("\"request data fail..\"");
		}
		
		this.replyMessageContext = replyModel.getMessageContext();
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
	

	/**
	 * 
	 * @return
	 */
	protected String getRequestMapKey() {
		return TransmitTool.getRequestMapKey(this.model);
	}
	
	
	
	public synchronized static Integer getRequestNo() {
		return requestNo++;
	}

	protected MessageModel getReplyMessageModel() {
		
		String key = TransmitTool.getRequestMapKey(this.model);
		
		return Receive.receiveMap.get(key); 
	}
}
