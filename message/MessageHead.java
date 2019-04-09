package message;

public class MessageHead implements MessageInterface{

	private static final long serialVersionUID = 1L;
	
	//���ڱ�ʶ�ͻ��˷��͵���Ϣ����
	private Integer type;
	//�������������
	private Class<?> requestDataType;
	//���������
	private String requestDescribe;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "MessageHead [type=" + type + ", requestDataType=" + requestDataType + ", requestDescribe="
				+ requestDescribe + "]";
	}

}
