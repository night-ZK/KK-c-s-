package threadmangagement;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadConsole {
	private static ThreadPoolExecutor threadpool;
	private static int threadCount;
	static {
		threadCount = 20;
		threadpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
	}
	private ThreadConsole() {
		
	}
	
	public static ThreadPoolExecutor useThreadPool() {
		return threadpool;
	}
}
