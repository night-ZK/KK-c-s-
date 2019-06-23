package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import frame.ChatWindow;
import frame.ClientLogin;
import frame.Window;
import message.MessageModel;
import threadmanagement.LockModel;
import threadmanagement.ThreadConsole;
import tools.ObjectTool;
import tools.TransmitTool;
import transmit.MessageManagement;
import transmit.getter.Receive;
import transmit.nio.SocketClientNIO;

public class CloseListener implements MouseListener{

	private Window _win;
	private JButton _jButton;
	private ImageIcon _closeJButton_Ago;
	private ImageIcon _closeJButton_After;
	private JPanel _jPanel;
	
	public CloseListener(Window winSelf) {
		_win = winSelf;
	}
	
	public CloseListener(Window winSelf, JButton jButton, ImageIcon agoIcon, ImageIcon afterIcon) {
		_win = winSelf;
		_jButton = jButton;
		_closeJButton_Ago = agoIcon;
		_closeJButton_After = afterIcon;
	}
	
	public CloseListener(Window winSelf, JPanel jPanel, ImageIcon agoIcon, ImageIcon afterIcon) {
		_win = winSelf;
		_jPanel = jPanel;
		_closeJButton_Ago = agoIcon;
		_closeJButton_After = afterIcon;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		_win.dispose();
		if (_win instanceof ChatWindow) {
			ChatWindow.set$index(ChatWindow.get$index() - 1);
			Number index_Key = ((ChatWindow) _win).get_index();
			((ChatWindow)_win).removeChatFriendsList(index_Key);
		}else if(!(_win instanceof ClientLogin)){
//			try {
//				Receive.getReceive().getSocket().setSoTimeout(5000);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
			try {
				MessageModel closeModel = MessageManagement.getCloseMessageModel();
				SocketClientNIO.createSocketClient().sendReuqest(closeModel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ThreadConsole.useThreadPool().shutdown();
//			ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
//			int activeThreadCount = threadGroup.activeCount();
//			Thread[] activeThreadArrays = new Thread[activeThreadCount];
//			threadGroup.enumerate(activeThreadArrays);
//			for (Thread thread : activeThreadArrays) {
//				if (!ObjectTool.isNull(thread)) {					
//					System.out.println("threadName: " + thread.getName());
//					thread.interrupt();
//				}
//			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (_closeJButton_Ago != null) {
			
			_jButton.setIcon(_closeJButton_After);			
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (_closeJButton_After != null) {
			
			_jButton.setIcon(_closeJButton_Ago);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
