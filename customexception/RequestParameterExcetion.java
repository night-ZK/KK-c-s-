package customexception;

public class RequestParameterExcetion extends FKException{

	
	public RequestParameterExcetion(String errorMessage) {
		// TODO Auto-generated constructor stub
		System.err.println(errorMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
