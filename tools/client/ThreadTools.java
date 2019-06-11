package tools.client;

import threadmanagement.LockModel;
import tools.TransmitTool;

public class ThreadTools {
	
	/**
	 * ���ѵȴ���Դ���߳�
	 * @param requestMapKey
	 */
	public static void notifyRequestThread(String requestMapKey) {
//		Runnable requestThread = Request.requestMap.get(requestMapKey);
		LockModel lockModel = TransmitTool.getLockModel().get(requestMapKey);
		System.out.println("responseMapKey: " + requestMapKey);
		if (lockModel != null) {
			
			synchronized (lockModel) {
				lockModel.notify();
			}
		}
	}
}
