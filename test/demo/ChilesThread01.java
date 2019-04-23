package test.demo;

import threadmanagement.ThreadConsole;

public class ChilesThread01 extends TreadDemo01{
	public ChilesThread01(Object o) {
		super(o);
	}

	public TreadDemo01 login() {
//		Thread thread = new Thread(this);
		ThreadConsole.useThreadPool().execute(this);
		
		System.out.println("login..");
		return this;
	}
}
