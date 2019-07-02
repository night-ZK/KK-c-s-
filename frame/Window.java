package frame;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.TopButtonListener;
import listener.FieldListener;
import tablebeans.User;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 1L;

	protected final static int _PROPORTIONDENOMINATOR_W = 1280;
	protected final static int _PROPORTIONDENOMINATOR_H = 1024;
	protected final static int _PROPORTIONDENOMINATOR_X = 1280;
	protected final static int _PROPORTIONDENOMINATOR_Y = 1024;

	protected static int _screenWidht;
	protected static int _screenHeight;
	protected static ImageIcon _closeJButton_Ago;
	protected static ImageIcon _closeJButton_After;
	protected static ImageIcon _default_Icon;
	protected static ImageIcon _main_Icon;
	protected static ImageIcon _minJButton_Ago;
	protected static ImageIcon _minJButton_After;
	
	private static User _saveUser;
	
	protected static SimpleDateFormat _dateFormat;

	protected int _height;
	protected int _width;
	protected double _x;
	protected double _y;
	protected String _fontRomen;
	
	protected Number _id; 
	protected String _nickName;
	protected String _loginState;
	protected String _path;
	protected Number _findSum;
	protected String _gender;
	protected String _personLabel;

	protected int _genderState;
	

	static{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		
		_screenWidht = screenSize.width;
		_screenHeight = screenSize.height;	
		_closeJButton_Ago = new ImageIcon("./resources/image/close_button-1.png");
		_closeJButton_After = new ImageIcon("./resources/image/close_button-2.png");
		_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		_default_Icon = new ImageIcon("./resources/icon/ZK-RGB.png");
		_main_Icon = new ImageIcon("./resources/icon/ZK-Main.png");
		_minJButton_Ago = new ImageIcon("./resources/image/min_ago.png");
		_minJButton_After = new ImageIcon("./resources/image/min_after.png");
	}
	
	protected Window(User user){
		this();
		_saveUser = user;
		this.setIconImage(_main_Icon.getImage());
		initUserInformation();
	}

	protected Window(){
		this.setIconImage(_default_Icon.getImage());
	}
	
	protected void saveImage(byte[] iconBytes) {

		File iFile = new File("./resources/icon/" + this._id + ".png");
		if(!iFile.exists())
			try {
				iFile.getParentFile().mkdirs();
				iFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try (FileImageOutputStream fio 
				= new FileImageOutputStream(iFile)){
			
			fio.write(iconBytes,0,iconBytes.length);
//			fio.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化用户相关信息
	 */
	protected void initUserInformation() {
		
		User user = getUserInfo();
		
		//初始化用户状态
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
				this._loginState = user.getUserState();
				break;
		}
		
		this._genderState = user.getGender().intValue(); 
		//初始化用户性别
		switch (this._genderState) {
			case 0:
				
				this._gender = "man";
				break;
			case 1:
				this._gender = "woman";
				break;
			default:
				break;
		}
		
		this._id = user.getId();
		this._nickName = user.getUserNick();
		this._path = user.getUserImagepath();//绝对路径
		this._personLabel = user.getPersonLabel();
		this._findSum = user.getFiendSum();
	}
	
	/**
	 * 获得关闭按钮
	 * @return
	 */
	protected JButton getCloseJButton(){
		
		JButton jButton = new JButton(_closeJButton_Ago);
		jButton.setBounds(this._width - 31, 0, 30, 30);
		
		//除边框
		jButton.setBorder(null);
		//除默认背景填充
		jButton.setContentAreaFilled(false);
		
//		jButton.setBorderPainted(false);//不打印边框
		jButton.setFocusPainted(false);//焦点框
		jButton.setIconTextGap(0);//图片文字间隔量设置为0
//		jButton.setMargin(new Insets(0, 0, 0, 0));
		jButton.setName("close");
		jButton.addMouseListener(new TopButtonListener(this, jButton, _closeJButton_Ago, _closeJButton_After));
		return jButton;
	}
	
	/**
	 * 获得最小化按钮
	 * @return
	 */
	protected JButton getMinJButton(){
		
		JButton jButton = new JButton(_minJButton_Ago);
		jButton.setBounds(this._width - 60, 1, 30, 30);
		
		//除边框
		jButton.setBorder(null);
		//除默认背景填充
		jButton.setContentAreaFilled(false);
		
//		jButton.setBorderPainted(false);//不打印边框
		jButton.setFocusPainted(false);//焦点框
		jButton.setIconTextGap(0);//图片文字间隔量设置为0
//		jButton.setMargin(new Insets(0, 0, 0, 0));
		jButton.setName("min");
		jButton.addMouseListener(new TopButtonListener(this, jButton, _minJButton_Ago, _minJButton_After));
		return jButton;
	}
	
	/**
	 * 获得移动JLabel
	 * @param retainWidth 前后的间距
	 * @return
	 */
	protected JLabel getMoveJLabel(int retainWidth){
		JLabel moveJLable = new JLabel();
		moveJLable.setBounds(retainWidth, 0, this._width - 2*retainWidth, 30);
		moveJLable.setName("move");
		moveJLable.addMouseListener(new FieldListener(this, moveJLable.getName()));
		return moveJLable;
	}
	
	/**
	 * 获得好友列表形式的JButton
	 * @param text 显示文字
	 * @param nameToIndex 使用那么属性作为下标
	 * @return
	 */
	protected JButton getFriendListButton(String text, String nameToIndex) {
		JButton friendListButton = new JButton(text);
		//TODO 使用系统设置字体
		friendListButton.setFont(new Font("", Font.BOLD, 12));
		friendListButton.setHorizontalAlignment(JButton.LEFT);
		friendListButton.setName(nameToIndex);
		return friendListButton;
	}
	
	/**
	 * 设置背景图片
	 * @param path 图片路径
	 */
	protected void setJPanelBackGroundImage(final String path) {
		JPanel jpanel_BackGroundImage = getJpanelImage(path);
		jpanel_BackGroundImage.setBounds(0, 0, _width, _height);
		this.getLayeredPane().add(jpanel_BackGroundImage, new Integer(Integer.MIN_VALUE));
	}
	
	/**
	 * 获得带有背景图的JPanel
	 * @param brakground 背景图片
	 * @return
	 */
	protected JPanel getJpanelImage(final ImageIcon brakground){
		JPanel jpanelImage = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(java.awt.Graphics arg0) {
				super.paintComponent(arg0);
				arg0.drawImage(brakground.getImage(), 0, 0, getWidth(), getHeight(), brakground.getImageObserver());
			}
		};
		return jpanelImage;
	}
	
	/**
	 * 获得带有背景图的JPanel
	 * @param path 图片路径
	 * @return
	 */
	protected JPanel getJpanelImage(final String path){
		ImageIcon userIcon = new ImageIcon(path);
		return getJpanelImage(userIcon);
	}
	
	/**
	 * 获得带有背景图的JPanel
	 * @param imageBytes 图片字节数组
	 * @return
	 */
	protected JPanel getJpanelImage(final byte[] imageBytes){
		JPanel jpanelImage = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(java.awt.Graphics arg0) {
				super.paintComponent(arg0);
				ImageIcon userIcon = new ImageIcon(imageBytes);
				arg0.drawImage(userIcon.getImage(), 0, 0, getWidth(), getHeight(), userIcon.getImageObserver());
			}
		};
		return jpanelImage;
	}
	
	protected User getUserInfo() {
		//TODO privilege management
		return _saveUser;
	}
	
	/**
	 * 返回当前用户名
	 * @return 用户名
	 */
	public static String getSaveUserName() {
		return _saveUser.getUserName();
	}
	
	/**
	 * 返回当前用户名
	 * @return 用户名
	 */
	public static String getSaveUserNick() {
		return _saveUser.getUserNick();
	}
	
	/**
	 * 用户ID = user表ID
	 * @return ID
	 */
	public static Number getSaveUserID() {
		return _saveUser.getId();
	}

	public double get_x() {
		return _x;
	}

	/**
	 * 设置组件的x点坐标, 参数为屏占比的分子
	 * @param proportionMolecule_X
	 */
	public void set_x(double proportionMolecule_X) {
		this._x = _screenWidht * proportionMolecule_X/_PROPORTIONDENOMINATOR_W;
	}

	/**
	 * 设置组件的x点坐标, 如果参数为0则, 组件在中间位置;
	 * 如果参数为10, 则组件的x点坐标为原中间坐标 - 10
	 * @param Ri_Displacement
	 */
	public void set_X(int Ri_Displacement) {
		this._x = ((_screenWidht / 2) - (this.get_width() / 2)) + Ri_Displacement;
	}
	
	public double get_y() {
		return _y;
	}

	/**
	 * 
	 * 设置组件的y点坐标, 参数为屏占比的分子
	 * @param proportionMolecule_Y
	 */
	public void set_y(double proportionMolecule_Y) {
		this._y = _screenHeight * proportionMolecule_Y/_PROPORTIONDENOMINATOR_H;
	}

	/**
	 * 设置组件的y点坐标, 如果参数为0则, 组件在中间位置;
	 * 如果参数为10, 则组件的y点坐标为原中间坐标 - 10
	 * @param up_Displacement
	 */
	public void set_y(int up_Displacement) {
		this._y = ((_screenHeight / 2) - (this.get_height() / 2)) - up_Displacement;
	}
	
	public int get_height() {
		return _height;
	}

	/**
	 * 设置height值
	 * @param proportionMolecule_H 屏占比的分子
	 */
	public void set_height(int proportionMolecule_H) {
//		this._height = _screenHeight * proportionMolecule_H/_PROPORTIONDENOMINATOR_H;
		this._height = 635;
	}
	
	/**
	 * 设置height的值
	 * @param height 实际需要的height
	 */
	public void _height(int height) {
		this._height = height;
	}
	

	/**
	 * 设置width值
	 * @param width 实际需要的width
	 */
	public void _width(int width) {
		this._width = width;
	}
	
	public int get_width() {
		return _width;
	}

	public void set_width(int proportionMolecule_W) {
//		this._widht = _screenWidht * proportionMolecule_W/_PROPORTIONDENOMINATOR_W;
		this._width = 280;
	}
	
}
