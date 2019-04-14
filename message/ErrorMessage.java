package message;

public class ErrorMessage implements MessageInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageID;
	//¥ÌŒÛ√Ë ˆ
	private String messageDescribe;
	private boolean isSuccess;
	public ErrorMessage(boolean isSuccess, String messageCon) {
		this.isSuccess = isSuccess;
		this.messageDescribe = messageCon;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	

	public String getMessageDescribe() {
		return messageDescribe;
	}

	public void setMessageDescribe(String messageDescribe) {
		this.messageDescribe = messageDescribe;
	}

	@Override
	public String toString() {
		return "ErrorMessage [messageID=" + messageID + ", messageDescribe=" + messageDescribe + ", isSuccess="
				+ isSuccess + "]";
	}
	
}
