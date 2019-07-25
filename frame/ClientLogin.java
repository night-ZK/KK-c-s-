package frame;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.TopButtonListener;
import model.PointModel;
import listener.FieldListener;

public class ClientLogin extends Window{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ClientLogin _clientLogin = null;
	
	private static String _userL;
	private static String _pasL;
	private static String _butLabel_Log;
	private static String _butLabel_Close;
	
//	private static String _fontRomen = "Times New Roman Italic";
	
	private static String _tipText_User;
	private static String _tipText_Pas;
	static boolean open;
	private static Object _objectLock;
	
	private TextField userText;
	private TextField pasText;
	
	private Button loginButton;
	private Button closeButton;
	
//	private TriangleModel triangleModel;
//	private List<Point> pointList;
	private List<PointModel> pointModelList;


	private Color backColor;
	
	static{
		 _userL = "username:";
		 _pasL = "password:";
		 _butLabel_Log = "login";
		 _butLabel_Close = "close";
		 
		 _tipText_User = "please enter username..";
		 _tipText_Pas = "please enter password..";
		 
		 _objectLock = "login";
	}
	
	
	public TextField getUserText() {
		return userText;
	}

	public void setUserText(TextField userText) {
		this.userText = userText;
	}

	public TextField getPasText() {
		return pasText;
	}

	public void setPasText(TextField pasText) {
		this.pasText = pasText;
	}

	public static String get_tipText_User() {
		return _tipText_User;
	}

	public static void set_tipText_User(String _tipText_User) {
		ClientLogin._tipText_User = _tipText_User;
	}

	public static String get_tipText_Pas() {
		return _tipText_Pas;
	}

	public static void set_tipText_Pas(String _tipText_Pas) {
		ClientLogin._tipText_Pas = _tipText_Pas;
	}

	private ClientLogin(){
		
		clientLoginInFo();

//		triangleModel = new TriangleModel(_width, _height);
//		pointList = new ArrayList<>();
		pointModelList = new ArrayList<>();
//		backColor = new Color(25, 25, 112);
//		backColor = new Color(230, 230, 250);
//		backColor = new Color(255, 192, 203);//粉红
//		backColor = new Color(127, 255, 170);//绿玉\碧绿色
		backColor = new Color(250, 235, 215);//古白	
		
		Font contextFont = new Font(_fontRomen, Font.BOLD, 15);
		Font enterFont = new Font(_fontRomen, Font.PLAIN, 10);

		JPanel container_JPanel = (JPanel)this.getContentPane();
		
		container_JPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				synchronized (_objectLock) {					
					open = true;
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				synchronized (_objectLock) {
					open = false;
					startMove();
				}
			}
			
		});
		
		JButton close_JButton = getCloseJButton();
		JButton min_JButton = getMinJButton();
		
		JLabel move_Lable = getMoveJLabel(close_JButton.getWidth() + min_JButton.getWidth());
		
		JLabel userLabel = new JLabel();
		JLabel pasLabel = new JLabel();
		userLabel.setBounds(_width/9, _height/3, 80, 20);
		pasLabel.setBounds(_width/9, userLabel.getY() + 30, 80, 20);
		userLabel.setText(_userL);
		pasLabel.setText(_pasL);
		
		userLabel.setFont(contextFont);
		pasLabel.setFont(contextFont);
		
		userText = new TextField();
		pasText = new TextField();
		
		FieldListener userFieldLis = new FieldListener(_tipText_User, userText, "textfield0");
		FieldListener pasFieldLis = new FieldListener(_tipText_Pas, pasText, "textfield1");
		
		userText.setBounds(_width/3 + 25, _height/3, 150, 20);
		pasText.setBounds(_width/3 + 25, userText.getY() + 30, 150, 20);
		userText.setText(_tipText_User);
		pasText.setText(_tipText_Pas);
		userText.setFont(enterFont);
		pasText.setFont(enterFont);
		pasText.enableInputMethods(false);
		userText.addMouseListener(userFieldLis);
		userText.addFocusListener(userFieldLis);
		userText.addKeyListener(userFieldLis);
		pasText.addMouseListener(pasFieldLis);
		pasText.addFocusListener(pasFieldLis);
		pasText.addKeyListener(pasFieldLis);
		
		loginButton = new Button();
		closeButton = new Button();
		loginButton.setBounds(_width/5, _height*2/3, 80, 20);
		closeButton.setBounds(loginButton.getX() + loginButton.getWidth() + 20, _height*2/3, 80, 20);
		loginButton.setLabel(_butLabel_Log);
		closeButton.setLabel(_butLabel_Close);
		loginButton.setFont(contextFont);
		closeButton.setFont(contextFont);
		closeButton.setName("close");
		
		loginButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				MessageWindow messageWindow = FieldListener.loginMainWindow();
				if (messageWindow == null) {
					FieldListener.loginSuccess(ClientLogin.createClientLogin());
				}
