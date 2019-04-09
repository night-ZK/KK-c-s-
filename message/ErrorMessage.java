package message;

public class ErrorMessage implements MessageInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageID;
	private String messageCon;
	private boolean isSuccess;
	public ErrorMessage(boolean isSuccess, String messageCon) {
		this.isSuccess = isSuccess;
		this.messageCon = messageCon;
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
	public String getMessageCon() {
		return messageCon;
	}
	public void setMessageCon(String messageCon) {
		this.messageCon = messageCon;
	}

	@Override
	public String toString() {
		return "ErrerMessage [messageID=" + messageID + ", messageCon=" + messageCon + ", isSuccess=" + isSuccess + "]";
	}
	
}
