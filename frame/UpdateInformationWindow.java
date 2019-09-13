package frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import frame.customComponent.ChatMessageTextJPanel;
import listener.UpdateFieldListener;
import message.MessageHead;
import message.MessageModel;
import model.UpdateInformation;
import tablebeans.User;
import threadmanagement.LockModel;
import tools.ImageTools;
import tools.TransmitTool;
import transmit.MessageManagement;
import transmit.nio.SocketClientNIO;

public class UpdateInformationWindow extends Window{

	private static final long serialVersionUID = 5L;
	
	String path;
	String newPath;
	ImageIcon userIcon;
	FileNameExtensionFilter fnef; 
	
	JPanel jpanel_UserImage;
	//用户昵称
	JTextField nickNameLabel;
	//用户状态
	JTextField stateLabel;
	//性别
	ButtonGroup genderGroup;
	JRadioButton manRB;
	JRadioButton womanRB;	
	//个性签名
	ChatMessageTextJPanel personLabel;
			
	JButton chooseImageFileButton;
	JButton submitButton;
	JButton clenButton;

	File fileChooserPathDir;
		
	public UpdateInformationWindow() {
		
//		UIManager.setLookAndFeel();
		initUserInformation();
		fnef = new FileNameExtensionFilter("*.png", "png");
		
		this.setSize(400, 300);
		JPanel jPanel = (JPanel)this.getContentPane();
		jPanel.setLayout(null);
		initUserInformationJPanel(jPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
//		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	private void initUserInformationJPanel(JPanel jpanel_UserInformation) {

		path = "./resources/icon/" + this._id + ".png";
		userIcon = new ImageIcon(MainWindow.createMainWindow().get_iconBytes());
//		ImageTools.saveImage(ImageTools.getImageBytesByImage(userIcon.getImage())
//				, "G:\\image\\"+System.currentTimeMillis()+".png");
		jpanel_UserImage = getJpanelImage(userIcon);
		jpanel_UserImage.setBounds(10, 10, 80, 80);
		
		//用户昵称
		nickNameLabel = new JTextField("nickName:  " + this._nickName);
		nickNameLabel.setName(nickNameLabel.getText());
		nickNameLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10, getWidth() - jpanel_UserImage.getWidth() - 50
				, 16);
		
		
		//用户状态
		stateLabel = new JTextField("state:  " + this._loginState);
		stateLabel.setName(stateLabel.getText());
		stateLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 16 + nickNameLabel.getY() + nickNameLabel.getHeight()
				, getWidth() - jpanel_UserImage.getWidth() - 50
				, 16);
		
		manRB = new JRadioButton("Man");
		int width = (getWidth() - jpanel_UserImage.getWidth() - 50)/3;
		manRB.setBounds(20 + jpanel_UserImage.getWidth()
		, 16 + stateLabel.getY() + stateLabel.getHeight()
		, width
		, 16);
		womanRB = new JRadioButton("WoMan");
		womanRB.setBounds(manRB.getX() + manRB.getWidth() + width/2
		, 16 + stateLabel.getY() + stateLabel.getHeight()
		, width
		, 16);
		
		switch (this._genderState) {
		case 0:	
			manRB.setSelected(true);
			break;
		case 1:			
			womanRB.setSelected(true);
			break;
		default:
			break;
		}
		
		genderGroup = new ButtonGroup();
		genderGroup.add(manRB);
		genderGroup.add(womanRB);
		
		chooseImageFileButton = new JButton("chooseImage");
		chooseImageFileButton.setBounds(jpanel_UserImage.getX()
				, 10 + jpanel_UserImage.getY() + jpanel_UserImage.getHeight()
				, jpanel_UserImage.getWidth()
				, 20);
		//除边框
		chooseImageFileButton.setBorder(null);
		//除默认背景填充
//		chooseImageFileButton.setContentAreaFilled(false);
		
//		jButton.setBorderPainted(false);//不打印边框
		chooseImageFileButton.setFocusPainted(false);//焦点框
		chooseImageFileButton.setIconTextGap(0);//图片文字间隔量设置为0
		chooseImageFileButton.setToolTipText("click for choose ImageIcon..");
				
		//个性签名
		personLabel = new ChatMessageTextJPanel();
		personLabel.setText("personality label:  " + this._personLabel);
		personLabel.setName(personLabel.getText());
		personLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10 + jpanel_UserImage.getY() + jpanel_UserImage.getHeight()
//				, getWidth() - jpanel_UserImage.getX() - (getWidth() - genderLabel.getX() - genderLabel.getWidth())
//				, genderLabel.getX() + genderLabel.getWidth() - jpanel_UserImage.getX()
				, getWidth() - jpanel_UserImage.getWidth() - 50
				, 80);
		personLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		
		submitButton = new JButton("submit");
		submitButton.setBounds(getWidth() / 8
				, 30 + personLabel.getY() + personLabel.getHeight()
				, getWidth() / 4
				, 25);
		
		submitButton.setBorder(null);
		submitButton.setFocusPainted(false);//焦点框
		submitButton.setIconTextGap(0);//图片文字间隔量设置为0
		
		clenButton = new JButton("clean");
		clenButton.setBounds(getWidth() / 2
				, 30 + personLabel.getY() + personLabel.getHeight()
//				, getWidth() - jpanel_UserImage.getX() - (getWidth() - genderLabel.getX() - genderLabel.getWidth())
				, getWidth() / 4
				, 25);
		
		clenButton.setBorder(null);
		clenButton.setFocusPainted(false);//焦点框
		clenButton.setIconTextGap(0);//图片文字间隔量设置为0
		
		bindExent();
		
		jpanel_UserInformation.add(jpanel_UserImage);
		jpanel_UserInformation.add(nickNameLabel);
		jpanel_UserInformation.add(stateLabel);
