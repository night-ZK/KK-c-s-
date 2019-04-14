package tools;

import message.MessageHead;

public class TransmitTool {
	/**
	 * 获取requestMap的key值
	 * @param messageHead
	 * @return
	 */
	public static String getRequestMapKey(MessageHead messageHead) {
		
		Integer headType = messageHead.getType();
		Long sendTime = messageHead.getRequestTime();

		return "Type: " + headType + ", sendTime: " + sendTime; 
		
	}
}
