package frame;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.CloseListener;
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
	
	private TextField userText = new TextField();
	private TextField pasText = new TextField();

	static{
		 _userL = "username:";
		 _pasL = "password:";
		 _butLabel_Log = "login";
		 _butLabel_Close = "close";
		 
		 _tipText_User = "please enter username..";
		 _tipText_Pas = "please enter password..";
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

		Font contextFont = new Font(_fontRomen, Font.BOLD, 15);
		Font enterFont = new Font(_fontRomen, Font.PLAIN, 10);

		JPanel container_JPanel = (JPanel)this.getContentPane();
		
		
		JButton close_JButton = getCloseJButton();
		
		JLabel move_Lable = getMoveJLabel(close_JButton.getWidth());
		
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
		
		Button loginButton = new Button();
		Button closeButton = new Button();
		loginButton.setBounds(_width/5, _height*2/3, 80, 20);
		closeButton.setBounds(loginButton.getX() + loginButton.getWidth() + 20, _height*2/3, 80, 20);
		loginButton.setLabel(_butLabel_Log);
		closeButton.setLabel(_butLabel_Close);
		loginButton.setFont(contextFont);
		closeButton.setFont(contextFont);
		
		loginButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				FieldListener.loginMainWindow();
			}
		});
		
		closeButton.addMouseListener(new CloseListener(this));
		
		JPanel backGroundJPanel = new JPanel();
		Color color = new Color(240,255,240);
		backGroundJPanel.setBackground(color);
		backGroundJPanel.setBounds(0, 0, _width, _height);
		
		container_JPanel.add(close_JButton);
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
		arg0.setColor(new Color(132,112,255));
		arg0.draw3DRect(0, 0, _width - 1, _height - 1, true);
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
	
	public static void main(String[] args) {
		ClientLogin.createClientLogin();
	}
	
}
