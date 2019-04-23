package test.demo;

public class TreadDemo01 implements Runnable{
	Object object = null;
	public TreadDemo01(Object o) {
		this.object = o;
	}
	@Override
	public synchronized void run() {
		try {
			synchronized(object) {				
				System.out.println("i do ..");
				object.wait();
				System.out.println("i done ..");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void test() {
		System.out.println("test..");
	}
}
