package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;


public class LoginView extends Window implements Runnable{

	private static final long serialVersionUID = 11L;
	
	static int index;
	static List<Point> pointList;
	static Integer openFlag;
	
	private static LoginView _loginView; 
	
	static {
		index = 0;
		pointList = new ArrayList<Point>();
	}
	
	public static synchronized LoginView createLoginView(){
		if (_loginView == null) {
			_loginView = new LoginView();
		}
		return _loginView;
	}
	
	public static void setOpenFlag(Integer openFlag) {
		synchronized (LoginView.openFlag) {			
			LoginView.openFlag = openFlag;
		}
	}
	
	public LoginView() {
		openFlag = 1;
		this._width(150);
		this._height(10);
		this.setSize(_width, 10);
		
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 215, 0));
		Point point = new Point(index++ * 15, 0);
		
		if(_width - point.x < 15) {
			index = 0;
			pointList.clear();
			point = new Point(index++ * 15, 0);
		}
		
		pointList.add(point);
		
		for(Point p: pointList) {			
			g2d.fillOval(p.x, p.y, 10, 10);
		}
	}
	
	@Override
	public void run() {
		this.requestFocus();
		try {
			boolean flag = true;
			while (flag) {			
				repaint();
				Thread.sleep(1000);
				synchronized(openFlag) {
					flag = openFlag == 1;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean die() {
		if (_loginView == null) {
			return false;
		}
		synchronized (openFlag) {			
			openFlag = 0;
		}
		_loginView.dispose();
		_loginView = null;
		return true;
	}
	
//	public static void main(String[] args) {
//		LoginView loginView = new LoginView();
//		ThreadConsole.useThreadPool().execute(loginView);
//	}

}
