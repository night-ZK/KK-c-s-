package message;

public class MessageContext implements MessageInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "MessageContext [object=" + object + "]";
	}
}
