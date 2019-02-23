package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.EvenProcess;
import frame.Window;
import tablebeans.User;

public class MainWindow extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static MainWindow _mainWindow = null;
	
	private MainWindow(String userName, String passWord){
		super(userName, passWord);
		mainWindowInFo();
		
		JPanel container_JPanel = (JPanel)this.getContentPane();
		container_JPanel.setOpaque(false);
		
		JButton jButton = getCloseJButton();
	
		JLabel moveJLable = getMoveJLabel(jButton.getWidth());

		JLabel jLabel = new JLabel("welcome to use this software..");
		jLabel.setBounds(10, 10, 200, 15);
		
//		Graphics graphics = container_JPanel.getGraphics();
//		graphics.drawString("welcome to use this software..", 10, 10);
		
		container_JPanel.add(jButton);
		container_JPanel.add(jLabel);
		container_JPanel.add(moveJLable);
		
		JPanel jpanel_UserInformation = new JPanel();
		jpanel_UserInformation.setBounds(0, 30, _widht, _height/6);
	
		JPanel jpanel_UserImage = getJpanelImage(_path);
		jpanel_UserImage.setBounds(10, 10, 100, 100);
		
		jpanel_UserInformation.setLayout(null);
		jpanel_UserInformation.setOpaque(false);
		jpanel_UserInformation.add(jpanel_UserImage);
		
		Label nickNameLabel = new Label("nickName:  " + this._nickName);
		nickNameLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		Label stateLabel = new Label("state:  " + this._loginState);
		stateLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10 + nickNameLabel.getY() + nickNameLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		Label genderLabel = new Label("gender:  " + this._gender);
		genderLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10 + stateLabel.getY() + stateLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		Label personLabel = new Label("personality label:  " + this._personLabel);
		personLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10 + genderLabel.getY() + genderLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
//		personLabel.setVisible(true);
//		Graphics graphics = personLabel.getGraphics();
//		FontMetrics fontMetrics = graphics.getFontMetrics();
//		fontMetrics.getAscent();
//		int personFintMet = fontMetrics.stringWidth("personality label:  " + this._personLabel);
//		int boundMet = jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30;
//		if (personFintMet > boundMet) {
//			graphics.drawLine(20 + jpanel_UserImage.getWidth()
//					, 3 + genderLabel.getY() + genderLabel.getHeight()
//					, boundMet, 30);
//		}
		
		jpanel_UserInformation.add(nickNameLabel);
		jpanel_UserInformation.add(stateLabel);
		jpanel_UserInformation.add(genderLabel);
		jpanel_UserInformation.add(personLabel);
		
		
		JPanel jpanel_FriendsList = new JPanel();
		int jpanel_FriendsList_y = jpanel_UserInformation.getHeight() + jpanel_UserInformation.getY();
		jpanel_FriendsList.setBounds(0, jpanel_FriendsList_y, _widht, _height*4/6);
		jpanel_FriendsList.setOpaque(false);
//		jpanel_FriendsList.setLayout(arg0);
		
		
		
		JPanel jpanel_UserSystem = new JPanel();
		int jpanel_UserSystem_y = jpanel_FriendsList_y + jpanel_FriendsList.getHeight();
		int jpanel_UserSystem_w = _height - jpanel_UserSystem_y;
		jpanel_UserSystem.setBounds(0, jpanel_UserSystem_y, _widht, jpanel_UserSystem_w);
		jpanel_UserSystem.setBackground(new Color(255, 181, 197));
		
		
//		GraphicsDevice[] gDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
//		try {
//			Robot robot = new Robot(gDevice[0]);
//			BufferedImage bi = robot.createScreenCapture(new Rectangle(_screenWidht, _screenHeight));
//			ImageIO.write(bi, "png", new File("E:\\img\\4.png"));
//			System.out.println("success..");
//		}
//		catch (AWTException e) {
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			System.out.println("faild..");
//			e.printStackTrace();
//		}
		

//		this.getLayeredPane().add(jpanel_BackGroundImage, new Integer(Integer.MIN_VALUE));
		container_JPanel.add(jpanel_UserInformation);
		container_JPanel.add(jpanel_FriendsList);
		container_JPanel.add(jpanel_UserSystem);
		System.out.println("L:"+_height);
		
		this.setJPanelsetBackGroundImage("E:\\img\\5.jpg");
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		arg0.setColor(new Color(132,112,255));
		arg0.draw3DRect(0, 0, _widht - 1, _height - 1, true);
	}
	
	private void mainWindowInFo() {
		ArrayList<User> userList = EvenProcess.getUserInfo(get_saveUserName());
		User user = userList.get(0);
		switch (user.getUserState()) {
			case "0":				
				this._loginState = "OnLine";
				break;
			case "1":				
				this._loginState = "Invisibilit";
				break;
			case "2":				
				this._loginState = "OutLogin";
				break;
			default:
				break;
		}
		switch (user.getGender().intValue()) {
			case 0:
				this._gender = "man";
				break;
			case 1:
				this._gender = "woman";
				break;
			default:
				break;
		}
		this._nickName = user.getUserNick();
		this._path = user.getUserImagepath();
		this._personLabel = user.getPersonLabel();
		
		System.out.println(",W:" + _screenWidht + ",h:" + _screenHeight);
		
		//参数为所占比例的分子
		this.set_widht(240);
		this.set_height(750);
		this.set_x(960);
		this.set_y(40);
		
		this.setSize(_widht, _height);
		this.setResizable(false);
		this.setLocation((int)_x, (int)_y);
		this.setLayout(null);
		this.setUndecorated(true);
		
		
		System.out.println(",TW:" + this._widht + ",Th:" + this._height);
	}
	
	public static MainWindow createMainWindow(String username, String password){
		if (_mainWindow == null) {
			
			if (username == null || password == null) {
				return null;
			}
			_mainWindow = new MainWindow(username, password);
		}
		return _mainWindow;
	}
	public static void main(String[] args) {
		MainWindow.createMainWindow("user", "pas");
	}
}