//				FieldListener.loginMainWindowForWait();
			}
		});
		
		closeButton.addMouseListener(new TopButtonListener(this));
		
		JPanel backGroundJPanel = new JPanel();
		backGroundJPanel.setBackground(backColor);
		backGroundJPanel.setBounds(0, 0, _width, _height);
		
		container_JPanel.add(close_JButton);
		container_JPanel.add(min_JButton);
		container_JPanel.add(move_Lable);
		
		container_JPanel.add(userLabel);
		container_JPanel.add(pasLabel);
		
		container_JPanel.add(userText);
		container_JPanel.add(pasText);

		container_JPanel.add(loginButton);
		container_JPanel.add(closeButton);
		
		container_JPanel.setOpaque(false);
		
		this.getLayeredPane().add(backGroundJPanel, new Integer(Integer.MIN_VALUE));
		this.setVisible(true);
		
	}
	
	
	@Override
	public void paint(Graphics arg0) {
		
		super.paint(arg0);
		arg0.setColor(new Color(132, 112, 255));
		arg0.draw3DRect(0, 0, _width - 1, _height - 1, true);
		
		Graphics2D g2d = (Graphics2D)arg0;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 215, 0));
		
//		if (pointList.size() < 20) {
//			Point p = triangleModel.randomPoint();
//			pointList.add(p);
//		}
		
		if (pointModelList.size() < 20) {
			PointModel pm = new PointModel(_width, _height);
			pointModelList.add(pm);
		}
		
		Iterator<PointModel> iterator = pointModelList.iterator();
		while (iterator.hasNext()) {
			
			PointModel pointModel = (PointModel) iterator.next();
//			pointList.add(new Point(pointModel.getPoint()));
//			triangleModel.pointMove(point);
			pointModel.pointMove();
			Point point = pointModel.getPoint();
//			pointList.add(new Point(point));
//			System.out.println(point.x);
			g2d.fillOval(point.x, point.y, 5, 5);
			
			if(!pointModel.inPanel()) {
				iterator.remove();
			}
		}
		
		
		for(int i = pointModelList.size()-1; i > 0; i--) {
			Point point = pointModelList.get(i).getPoint();
			for(int j = 0; j < i; j++) {
				Point nextPoint = pointModelList.get(j).getPoint();
				int abs_X = Math.abs(point.x-nextPoint.x);
				int abs_Y = Math.abs(point.y-nextPoint.y);
				int distance = (int) Math.sqrt(abs_X * abs_X + abs_Y * abs_Y);
				if (distance < 50) {				
//					g2d.setColor(Color.BLACK);
					g2d.drawLine(point.x + 2, point.y + 2, nextPoint.x + 2, nextPoint.y + 2);
				}
			}
		}
		
//		for (int i = 0; i < pointModelList.size() - 1; i++) {
//			Point point = pointModelList.get(i).getPoint();
//			Point nextPoint = pointModelList.get(i+1).getPoint();
//			int abs_X = Math.abs(point.x-nextPoint.x);
//			int abs_Y = Math.abs(point.y-nextPoint.y);
//			int distance = (int) Math.sqrt(abs_X * abs_X + abs_Y * abs_Y);
//			if (distance < 20) {				
////				g2d.setColor(Color.BLACK);
//				g2d.drawLine(point.x + 2, point.y + 2, nextPoint.x + 2, nextPoint.y + 2);
//			}
//		}
		
//		Iterator<Point> it = pointList.iterator();
//		while (it.hasNext()) {
//			Point point = (Point) it.next();			
//			triangleModel.pointMove(point);
//			g2d.fillOval(point.x, point.y, 5, 5);
//			if(!triangleModel.inPanel(point)) {
//				iterator.remove();
//			}
//		}
		
//		for (int i = 0; i < pointList.size() - 1; i++) {
//			Point point = pointList.get(i);
//			System.err.println(pointList.size());
//			Point nextPoint = pointList.get(i+1);
//			g2d.setColor(Color.BLACK);
//			g2d.drawLine(point.x, point.y, nextPoint.x, nextPoint.y);
//		}
		
	}

	private void clientLoginInFo(){
		
//		this.set_widht(330);
//		this.set_height(220);
//		this.setSize(_widht, _height);
		
		this._width(330);
		this._height(220);
		this.setSize(_width, _height);
		
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
	}
	
	public synchronized static ClientLogin createClientLogin(){
		if (_clientLogin == null) {
			_clientLogin = new ClientLogin();
		}
		return _clientLogin;
	}
	
	public synchronized static void createClientLogin(String userName){
		createClientLogin().userText.setText(userName);
	}
	
	public static void set_clientLogin(ClientLogin _clientLogin) {
		ClientLogin._clientLogin = _clientLogin;
	}
	
	private void startMove() {
		final Timer clickTimer = new Timer();
		clickTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				repaint();
				synchronized(_objectLock) {					
					if (open) {
						this.cancel();
						//关闭定时器线程
						clickTimer.cancel();
					}
				}
			}
		}, new Date(), 40);
		//建议gc回收无用变量引用
//		System.gc();
	}
	
	public static void main(String[] args) {
		ClientLogin.createClientLogin().startMove();
		
	}
	
}
