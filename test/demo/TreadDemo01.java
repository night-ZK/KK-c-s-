package test.demo;

public class TreadDemo01 implements Runnable{
	@Override
	public synchronized void run() {
		try {
			System.out.println("i do ..");
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
