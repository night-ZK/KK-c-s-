package message;

public class ChatMessages extends MessageContext{

	private static final long serialVersionUID = 1L;
	
	private Integer senderID;
	private Integer getterID;
	private String Message;
	public Integer getSenderID() {
		return senderID;
	}
	public void setSenderID(Integer senderID) {
		this.senderID = senderID;
	}
	public Integer getGetterID() {
		return getterID;
	}
	public void setGetterID(Integer getterID) {
		this.getterID = getterID;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	@Override
	public String toString() {
		return "ChatMessages [senderID=" + senderID + ", getterID=" + getterID + ", Message=" + Message + "]";
	}
}
