package message;

public class MessageHead implements MessageInterface{

	private static final long serialVersionUID = 1L;
	
	//���󷬺�
	private Integer requestNO;
	
	//���ڱ�ʶ�ͻ��˷��͵���Ϣ����
	private Integer type;
	//�������������
	private Class<?> requestDataType;
	//���������
	private String requestDescribe;
	//��������ʱ���
	private Long requestTime;
	//�Ƿ����context
	private boolean hasMessageContext;
	
	//�ظ�����ʱ���
	private Long replyTime;
	//�ظ�������
	private boolean replyRequestResult;
	//�ظ���������
	private String replyDescribe;
	//�ظ�����������
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
