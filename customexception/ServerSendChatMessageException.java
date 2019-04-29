package customexception;

public class ServerSendChatMessageException extends FKException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerSendChatMessageException(String errorMessage) {
		// TODO Auto-generated constructor stub
		System.err.println(errorMessage);
	}
}
