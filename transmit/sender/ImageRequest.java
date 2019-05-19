package transmit.sender;

import java.rmi.ConnectException;

import message.MessageModel;

public class ImageRequest extends GetRequest{

	private String imageRequestMapKey = null;
	
	public ImageRequest(MessageModel model) {
		super(model);
	}

	@Override
	protected String getRequestMapKey() {

		if (imageRequestMapKey != null) {
			return imageRequestMapKey;
		}
		
		return super.getRequestMapKey();
	}

	public String getImageRequestMapKey() {
		return imageRequestMapKey;
	}

	public void setImageRequestMapKey(String imageRequestMapKey) {
		this.imageRequestMapKey = imageRequestMapKey;
	}

	@Override
	protected void setReplyModel() throws ConnectException {
		
	}

	
}