//		jpanel_UserInformation.add(genderLabel);
		jpanel_UserInformation.add(manRB);
		jpanel_UserInformation.add(womanRB);
		jpanel_UserInformation.add(personLabel);
		jpanel_UserInformation.add(submitButton);
		jpanel_UserInformation.add(chooseImageFileButton);
		jpanel_UserInformation.add(clenButton);
		
	}

	private void bindExent() {
		UpdateFieldListener updateFieldListener = new UpdateFieldListener();
		nickNameLabel.addFocusListener(updateFieldListener);
		stateLabel.addFocusListener(updateFieldListener);
		personLabel.addFocusListener(updateFieldListener);
		
		
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				byte[] iconByte = null;
				iconByte = ImageTools.getImageBytesByImage(userIcon.getImage());
					
				int gender = manRB.isSelected() ? 0 : 1;
				
				User userInfo = new User();
				userInfo.setUserNick(isOldText(nickNameLabel) ? "" : nickNameLabel.getText());
				userInfo.setUserState(isOldText(stateLabel) ? "" : stateLabel.getText());
				userInfo.setGender(gender);
				userInfo.setPersonLabel(isOldText(personLabel) ? "" : personLabel.getText());
				userInfo.setId(Window.getSaveUserID());
				UpdateInformation updateInformation =
						new UpdateInformation(iconByte, userInfo);
				
				MessageModel updateUserInfoModel = MessageManagement.getUpdateUserInfoMessageModel(updateInformation);
				LockModel lockModel = new LockModel(0, "updateUserInformation request");
				MessageModel replyModel = null;
				try {
					replyModel = 
							TransmitTool.sendRequestMessageForNIOByBlock(updateUserInfoModel, lockModel);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if (replyModel == null) {
					new MessageWindow("Error", "502: Server Error", JRootPane.ERROR_DIALOG);
					return;
				}
				updateThen(replyModel);
				
			}
		});
		
		clenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nickNameLabel.setText(nickNameLabel.getName());
				stateLabel.setText(stateLabel.getName());;
				manRB.setSelected(_genderState == 0);
				womanRB.setSelected(_genderState == 1);
				personLabel.setText(personLabel.getName());
			}
		});
		
		MouseAdapter chooseImage = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				if (fileChooserPathDir != null) {
					jFileChooser.setCurrentDirectory(fileChooserPathDir);
				}
				jFileChooser.setDialogTitle("open");
				jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				jFileChooser.setFileFilter(fnef);
				
				int value = jFileChooser.showOpenDialog(UpdateInformationWindow.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					File selectFile = jFileChooser.getSelectedFile();
					fileChooserPathDir = selectFile.getParentFile();
					String absolutePath = selectFile.getAbsolutePath();
					try {
						userIcon = new ImageIcon(absolutePath);
						UpdateInformationWindow.this.remove(jpanel_UserImage);
						jpanel_UserImage = getJpanelImage(userIcon);
						jpanel_UserImage.setBounds(10, 10, 80, 80);
						UpdateInformationWindow.this.add(jpanel_UserImage);
//						Graphics graphics = jpanel_UserImage.getGraphics();
//						System.out.println("graphics: " + graphics);
//						graphics.drawImage(userIcon.getImage(), 0, 0, jpanel_UserImage.getWidth(), jpanel_UserImage.getHeight()
//								, userIcon.getImageObserver());
//						jpanel_UserImage.repaint();
						UpdateInformationWindow.this.repaint();
					} catch (Exception e2) {
//						e2.printStackTrace();
						new MessageWindow("error", "select file extension error..", JRootPane.ERROR_DIALOG);
					}
				}
			}
		};
		
		jpanel_UserImage.addMouseListener(chooseImage);
		
		chooseImageFileButton.addMouseListener(chooseImage);
	}
	
	public static boolean isOldText(JTextComponent jtf) {
		return jtf.getText().equals(jtf.getName());
	}
	
	public void updateThen(MessageModel replyModel) {
		MessageHead messageHead = replyModel.getMessageHead();
		boolean isUpdateSuccess = messageHead.getReplyDescribe().equals("1");
		if (isUpdateSuccess) {
			ClientLogin.createClientLogin(Window.getSaveUserName());
			MainWindow.createMainWindow().dispose();
			MainWindow.set_mainWindow(null);
			SocketClientNIO.createSocketClient().destroy();
			this.dispose();
			new MessageWindow("tip", "update success..", JRootPane.PLAIN_DIALOG);
			
		}else {			
			new MessageWindow("Error", "update failed..", JRootPane.ERROR_DIALOG);
		}
	}
}
