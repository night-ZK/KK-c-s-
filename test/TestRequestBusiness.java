package test;

import java.awt.Frame;
import java.util.List;

import javax.swing.JTextPane;

import org.junit.Test;

import frame.UpdateInformationWindow;
import frame.customComponent.ChatMessageTextJPanel;
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
	
	@Test
	public void testSplit() {
		String aString = "asdasdas d";
		String[] as = aString.split(" ");
		for (String string : as) {
			
			System.out.println("as: " + string);
		}
		String[] bs = aString.split("G");
		for (String string : bs) {
			
			System.out.println("bs: " + string);
		}
	}
	
	@Test
	public void testgetByte() {
		String aString = "";
		int b = aString.getBytes().length;
		System.out.println("b: " + b);
	}
	
	@Test
	public void testTextJPanel() {
		Frame frame = new Frame();
		frame.setLayout(null);
		frame.setSize(300, 500);
		
		ChatMessageTextJPanel kJTextPane = new ChatMessageTextJPanel();
		kJTextPane.setEditable(true);
		double h = kJTextPane.getPreferredSize().height;
		System.out.println("h1: " + h);
		kJTextPane.setText("asdasddddddddasdas"
				+ "asdasdasddddddddddddddddd"
				+ "asddddd");
		kJTextPane.setBounds(10, 10, 20, (int)h);
		h = kJTextPane.getPreferredSize().height;
		System.out.println("h: " + h);
		frame.add(kJTextPane);
		frame.setVisible(true);
	}

	@Test
	public void testUpdateWinodw() {
		new UpdateInformationWindow();
		long a = System.currentTimeMillis();
		while (System.currentTimeMillis() - a < 10000) {
//			System.out.println(1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
