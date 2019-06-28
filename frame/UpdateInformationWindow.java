package frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import frame.customComponent.ChatMessageTextJPanel;
import listener.UpdateFieldListener;
import message.MessageContext;
import message.MessageModel;
import model.UpdateInformation;
import threadmanagement.LockModel;
import tools.TransmitTool;
import transmit.MessageManagement;
import transmit.nio.SocketClientNIO;

public class UpdateInformationWindow extends Window{

	private static final long serialVersionUID = 5L;
	
	String path;
	String newPath;
	ImageIcon userIcon;
	
	
	JPanel jpanel_UserImage;
	//�û��ǳ�
	JTextField nickNameLabel;
	
	//�û�״̬
	JTextField stateLabel;
	
	//�Ա�
	JTextField genderLabel;
	JRadioButton manRB;
	JRadioButton womanRB;
	
	JButton chooseImageFileButton;
			
	//����ǩ��
	ChatMessageTextJPanel personLabel;
	
	JButton submitButton;
	
	JButton clenButton;

	public UpdateInformationWindow() {
		initUserInformation();
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
		userIcon = new ImageIcon(path);
		jpanel_UserImage = getJpanelImage(userIcon);
		jpanel_UserImage.setBounds(10, 10, 80, 80);
		
		//�û��ǳ�
		nickNameLabel = new JTextField("nickName:  " + this._nickName);
		nickNameLabel.setName(nickNameLabel.getText());
		nickNameLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10, getWidth() - jpanel_UserImage.getWidth() - 50
				, 16);
		
		
		//�û�״̬
		stateLabel = new JTextField("state:  " + this._loginState);
		stateLabel.setName(stateLabel.getText());
		stateLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 16 + nickNameLabel.getY() + nickNameLabel.getHeight()
				, getWidth() - jpanel_UserImage.getWidth() - 50
				, 16);
		
		//�Ա�
//		genderLabel = new JTextField("gender:  " + this._gender);
//		genderLabel.setName(genderLabel.getText());
//		genderLabel.setBounds(20 + jpanel_UserImage.getWidth()
//				, 16 + stateLabel.getY() + stateLabel.getHeight()
//				, getWidth() - jpanel_UserImage.getWidth() - 50
//				, 16);
		
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
		
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(manRB);
		genderGroup.add(womanRB);
		
		chooseImageFileButton = new JButton("chooseImage");
		chooseImageFileButton.setBounds(jpanel_UserImage.getX()
				, 10 + jpanel_UserImage.getY() + jpanel_UserImage.getHeight()
				, jpanel_UserImage.getWidth()
				, 20);
		//���߿�
		chooseImageFileButton.setBorder(null);
		//��Ĭ�ϱ������
//		chooseImageFileButton.setContentAreaFilled(false);
		
//		jButton.setBorderPainted(false);//����ӡ�߿�
		chooseImageFileButton.setFocusPainted(false);//�����
		chooseImageFileButton.setIconTextGap(0);//ͼƬ���ּ��������Ϊ0
		chooseImageFileButton.setToolTipText("click for choose ImageIcon..");
				
		//����ǩ��
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
		submitButton.setFocusPainted(false);//�����
		submitButton.setIconTextGap(0);//ͼƬ���ּ��������Ϊ0
		
		clenButton = new JButton("clean");
		clenButton.setBounds(getWidth() / 2
				, 30 + personLabel.getY() + personLabel.getHeight()
//				, getWidth() - jpanel_UserImage.getX() - (getWidth() - genderLabel.getX() - genderLabel.getWidth())
				, getWidth() / 4
				, 25);
		
		clenButton.setBorder(null);
		clenButton.setFocusPainted(false);//�����
		clenButton.setIconTextGap(0);//ͼƬ���ּ��������Ϊ0
		
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
//		genderLabel.addFocusListener(updateFieldListener);
		personLabel.addFocusListener(updateFieldListener);
		
		chooseImageFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new MessageWindow("@~@..", "Coming soon..", JRootPane.FRAME);
			}
		});
		
		
		clenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				byte[] iconByte = null;
				if(newPath != null && !newPath.equals(path) && userIcon != null) 
					iconByte = TransmitTool.getImageBytesByImage(userIcon.getImage());
				
				int gender = manRB.isSelected() ? 1 : 0;
				
				UpdateInformation updateInformation =
						new UpdateInformation(iconByte
								, nickNameLabel.getText()
								, stateLabel.getText()
								, gender
								, personLabel.getText()
								, Window.getSaveUserID().intValue());
				
				MessageModel updateUserInfoModel = MessageManagement.getUpdateUserInfoMessageModel(updateInformation);
				LockModel lockModel = new LockModel(0, "updateUserInformation request");
				
				try {
					MessageContext replyMessageContext = 
							TransmitTool.sendRequestMessageForNIOByBlock(updateUserInfoModel, lockModel).getMessageContext();
//					SocketClientNIO.createSocketClient().sendReuqest(updateUserInfoModel);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
	}

	
}
