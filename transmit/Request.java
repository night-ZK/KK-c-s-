package transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
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
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import tools.TransmitTool;

/**
 * 用于发送请求
 * @author zxk
 *
 */
public abstract class Request implements Runnable{
	
	//保存请求的详细信息, 用于标识
	public static Map<String, Runnable> requestMap = 
			new HashMap<String, Runnable>(); 
	
	//请求结果
	List<MessageInterface> result = null;
	
	private Socket socket;
	protected MessageHead messageHead;
	protected MessageContext messageContext;
	
	protected MessageModel model;
	
	protected OutputStream os;
	protected InputStream is;
	
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	
	protected MessageContext replyMessageContext;
	
	protected Request(MessageModel model) {
		try {
			this.socket = SocketClient.getSocket();
			this.model = model;
			
			this.messageHead = model.getMessageHead();
			this.messageContext = model.getMessageContext();
			
			if(ObjectTool.isRequestHeadDoable(messageHead))
				throw new NullPointerException("messageHead is no doable..");

			
			this.os = socket.getOutputStream();
			this.is = socket.getInputStream();
			
			this.oos = new ObjectOutputStream(os);
			this.ois = new ObjectInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void run() {
		
		try {
			
			this.sendMessageModel();
			
			String requestMapKey = TransmitTool.getRequestMapKey(this.messageHead);
			
			System.out.println("requestMapKey:" + requestMapKey);
			
			requestMap.put(requestMapKey, this);
			
			MessageModel replyModel = null;
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
	
	public Thread sendRequest(boolean isJoin) {
		Thread requestThread = new Thread(this);
		ThreadConsole.useThreadPool().execute(requestThread);
		
		try {
			if (isJoin) requestThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return requestThread;
		
//		MessageModel replyModel = this.getReplyMessageModel();
//		
//		MessageHead replyMessageHead = replyModel.getMessageHead();
//		
//		if (!replyMessageHead.getReplyRequestResult()) {
//			throw new ConnectException("\"request data fail..\"");
//		}
//		
//		MessageContext messageContext = replyModel.getMessageContext();
//		
//		return messageContext.getObject();
//		if (messageContext.getObject() instanceof MessageHead) {
//		}
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
	
	protected abstract void sendMessageModel() throws IOException; 
	
	protected abstract MessageModel getReplyMessageModel();
	
	protected abstract void sendMessageHead();

	protected abstract void sendMessageContext();
//	
//	
//	
//	/**
//	 * 发送消息头
//	 */
//	public abstract ErrorMessage sendMessageHead();
//	
//	/**
//	 * 发送消息正文
//	 */
//	public abstract ErrorMessage sendMessageContext();
}
