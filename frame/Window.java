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
	 * ��ʼ���û������Ϣ
	 */
	protected void initUserInformation() {
		
		User user = getUserInfo();
		
		//��ʼ���û�״̬
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
		//��ʼ���û��Ա�
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
		this._path = user.getUserImagepath();//����·��
		this._personLabel = user.getPersonLabel();
		this._findSum = user.getFiendSum();
	}
	
	/**
	 * ��ùرհ�ť
	 * @return
	 */
	protected JButton getCloseJButton(){
		
		JButton jButton = new JButton(_closeJButton_Ago);
		jButton.setBounds(this._width - 31, 0, 30, 30);
		
		//���߿�
		jButton.setBorder(null);
		//��Ĭ�ϱ������
		jButton.setContentAreaFilled(false);
		
//		jButton.setBorderPainted(false);//����ӡ�߿�
		jButton.setFocusPainted(false);//�����
		jButton.setIconTextGap(0);//ͼƬ���ּ��������Ϊ0
//		jButton.setMargin(new Insets(0, 0, 0, 0));
		jButton.setName("close");
		jButton.addMouseListener(new TopButtonListener(this, jButton, _closeJButton_Ago, _closeJButton_After));
		return jButton;
	}
	
	/**
	 * �����С����ť
	 * @return
	 */
	protected JButton getMinJButton(){
		
		JButton jButton = new JButton(_minJButton_Ago);
		jButton.setBounds(this._width - 60, 1, 30, 30);
		
		//���߿�
		jButton.setBorder(null);
		//��Ĭ�ϱ������
		jButton.setContentAreaFilled(false);
		
//		jButton.setBorderPainted(false);//����ӡ�߿�
		jButton.setFocusPainted(false);//�����
		jButton.setIconTextGap(0);//ͼƬ���ּ��������Ϊ0
//		jButton.setMargin(new Insets(0, 0, 0, 0));
		jButton.setName("min");
		jButton.addMouseListener(new TopButtonListener(this, jButton, _minJButton_Ago, _minJButton_After));
		return jButton;
	}
	
	/**
	 * ����ƶ�JLabel
	 * @param retainWidth ǰ��ļ��
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
	 * ��ú����б���ʽ��JButton
	 * @param text ��ʾ����
	 * @param nameToIndex ʹ����ô������Ϊ�±�
	 * @return
	 */
	protected JButton getFriendListButton(String text, String nameToIndex) {
		JButton friendListButton = new JButton(text);
		//TODO ʹ��ϵͳ��������
		friendListButton.setFont(new Font("", Font.BOLD, 12));
		friendListButton.setHorizontalAlignment(JButton.LEFT);
		friendListButton.setName(nameToIndex);
		return friendListButton;
	}
	
	/**
	 * ���ñ���ͼƬ
	 * @param path ͼƬ·��
	 */
	protected void setJPanelBackGroundImage(final String path) {
		JPanel jpanel_BackGroundImage = getJpanelImage(path);
		jpanel_BackGroundImage.setBounds(0, 0, _width, _height);
		this.getLayeredPane().add(jpanel_BackGroundImage, new Integer(Integer.MIN_VALUE));
	}
	
	/**
	 * ��ô��б���ͼ��JPanel
	 * @param brakground ����ͼƬ
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
	 * ��ô��б���ͼ��JPanel
	 * @param path ͼƬ·��
	 * @return
	 */
	protected JPanel getJpanelImage(final String path){
		ImageIcon userIcon = new ImageIcon(path);
		return getJpanelImage(userIcon);
	}
	
	/**
	 * ��ô��б���ͼ��JPanel
	 * @param imageBytes ͼƬ�ֽ�����
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
	 * ���ص�ǰ�û���
	 * @return �û���
	 */
	public static String getSaveUserName() {
		return _saveUser.getUserName();
	}
	
	/**
	 * ���ص�ǰ�û���
	 * @return �û���
	 */
	public static String getSaveUserNick() {
		return _saveUser.getUserNick();
	}
	
	/**
	 * �û�ID = user��ID
	 * @return ID
	 */
	public static Number getSaveUserID() {
		return _saveUser.getId();
	}

	public double get_x() {
		return _x;
	}

	/**
	 * ���������x������, ����Ϊ��ռ�ȵķ���
	 * @param proportionMolecule_X
	 */
	public void set_x(double proportionMolecule_X) {
		this._x = _screenWidht * proportionMolecule_X/_PROPORTIONDENOMINATOR_W;
	}

	/**
	 * ���������x������, �������Ϊ0��, ������м�λ��;
	 * �������Ϊ10, �������x������Ϊԭ�м����� - 10
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
	 * ���������y������, ����Ϊ��ռ�ȵķ���
	 * @param proportionMolecule_Y
	 */
	public void set_y(double proportionMolecule_Y) {
		this._y = _screenHeight * proportionMolecule_Y/_PROPORTIONDENOMINATOR_H;
	}

	/**
	 * ���������y������, �������Ϊ0��, ������м�λ��;
	 * �������Ϊ10, �������y������Ϊԭ�м����� - 10
	 * @param up_Displacement
	 */
	public void set_y(int up_Displacement) {
		this._y = ((_screenHeight / 2) - (this.get_height() / 2)) - up_Displacement;
	}
	
	public int get_height() {
		return _height;
	}

	/**
	 * ����heightֵ
	 * @param proportionMolecule_H ��ռ�ȵķ���
	 */
	public void set_height(int proportionMolecule_H) {
//		this._height = _screenHeight * proportionMolecule_H/_PROPORTIONDENOMINATOR_H;
		this._height = 635;
	}
	
	/**
	 * ����height��ֵ
	 * @param height ʵ����Ҫ��height
	 */
	public void _height(int height) {
		this._height = height;
	}
	

	/**
	 * ����widthֵ
	 * @param width ʵ����Ҫ��width
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
