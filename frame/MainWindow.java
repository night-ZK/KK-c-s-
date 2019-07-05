package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreeSelectionModel;

import frame.Window;
import frame.customComponent.FriendNodeRenderer;
import frame.customComponent.FriendsListTree;
import listener.FriendsListJTreeList;
import message.MessageContext;
import message.MessageModel;
import tablebeans.User;
import tablejson.UserFriendsInformation;
import threadmanagement.LockModel;
import tools.ObjectTool;
import tools.TransmitTool;
import transmit.MessageManagement;
import transmit.getter.Receive;
import transmit.sender.GetRequest;

public class MainWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	private static MainWindow _mainWindow = null;

	static Map<Integer, FriendsListTree> friendsListTreeMap = new HashMap<>();
	
	private byte[] _iconBytes;
	private MainWindow(User user, byte[] iconBytes){
		super(user);

		this._iconBytes =  iconBytes;
		
		saveImage(iconBytes);
		
		initMainWindow();
		
		JPanel container_JPanel = (JPanel)this.getContentPane();
		container_JPanel.setOpaque(false);
		container_JPanel.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 2, true));
		
		//关闭按钮
		JButton close_JButton = getCloseJButton();
		JButton min_JButton = getMinJButton();
	
		//绑定移动事件的的JLable
		JLabel moveJLable = getMoveJLabel(close_JButton.getWidth() + min_JButton.getWidth());

		//欢迎JLable
		JLabel jLabel = new JLabel("welcome to use this software..");
		jLabel.setBounds(10, 10, 200, 15);
		
		//用于显示用户信息的JPanel
		JPanel jpanel_UserInformation = new JPanel(); 		
		//初始化用户信息JPanel
		initUserInformationJPanel(jpanel_UserInformation);
		
		//用于显示好友类别的JPanel
		JPanel jpanel_FriendsList = new JPanel();
		
		//初始化好友列表
		try {
			initFriendsListJPanel(jpanel_FriendsList);
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		
		//用于显示设置系统信息, 用户信息的JPanel
		JPanel jpanel_InformationManagement = new JPanel();
		//初始化信息管理界面
		initInformationManagementJPanel(jpanel_InformationManagement);
		
		container_JPanel.add(close_JButton);
		container_JPanel.add(min_JButton);
		container_JPanel.add(jLabel);
		container_JPanel.add(moveJLable);
		
		container_JPanel.add(jpanel_UserInformation);
		container_JPanel.add(jpanel_FriendsList);
		container_JPanel.add(jpanel_InformationManagement);
		
		
		this.setVisible(true);
	}


	/**
	 * 初始化信息管理JPanel
	 * @param jpanel_InformationManagement
	 */
	private void initInformationManagementJPanel(JPanel jpanel_InformationManagement) {
		
		jpanel_InformationManagement.setBounds(0, 588, _width, 40);
		jpanel_InformationManagement.setOpaque(false);
		jpanel_InformationManagement.setLayout(null);
		
		//添加好友按钮
		JButton jbutton_AddFriend = getSystemConfigButton(0, "addfriend");
//		jbutton_AddFriend.setText("addfriend");
		
		//查找好友按钮
		JButton jbutton_FindFriend = getSystemConfigButton(1, "findfriend");
//		jbutton_FindFriend.setText("findfriend");
		
		//显示个人信息按钮
		JButton jbutton_UserInfo = getSystemConfigButton(2, "userinfo");
//		jbutton_UserInfo.setText("userinfo");
		
		//配置个人信息按钮
		JButton jbutton_InfoConfig = getSystemConfigButton(3, "infoconfig");
		jbutton_InfoConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		jpanel_InformationManagement.add(jbutton_AddFriend);
		jpanel_InformationManagement.add(jbutton_FindFriend);
		jpanel_InformationManagement.add(jbutton_UserInfo);
		jpanel_InformationManagement.add(jbutton_InfoConfig);
		
	}

	/**
	 * 初始化好友列表
	 * @param jpanel_FriendsList
	 * @throws ConnectException 
	 */
	@SuppressWarnings("unchecked")
	private void initFriendsListJPanel(JPanel jpanel_FriendsList) throws ConnectException {
		
		jpanel_FriendsList.setBounds(0, 155, _width, 430);
		jpanel_FriendsList.setOpaque(false);
		
		jpanel_FriendsList.setLayout(null);
		
		//显示好友的JPanel
		JPanel jpanel_Friend = new JPanel();
		jpanel_Friend.setBounds(10, 10
				, jpanel_FriendsList.getWidth() - 20
				, jpanel_FriendsList.getHeight() - 10);
		jpanel_Friend.setLayout(new BorderLayout(0, 0));
		
		JScrollPane friendJScrollPane = new JScrollPane(); 
		
		FriendsListTree friendsListTree_RootNode = new FriendsListTree();
		
		FriendsListTree group_Myfrends = new FriendsListTree();
		group_Myfrends.set_groupText("myFriends");
		
		FriendsListTree group_Stranger = new FriendsListTree();
		group_Stranger.set_groupText("stranger");
		
		
		MessageModel getUserFriendInfoListModel = MessageManagement.getUserFriendInfoListMessageModel(_id.intValue(),"myFriends");
		
		LockModel lockModel = new LockModel(0, "getUserFriendInfoListModel request");
		
		try {
			MessageContext replyMessageContext = 
					TransmitTool.sendRequestMessageForNIOByBlock(getUserFriendInfoListModel, lockModel).getMessageContext();
			
			if (replyMessageContext != null) {
				List<UserFriendsInformation> userFriendsInformationList = 
						(ArrayList<UserFriendsInformation>) replyMessageContext.getObject();
				
				for (UserFriendsInformation userFriendsInformation : userFriendsInformationList) {
					
					Integer friendID = userFriendsInformation.getId().intValue();
					MessageModel imageMessageModel = 
							MessageManagement.getUserFriendImageMessageModel(friendID);
		
					lockModel = new LockModel(0, "getUserFriendImageMessageModel request");
					String imageKey = friendID + "";
					ImageIcon imageIcon =
							TransmitTool.sendImageRequestMessageForNIOByBlock(imageMessageModel, lockModel, imageKey);
					
					FriendsListTree friendsNode = new FriendsListTree();
					friendsNode.set_userFriendInfo(userFriendsInformation);
					friendsNode.set_userImageIcon(imageIcon);
					friendsListTreeMap.put(friendID, friendsNode);
				}
				
				setGroupList(group_Myfrends, friendsListTreeMap);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		GetRequest getUserFriendInfoListRequest = new GetRequest(getUserFriendInfoListModel) {
//			@Override
//			public void then() {
//				List<UserFriendsInformation> userFriendsInformationList = 
//						(ArrayList<UserFriendsInformation>) this.getReplyMessageContext().getObject();
//				
//				for (UserFriendsInformation userFriendsInformation : userFriendsInformationList) {
//					
//					Integer friendID = userFriendsInformation.getId().intValue();
//					MessageModel messageModel = 
//							MessageManagement.getUserFriendImageMessageModel(friendID);
//					
//					ImageRequest getUserFriendImageRequest = new ImageRequest(messageModel);
//					getUserFriendImageRequest.setImageRequestMapKey(friendID.toString());
//					getUserFriendImageRequest.sendRequest(true);
//					ImageIcon imageIcon = Receive.receiveImageMap.get(friendID.toString());
//					
//					FriendsListTree friendsNode = new FriendsListTree();
//					friendsNode.set_userFriendInfo(userFriendsInformation);
//					friendsNode.set_userImageIcon(imageIcon);
//					friendsListTreeMap.put(friendID, friendsNode);
//				}
//				setGroupList(group_Myfrends, friendsListTreeMap);
//			}
//		};
//		getUserFriendInfoListRequest.sendRequest(true).then();		
		
		group_Myfrends.srotByFriendState();
		friendsListTree_RootNode.add(group_Myfrends);
		
		friendsListTree_RootNode.add(group_Stranger);
		
		JTree groupListTreeRoot = new JTree(friendsListTree_RootNode);
		groupListTreeRoot.setRootVisible(false);
		groupListTreeRoot.putClientProperty("JTree.lineStyle"
				, "Horizontal");
		groupListTreeRoot.setToggleClickCount(1);
		groupListTreeRoot.getSelectionModel()
		.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		groupListTreeRoot.setCellRenderer(
				new FriendNodeRenderer(jpanel_Friend.getWidth()));
		//添加树中节点的鼠标双击事件
		groupListTreeRoot.addMouseListener(new FriendsListJTreeList());
		groupListTreeRoot.setUI(new BasicTreeUI() {

			@Override
			public void setLeftChildIndent(int newAmount) {
			}

			@Override
			public void setRightChildIndent(int newAmount) {
			}
			
		});
		friendJScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		friendJScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		friendJScrollPane.setViewportView(groupListTreeRoot);
		
		jpanel_Friend.add(friendJScrollPane, BorderLayout.CENTER);
		jpanel_FriendsList.add(jpanel_Friend);
	}

	/**
	 * 设置分组好友list
	 * @param group_Myfrends
	 * @param friendsListTreeMap
	 */
	private void setGroupList(FriendsListTree group_Myfrends, Map<Integer, FriendsListTree> friendsListTreeMap) {
		for (FriendsListTree friendsNode : friendsListTreeMap.values()) {
			group_Myfrends.add(friendsNode);
		}
	}
	
	/**
	 * 设置分组好友list
	 * @param group_Myfrends
	 * @param friendsListTreeMap
	 */
	private void setGroupListBySort(FriendsListTree group_Myfrends
			, Map<Integer, FriendsListTree> friendsListTreeMap
			, int index) {
		Iterator<FriendsListTree> iterable = friendsListTreeMap.values().iterator();
		while (iterable.hasNext()) {
			FriendsListTree friendsNode = iterable.next();
			if(friendsNode.get_userFriendInfo().getUserState().equals("0")) {				
				group_Myfrends.add(index++, friendsNode);
				iterable.remove();
			}
		}
		
		iterable = friendsListTreeMap.values().iterator();
		while (iterable.hasNext()) {
			FriendsListTree friendsNode = iterable.next();
			group_Myfrends.add(index++, friendsNode);
			iterable.remove();
		}
		
//		for (FriendsListTree friendsNode : friendsListTreeMap.values()) {
//			group_Myfrends.add(friendsNode);
//		}
	}

	/**
	 * 递归设置分组中的好友列表
	 * @param getUserFriendInfoRequestList
	 * @param getUserFriendImageList 
	 * @param friendsListTreeMap 
	 */
	@SuppressWarnings("unused")
	private void setFriendsGroupListTree(List<GetRequest> getUserFriendInfoRequestList
			, List<Integer> getUserFriendImageList, Map<Integer, FriendsListTree> friendsListTreeMap
			,long currentTime) {
		
		Iterator<GetRequest> requestIteror = getUserFriendInfoRequestList.iterator();
		while(requestIteror.hasNext()) {
			GetRequest getRequest = requestIteror.next();
			
			if (!getRequest.isWait && getRequest.hasRepley && 
					getRequest.getReplyMessageContext() != null) {
				
				UserFriendsInformation userFriendInformation = 
						(UserFriendsInformation) getRequest.getReplyMessageContext().getObject();
				
				FriendsListTree friendsNode = new FriendsListTree();
				
				friendsNode.set_userFriendInfo(userFriendInformation);
				friendsListTreeMap.put(userFriendInformation.getId().intValue(), friendsNode);
				getUserFriendImageList.add(userFriendInformation.getId().intValue());
//				group.add(friendsNode);
				
				requestIteror.remove();
			}
			
		}
		
	}

	
	@SuppressWarnings("unused")
	private void setFriendsGroupListImageTree(List<Integer> getUserFriendImageList
			, Map<Integer, FriendsListTree> friendsListTreeMap) {
		
		Iterator<Map.Entry<Integer, FriendsListTree>> entries = friendsListTreeMap.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<Integer, FriendsListTree> friendsList = entries.next();
			Integer receiveImageMapKey = friendsList.getKey();
			ImageIcon imageIcon = Receive.receiveImageMap.get(receiveImageMapKey.toString());
			if (!ObjectTool.isNull(imageIcon)) {
				friendsList.getValue().set_userImageIcon(imageIcon);
				getUserFriendImageList.remove(receiveImageMapKey);
			}
		}
		
//		if(!getUserFriendImageList.isEmpty()) 
//			setFriendsGroupListImageTree(getUserFriendImageList, friendsListTreeMap);
		
	}
	

	/**
	 * 初始化用于显示用户信息的JPanel
	 * @throws  
	 */
	private void initUserInformationJPanel(JPanel jpanel_UserInformation) {

		jpanel_UserInformation.setBounds(0, 30, _width, 155);
		jpanel_UserInformation.setLayout(null);
		jpanel_UserInformation.setOpaque(false);
//		jpanel_UserInformation.setBackground(new Color(255, 52, 179));

		JPanel jpanel_UserImage = getJpanelImage(this._iconBytes);
		jpanel_UserImage.setBounds(10, 10, 80, 80);
		jpanel_UserImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new UpdateInformationWindow();
			}
		});
		
		//用户昵称
		Label nickNameLabel = new Label("nickName:  " + this._nickName);
		nickNameLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		//用户状态
		Label stateLabel = new Label("state:  " + this._loginState);
		stateLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 16 + nickNameLabel.getY() + nickNameLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		//性别
		Label genderLabel = new Label("gender:  " + this._gender);
		genderLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 16 + stateLabel.getY() + stateLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		//个性签名
		Label personLabel = new Label("personality label:  " + this._personLabel);
		personLabel.setBounds(jpanel_UserImage.getX()
				, 10 + jpanel_UserImage.getY() + jpanel_UserImage.getHeight()
				, jpanel_UserInformation.getWidth() - 20
				, 16);
				
		//分割线
		Label label_Division = getDIvision();
