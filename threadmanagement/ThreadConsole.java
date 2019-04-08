package threadmanagement;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * �ͻ����̹߳�����
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
}