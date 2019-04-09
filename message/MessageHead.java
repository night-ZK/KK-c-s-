package message;

public class MessageHead implements MessageInterface{

	private static final long serialVersionUID = 1L;
	
	//用于标识客户端发送的消息类型
	private Integer type;
	//请求的数据类型
	private Class<?> requestDataType;
	//请求的描述
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
