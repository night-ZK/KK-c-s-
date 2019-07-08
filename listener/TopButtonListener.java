package listener;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import frame.ChatWindow;
import frame.MainWindow;
import frame.Window;
import message.MessageModel;
import threadmanagement.ThreadConsole;
import transmit.MessageManagement;
import transmit.nio.SocketClientNIO;

public class TopButtonListener implements MouseListener{

	private static SystemTray _tray;
	
	private Window _win;
	private JButton _jButton;
	private ImageIcon _closeJButton_Ago;
	private ImageIcon _closeJButton_After;
	
	static {
		 _tray = SystemTray.getSystemTray();
	}
	
	public TopButtonListener(Window winSelf) {
		_win = winSelf;
	}
	
	public TopButtonListener(Window winSelf, JButton jButton, ImageIcon agoIcon, ImageIcon afterIcon) {
		_win = winSelf;
		_jButton = jButton;
		_closeJButton_Ago = agoIcon;
		_closeJButton_After = afterIcon;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getComponent().getName().equals("close")) {			
			close(this._win);
		}else {
			min(this._win);
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
	

	private void min(Window win) {
		if (win instanceof MainWindow) {
			win.setVisible(false);
			
			PopupMenu popupMenu = new PopupMenu();
			TrayIcon trayIcon = new TrayIcon(win.getIconImage(), "chat..", popupMenu);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						show(win);						
					}
				}
			});
			
			
			MenuItem show_Window = new MenuItem("show");
			MenuItem exit_Window = new MenuItem("exit");
			
			show_Window.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					show(win);
				}
			});
			
			exit_Window.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					_tray.remove(trayIcon);
					System.exit(0);
				}
			});
			
			popupMenu.add(show_Window);
			popupMenu.add(exit_Window);
			
			try {
				if (_tray.getTrayIcons().length == 0) {					
					_tray.add(trayIcon);
				}
			} catch (AWTException e) {
				e.printStackTrace();
			}
			
			
		}else {			
			win.setExtendedState(JFrame.ICONIFIED);
		}
	}
	
	public void close(Window win) {
		win.dispose();
		SocketClientNIO socketClientNIO = SocketClientNIO.createSocketClient();
		if (win instanceof ChatWindow) {
			ChatWindow.set$index(ChatWindow.get$index() - 1);
			Number index_Key = ((ChatWindow) win).get_index();
			((ChatWindow)win).removeChatFriendsList(index_Key);
		}else {
			 if(socketClientNIO.getSocketChannel() != null && socketClientNIO.getSocketChannel().isOpen()) {
				 try {
					 MessageModel closeModel = MessageManagement.getCloseMessageModel(Window.getSaveUserID().intValue());
					 socketClientNIO.sendReuqest(closeModel);
				 } catch (IOException e) {
					 e.printStackTrace();
				 }				 
			 }
			 ThreadConsole.useThreadPool().shutdown();
			 System.exit(0);
		}
	}
	
	private void show(Window win) {
		win.setVisible(true);
		win.setExtendedState(JFrame.NORMAL);
		win.toFront();
	}
	
}