//		int label_Division_X = jpanel_UserImage.getX();
		int label_Division_Y = personLabel.getHeight() 
				+ personLabel.getY() + 5;
		label_Division.setBounds(0, label_Division_Y
				, jpanel_UserInformation.getWidth()
				, 3);
		
		jpanel_UserInformation.add(jpanel_UserImage);
		jpanel_UserInformation.add(nickNameLabel);
		jpanel_UserInformation.add(stateLabel);
		jpanel_UserInformation.add(genderLabel);
		jpanel_UserInformation.add(personLabel);
		jpanel_UserInformation.add(label_Division);
		
	}


	/**
	 * 获得显示为分割线的label
	 * @return 显示为分割线的label
	 */
	public Label getDIvision() {
		Label divisionLabel = new Label();
		StringBuffer divisionText = new StringBuffer();
		for (int i = 0; i < 50; i++) {
			divisionText.append(" I ");
		}
		divisionLabel.setText(divisionText.toString());
		return divisionLabel;
	}
	
	/**
	 * 获得设置系统配置的JButton
	 * @param index 自身所在位置下标
	 * @return
	 */
	protected JButton getSystemConfigButton(int index, String desc) {
		JButton jButtons_SystemConfig = new JButton();
		int jbutton_SystemConfig_Width = (_width - (10 * (4 + 1))) / 4;//数量是4，间距是10
//		int jbutton_SystemConfig_Y = 40 / 2 - 30 / 2;
		jButtons_SystemConfig.setBounds(
				10 * (index + 1) + jbutton_SystemConfig_Width * index
				, 5, jbutton_SystemConfig_Width, 30);
		
		jButtons_SystemConfig.setText(desc);
		jButtons_SystemConfig.setToolTipText(desc);
		jButtons_SystemConfig.setFocusPainted(false);//焦点框
		jButtons_SystemConfig.setIconTextGap(0);//图片文字间隔量设置为0
		jButtons_SystemConfig.setMargin(new Insets(0, 0, 0, 0));
		
		return jButtons_SystemConfig;
	}

	/**
	 * 获得设置系统配置的JButton
	 * @param index 自身所在位置下标
	 * @return
	 */
	protected JButton getSystemConfigButton(int index) {
		JButton jButtons_SystemConfig = new JButton();
		int jbutton_SystemConfig_Width = (_width - (10 * (4 + 1))) / 4;//数量是4，间距是10
//		int jbutton_SystemConfig_Y = 40 / 2 - 30 / 2;
		jButtons_SystemConfig.setBounds(
				10 * (index + 1) + jbutton_SystemConfig_Width * index
				, 5, jbutton_SystemConfig_Width, 30);
		
		jButtons_SystemConfig.setFocusPainted(false);//焦点框
		jButtons_SystemConfig.setIconTextGap(0);//图片文字间隔量设置为0
		jButtons_SystemConfig.setMargin(new Insets(0, 0, 0, 0));
		
		return jButtons_SystemConfig;
	}
	
	/**
	 * 获得设置系统配置的JButton
	 * @param number 父容器中设置的总个数
	 * @param index 自身所在位置下标
	 * @param spacing 水平间距
	 * @return
	 */
	protected JButton getSystemConfigButton(int number, int index, int spacing) {
		JButton jButtons_SystemConfig = new JButton();
		int jbutton_SystemConfig_Width = (_width - (spacing * (number + 1))) / number;
//		int jbutton_SystemConfig_Y = 40 / 2 - 30 / 2;
		jButtons_SystemConfig.setBounds(
				spacing * (index + 1) + jbutton_SystemConfig_Width * index
				, 5, jbutton_SystemConfig_Width, 30);
		
		return jButtons_SystemConfig;
	}
	
	
	/**
	 * 获得设置系统配置的JButton数组
	 * @param number 父容器中设置的总个数
	 * @param spacing 水平间距
	 * @return
	 */
	protected JButton[] getSystemConfigButton(int number, int spacing) {
		JButton[] jButtons_SystemConfigArray = new JButton[number];
		int jbutton_SystemConfig_Width = (_width - (spacing * (number + 1))) / number;
//		int jbutton_SystemConfig_Y = 40 / 2 - 30 / 2;
		for (int i = 0; i < jButtons_SystemConfigArray.length; i++) {
			jButtons_SystemConfigArray[i] = new JButton();
			jButtons_SystemConfigArray[i].setBounds(
					spacing * (i + 1) + jbutton_SystemConfig_Width * i
					, 5, jbutton_SystemConfig_Width, 30);
		}
		return jButtons_SystemConfigArray;
	}
	
	/**
	 * 获得设置系统配置的JButton, 所占位置为父容器的中间位置
	 * @param parentWidth 父容器的宽度
	 * @param parentHeight 父容器的高度
	 * @param buttonHeight 自身的高度
	 * @param number 父容器中设置的总个数
	 * @param index 自身所在位置下标
	 * @param spacing 水平间距
	 * @return
	 */
	protected JButton getSystemConfigButton(int parentWidth, int parentHeight, int buttonHeight, int number, int index, int spacing) {
		JButton jbutton_SystemConfig = new JButton();
		int jbutton_SystemConfig_Width = (parentWidth - (spacing * (number + 1))) / number;
		int jbutton_SystemConfig_Y = parentHeight / 2 - buttonHeight / 2;
		jbutton_SystemConfig.setBounds(spacing * (index + 1) + jbutton_SystemConfig_Width * index, jbutton_SystemConfig_Y, jbutton_SystemConfig_Width, buttonHeight);
		return jbutton_SystemConfig;
	}
	
	/**
	 * 初始化主窗体 , 设置相关信息
	 */
	private void initMainWindow() {
		initUserInformation();
//		System.out.println(",W:" + _screenWidht + ",h:" + _screenHeight);
		
		//参数为所占比例的分子
		this.set_width(240);
		this.set_height(850);
		this.set_x(960);
		this.set_y(40);
		
		this.setSize(_width, _height);
		this.setResizable(false);
		this.setLocation((int)_x, (int)_y);
		this.setLayout(null);
		this.setUndecorated(true);

		this.setJPanelBackGroundImage(
				"./resources/image/backGround_mainWindow-2.png");
//		System.out.println(",TW:" + this._width + ",Th:" + this._height);
	}
	

	/**
	 * singleton model, 创建主窗口对象
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public synchronized static MainWindow createMainWindow(User user, byte[] iconBytes){
		if (_mainWindow == null) {
			
			if (user.getUserName() == null || user.getUserName() == null) {
				return null;
			}
			_mainWindow = new MainWindow(user, iconBytes);
		}
		return _mainWindow;
	}
	
	public byte[] get_iconBytes() {
		return _iconBytes;
	}

	/**
	 * singleton model, 获得主窗口对象
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public synchronized static MainWindow createMainWindow(){
		return _mainWindow;
	}
	
	public static void set_mainWindow(MainWindow _mainWindow) {
		MainWindow._mainWindow = _mainWindow;
	}
}
