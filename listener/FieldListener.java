package listener;


import java.awt.Component;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.List;

import frame.ClientLogin;
import frame.MainWindow;
import frame.MessageWindow;
import frame.Window;
import message.ErrorMessage;
import message.MessageContext;
import message.MessageInterface;
import message.MessageModel;
import tablebeans.User;
import threadmanagement.LockModel;
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import tools.TransmitTool;
import transmit.MessageManagement;
import transmit.RequestBusiness;
import transmit.getter.Receive;
import transmit.nio.SocketClientNIO;
import transmit.sender.GetRequest;

public class FieldListener implements MouseListener, FocusListener, KeyListener {

	private String _textLis = "";
	private TextField _evenLis = null;
	private String _componentName = "";
	private Window _win;
	
	private Point _move_Ago;
	private Point _move_After;

	private static String _pas = new String();
	private static String _user = new String();
	
	public FieldListener(String text, Component comp, String textField_name) {
		_textLis = text;
		_evenLis = (TextField)comp;
		_componentName = textField_name;
	}
	 public FieldListener() {
		
	}
	 public FieldListener(Window window, String componentName){
		 _win = window;
		 _componentName = componentName;
	 }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		arg0.getComponent().requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
//		if (arg0.getX() >= 30 && arg0.getX() <= 270) {
//		}
		//通过_componentName变量来判断绑定事件源是否属于需要监听的移动组件
		if (_componentName.equals("move")) {
			
			_move_Ago = arg0.getLocationOnScreen();	
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (_move_Ago != null) {
			
			_move_After = arg0.getLocationOnScreen();
			
			if (_win != null) {
				
				int mainWindowX_after = (int)_move_After.getX() - (int)_move_Ago.getX() + _win.getX();
				int mainWindowY_after = (int)_move_After.getY() - (int)_move_Ago.getY() + _win.getY(); 				
				
				_win.setBounds(mainWindowX_after, mainWindowY_after, _win.get_width(), _win.get_height());
				_move_Ago = null;
				_move_After = null;
			}
			
		}
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		if(arg0.getComponent() instanceof TextField) {
			TextField textField = (TextField)arg0.getComponent();
			if(textField.getText().equals(_textLis)) {
				textField.setText("");
			}
			
		}
//		if (_evenLis.getText().equals(_textLis)) {
//			//获得焦点时, 文本框内存在默认字符, 则清除
//			_evenLis.setText("");					
//		}
	}
	
