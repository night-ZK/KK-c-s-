package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import frame.Window;

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
