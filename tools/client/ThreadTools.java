package tools.client;

import transmit.sender.Request;

public class ThreadTools {
	
	/**
	 * ���ѵȴ���Դ���߳�
	 * @param requestMapKey
	 */
	public static void notifyRequestThread(String requestMapKey) {
		Runnable requestThread = Request.requestMap.get(requestMapKey);
		System.out.println("responseMapKey: " + requestMapKey);
		if (requestThread != null) {
			
			synchronized (requestThread) {
				requestThread.notify();
			}
		}
	}
}
