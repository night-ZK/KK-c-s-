package customexception;

public class ResponseLineNotAbleExcetion extends FKException{

	
	public ResponseLineNotAbleExcetion(String errorMessage) {
		// TODO Auto-generated constructor stub
		System.err.println(errorMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
