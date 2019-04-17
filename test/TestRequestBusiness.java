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
		
		ChilesThread01 ct = new ChilesThread01();
		ct.login();
		ThreadConsole.useThreadPool().execute(ct);
//		System.out.println(Thread.interrupted());
		
	}
	
	@Test
	public void testTools() {
		MessageHead messageHead = new MessageHead();
		System.out.println(ObjectTool.isEmpty(messageHead));
	}
	
}
