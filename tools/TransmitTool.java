package tools;

import message.MessageHead;

public class TransmitTool {
	/**
	 * ��ȡrequestMap��keyֵ
	 * @param messageHead
	 * @return
	 */
	public static String getRequestMapKey(MessageHead messageHead) {
		
		Integer headType = messageHead.getType();
		Long sendTime = messageHead.getRequestTime();

		return "Type: " + headType + ", sendTime: " + sendTime; 
		
	}
}
