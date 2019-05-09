package test;

import java.util.List;

import org.junit.Test;

import message.MessageHead;
import message.MessageInterface;
import message.MessageModel;
import test.demo.ChilesThread01;
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import transmit.MessageManagement;
import transmit.RequestBusiness;

public class TestRequestBusiness {
	@Test
	public void testLogin() {
//		MessageModel messageModel = MessageManagement.loginMessageModel("zxk", "zk001");
//		
//		RequestBusiness requestBusiness = new RequestBusiness(messageModel);
//		List<MessageInterface> loginResult = null;
//		loginResult = requestBusiness.login();
//		ThreadConsole.useThreadPool().execute(requestBusiness);
//		
//		while (loginResult == null) {
//			
//			//TODO wait frame
//			System.out.println("login wait..");
//		}
		Object a = "1";
		ChilesThread01 ct = new ChilesThread01(a);
		ct.login().test();
		synchronized (a) {
			System.out.println("i get " + a);
			a.notify();
			System.out.println("i lose " + a);
		}
		a = "2";
		ChilesThread01 ct1 = new ChilesThread01(a);
		ct1.login().test();
		synchronized (a) {
			System.out.println("i get a..");
			a.notify();
			System.out.println("i lose a..");
		}
//		System.out.println(Thread.interrupted());
		
	}
	
	@Test
	public void testTools() {
//		MessageHead messageHead = new MessageHead();
//		System.out.println(ObjectTool.isEmpty(messageHead));
		
//		String aString = "a b c";
//		String[] aStrings = aString.split(" ");
//		System.out.println(aStrings[1]);
		
		System.out.println(ObjectTool.isInteger("123"));
	}
	
	@Test
	public void testThreadJoin() {
		Thread aThread = new Thread() {
			@Override
			public void run() {
				Thread.currentThread().setName("Thread-a");
				for(int i = 0; i < 10; i++) {					
					System.out.println(Thread.currentThread().getName() + " : " + i);
				}
			}
		};
		
		Thread bThread = new Thread() {
			@Override
			public void run() {
				Thread.currentThread().setName("Thread-b");
				for(int i = 0; i < 10; i++) {					
					System.out.println(Thread.currentThread().getName() + " : " + i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		ThreadConsole.useThreadPool().execute(aThread);
//		aThread.start();
		ThreadConsole.useThreadPool().execute(bThread);
		try {
			bThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end..");
	}
	
}
