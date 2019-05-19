package message;

public class MessageHead implements MessageInterface{

	private static final long serialVersionUID = 1L;
	
	//请求番号
	private Integer requestNO;
	
	//用于标识客户端发送的消息类型
	private Integer type;
	//请求的数据类型
	private Class<?> requestDataType;
	//请求的描述
	private String requestDescribe;
	//发送请求时间戳
	private Long requestTime;
	//是否存在context
	private boolean hasMessageContext;
	
	//回复请求时间戳
	private Long replyTime;
	//回复请求结果
	private boolean replyRequestResult;
	//回复请求描述
	private String replyDescribe;
	//回复的数据类型
	private Class<?> replyDataType;
	
	public Integer getRequestNO() {
		return requestNO;
	}

	public void setRequestNO(Integer requestNO) {
		this.requestNO = requestNO;
	}

	public boolean isHasMessageContext() {
		return hasMessageContext;
	}

	public void setHasMessageContext(boolean hasMessageContext) {
		this.hasMessageContext = hasMessageContext;
	}

	public Class<?> getReplyDataType() {
		return replyDataType;
	}

	public void setReplyDataType(Class<?> replyDataType) {
		this.replyDataType = replyDataType;
	}

	public String getReplyDescribe() {
		return replyDescribe;
	}

	public void setReplyDescribe(String replyDescribe) {
		this.replyDescribe = replyDescribe;
	}

	public boolean getReplyRequestResult() {
		return replyRequestResult;
	}

	public void setReplyRequestResult(boolean replyRequestResult) {
		this.replyRequestResult = replyRequestResult;
	}

	public Long getReplyTime() {
		return replyTime;
	}
	
	public void setReplyTime(Long replyTime) {
		this.replyTime = replyTime;
	}
	
	public Long getRequestTime() {
		return requestTime;
	}
	
	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Class<?> getRequestDataType() {
		return requestDataType;
	}
	
	public void setRequestDataType(Class<?> requestDataType) {
		this.requestDataType = requestDataType;
	}
	
	public String getRequestDescribe() {
		return requestDescribe;
	}
	
	public void setRequestDescribe(String requestDescribe) {
		this.requestDescribe = requestDescribe;
	}

	@Override
	public String toString() {
		return "MessageHead [type=" + type + ", requestDataType=" + requestDataType + ", requestDescribe="
				+ requestDescribe + ", requestTime=" + requestTime + ", hasMessageContext=" + hasMessageContext
				+ ", replyTime=" + replyTime + ", replyRequestResult=" + replyRequestResult + ", replyDescribe="
				+ replyDescribe + ", replyDataType=" + replyDataType + "]";
	}
	
	
}
