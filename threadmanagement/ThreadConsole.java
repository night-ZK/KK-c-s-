package threadmanagement;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 客户端线程管理类
 * @author zxk
 *
 */
public class ThreadConsole {
	private static ThreadPoolExecutor threadpool;
	private static int threadCount;
	static {
		//TODO change 20 , for XML
		threadCount = 20;
		threadpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
	}
	private ThreadConsole() {
		
	}
	
	public static ThreadPoolExecutor useThreadPool() {
		return threadpool;
	}

	public static void blockThread(LockModel lockModel, Thread blockThread) {
		try {		
			synchronized(lockModel) {
				lockModel.setLockState(2);
				lockModel.wait();
				lockModel.setLockState(3);
			}
			System.out.println(lockModel.getLockDescribe() + ": wait done..");
//			blockThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
