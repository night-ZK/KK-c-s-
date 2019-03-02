package frame;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.CloseListener;
import listener.FieldListener;

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
	
	private final String _saveUserName;
	private final String _savePassWord;
	
	protected int _height;
	protected int _widht;
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

	static{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		
		_screenWidht = screenSize.width;
		_screenHeight = screenSize.height;	
		_closeJButton_Ago = new ImageIcon("./resources/image/close_button-1.png");
		_closeJButton_After = new ImageIcon("./resources/image/close_button-2.png");
	}
	
	protected Window(String userName, String passWord){
		_saveUserName = userName;
		_savePassWord = passWord;
	}

	protected Window(){
		_saveUserName = null;
		_savePassWord = null;
	}
	
	/**
	 * 获得关闭按钮
	 * @return
	 */
	protected JButton getCloseJButton(){
		
		JButton jButton = new JButton();
		jButton.setBounds(this._widht - 31, 1, 30, 30);
		
		jButton.setBorder(null);
		jButton.setIcon(_closeJButton_Ago);
		jButton.addMouseListener(new CloseListener(this, jButton, _closeJButton_Ago, _closeJButton_After));
		return jButton;
	}
	
	/**
	 * 获得移动JLabel
	 * @param retainWidth
	 * @return
	 */
	protected JLabel getMoveJLabel(int retainWidth){
		JLabel moveJLable = new JLabel();
		moveJLable.setBounds(retainWidth, 0, this._widht - 2*retainWidth, 30);
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
		jpanel_BackGroundImage.setBounds(0, 0, _widht, _height);
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
		JPanel jpanelImage = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(java.awt.Graphics arg0) {
				super.paintComponent(arg0);
				ImageIcon userIcon = new ImageIcon(path);
				arg0.drawImage(userIcon.getImage(), 0, 0, getWidth(), getHeight(), userIcon.getImageObserver());
			}
		};
		return jpanelImage;
	}
	
	
	public String get_saveUserName() {
		return _saveUserName;
	}

	public String get_savePassWord() {
		return _savePassWord;
	}

	public double get_x() {
		return _x;
	}

	public void set_x(double proportionMolecule_X) {
		this._x = _screenWidht * proportionMolecule_X/_PROPORTIONDENOMINATOR_W;
	}

	public double get_y() {
		return _y;
	}

	public void set_y(double proportionMolecule_Y) {
		this._y = _screenHeight * proportionMolecule_Y/_PROPORTIONDENOMINATOR_H;
	}

	public int get_height() {
		return _height;
	}

	public void set_height(int proportionMolecule_H) {
//		this._height = _screenHeight * proportionMolecule_H/_PROPORTIONDENOMINATOR_H;
		this._height = 635;
	}

	public int get_widht() {
		return _widht;
	}

	public void set_widht(int proportionMolecule_W) {
//		this._widht = _screenWidht * proportionMolecule_W/_PROPORTIONDENOMINATOR_W;
		this._widht = 280;
	}
	
}