	@Override
	public void focusLost(FocusEvent arg0) {

		if(arg0.getComponent() instanceof TextField) {
			TextField textField = (TextField)arg0.getComponent();
			if(textField.getText().equals("")) {
//				System.out.println("_textLis:" + _textLis);
				textField.setText(_textLis);
			}else if (_componentName.equals("textfield0")) {
				_user = _evenLis.getText();
				System.out.println("_user: " + _user);
			}
			
		}
//		if (_evenLis.getText().equals("")) {
//			_evenLis.setText(_textLis);					
//		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		
		//
		if (_componentName.equals("textfield1")) {
			
			//获得密码框内的字符串, 转换为字符数组
			char[] pasChar = _evenLis.getText().trim().toCharArray();
			
			//松开的是删除键
			if (arg0.getKeyCode() == 8) {
				
				_pas = _pas.substring(0, pasChar.length);			
			}

			System.out.println("_pas:"+_pas);
			
			StringBuffer pastxtBuffer  = new StringBuffer();
			for (int i = 0; i < pasChar.length; i++) {
				pastxtBuffer.append("*");
			}
			_evenLis.setText(pastxtBuffer.toString());
			_evenLis.setCaretPosition(pasChar.length);
			
		}
		if (arg0.getKeyChar() == '\n') {
			if (_componentName.equals("textfield0")) {
				_user = _evenLis.getText();
			}
			if (_user.equals("") || _pas.equals("")) {
				//TODO
				
				return;
			}
			boolean loginSucceed = loginMainWindow();
			ClientLogin clientLogin = ClientLogin.createClientLogin();
			if (loginSucceed) {
				//登录成功, 销毁登录窗口
				clientLogin.dispose();
			} else{
				_user = "";
				_pas = "";
				
				clientLogin.getUserText().setText("");
				clientLogin.getPasText().setText("");
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		char keyChar = arg0.getKeyChar();
		if (_componentName.equals("textfield1")) {
			//密码只允许字母和数字
			if (String.valueOf(keyChar).matches("[a-zA-Z0-9+]")) {
				_pas = _pas.concat(String.valueOf(keyChar));
			}else {
				//TODO 密码只允许字母和数字提示框
			}
			
		}
	}
	
	/**
	 * 登陆到MainWindow窗口
	 * 
	 */
	public static boolean loginMainWindow() {
		
		if (ObjectTool.isNull(_user) || ObjectTool.isNull(_pas)) {
			new MessageWindow();
			return false;
		}
		
		MessageModel requestMessageModel = MessageManagement.loginMessageModel(_user, _pas);
		LockModel lockModel = new LockModel(0, "login request");
		
//		SocketClientNIO socketClientNIO = SocketClientNIO.createSocketClient();
//		socketClientNIO.start();
//		
//		String lockKey = TransmitTool.getRequestMapKey(requestMessageModel);
//		LockModel lockModel = new LockModel(0, "login request");
//		TransmitTool.getLockModel().put(lockKey, lockModel);
//		
//		ThreadConsole.blockThread(lockModel, socketClientNIO);
		
//		socketClientNIO.sendReuqest(messageModel);
//		GetRequest getRequest = new GetRequest(messageModel);
		
//		Runnable round = () ->{
//			
//			while(true) {
//				synchronized(getRequest) {						
//					if (getRequest.isWait) {
//						Receive.startReceiveThread();
//						break;
//					}
//				}
//			}
//		};
		
//		ThreadConsole.useThreadPool().execute(round);
		
		System.out.println("login..");
//		getRequest.sendRequest(true);
		
		MessageContext messageContext = null;
		try {
			messageContext = TransmitTool.sendRequestMessageForNIOByBlock(requestMessageModel, lockModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (messageContext == null) {
			System.out.println("login fail..");
			//TODO
			return false;
		}
		
		if (messageContext.getObject() instanceof ArrayList<?>) {
			ArrayList<?> loginContext = (ArrayList<?>)messageContext.getObject();

			if (loginContext.get(0) instanceof User) {
				
				User loginUser = (User) loginContext.get(0);
				if (ObjectTool.isNull(loginUser.getId())) {
					System.out.println("user or password error..");
					//TODO
					
					return false;
				}
				
				if (loginContext.get(1) instanceof byte[]) {
					byte[] iconBytes = (byte[]) loginContext.get(1);
					if (ObjectTool.isNull(iconBytes)) {
						System.err.println("server no find icon error..");
						//TODO 加载默认icon
						
					}
					
					MainWindow.createMainWindow(loginUser, iconBytes);
					System.out.println("login sucess..");
				}else {
					System.err.println("login icon data type error..");
					return false;
				}
				
			}else {
				System.err.println("login user data type error..");
				new MessageWindow();
				return false;
			}
						
		}else {
			//TODO
			System.err.println("data type error..");
			
			return false;
		}

		return true;
		
//		RequestBusiness requestBusiness = new RequestBusiness(messageModel);
//		Thread loginThread = new Thread(requestBusiness);
//		ThreadConsole.useThreadPool().execute(loginThread);
//		
//		Thread loginwaitThread = new Thread() {
//			@Override
//			public void run() {
//				//TODO wait frame
//				while(true) {					
//					System.out.println("login...");
//				}
//			}
//		};
//		
//		ThreadConsole.useThreadPool().execute(loginwaitThread);
//		
//		try {
//			loginThread.join();
//			loginwaitThread.interrupt();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		List<MessageInterface> loginResult = requestBusiness.login();
//		
//		ErrorMessage errorMessage = (ErrorMessage) loginResult.get(0);
//		
//		System.out.println("isLog:"+errorMessage.isSuccess());
//		System.out.println("user:"+_user+",pas:"+_pas);
//		if (errorMessage.isSuccess()) {
//			System.out.println("login sucess..");
//			//登录成功, 销毁登录窗口
//			ClientLogin.createClientLogin().dispose();
//			MessageContext messageContext = (MessageContext) loginResult.get(1);
//			if (messageContext.getObject() instanceof User) {
//				User loginUser = (User) new MessageContext().getObject();
//				MainWindow.createMainWindow(loginUser);
//			}
//			
//		}else {
//			//TODO login fail.. 
//			System.out.println("login fail..");
//		}
		
	}
	
}
