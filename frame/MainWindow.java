package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Label;
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
import frame.customjtree.FriendNodeRenderer;
import frame.customjtree.FriendsListTree;
import listener.FriendsListJTreeList;
import message.MessageModel;
import tablebeans.User;
import tablejson.UserFriendsInformation;
import tools.ObjectTool;
import transmit.GetRequest;
import transmit.MessageManagement;
import transmit.Receive;

public class MainWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	private static MainWindow _mainWindow = null;

	private MainWindow(User user){
		super(user);
		initMainWindow();
		
		JPanel container_JPanel = (JPanel)this.getContentPane();
		container_JPanel.setOpaque(false);
		container_JPanel.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 2, true));
		
		//�رհ�ť
		JButton jButton = getCloseJButton();
	
		//���ƶ��¼��ĵ�JLable
		JLabel moveJLable = getMoveJLabel(jButton.getWidth());

		//��ӭJLable
		JLabel jLabel = new JLabel("welcome to use this software..");
		jLabel.setBounds(10, 10, 200, 15);
		
		//������ʾ�û���Ϣ��JPanel
		JPanel jpanel_UserInformation = new JPanel(); 		
		//��ʼ���û���ϢJPanel
		initUserInformationJPanel(jpanel_UserInformation);
		
		//������ʾ��������JPanel
		JPanel jpanel_FriendsList = new JPanel();
		
		//��ʼ�������б�
		try {
			initFriendsListJPanel(jpanel_FriendsList);
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		
		//������ʾ����ϵͳ��Ϣ, �û���Ϣ��JPanel
		JPanel jpanel_InformationManagement = new JPanel();
		//��ʼ����Ϣ�������
		initInformationManagementJPanel(jpanel_InformationManagement);
		
		container_JPanel.add(jButton);
		container_JPanel.add(jLabel);
		container_JPanel.add(moveJLable);
		
		container_JPanel.add(jpanel_UserInformation);
		container_JPanel.add(jpanel_FriendsList);
		container_JPanel.add(jpanel_InformationManagement);
		
		
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ����Ϣ����JPanel
	 * @param jpanel_InformationManagement
	 */
	private void initInformationManagementJPanel(JPanel jpanel_InformationManagement) {
		
		jpanel_InformationManagement.setBounds(0, 588, _width, 40);
		jpanel_InformationManagement.setOpaque(false);
		jpanel_InformationManagement.setLayout(null);
		
		//��Ӻ��Ѱ�ť
		JButton jbutton_AddFriend = getSystemConfigButton(0);
		jbutton_AddFriend.setText("addfriend");
		
		//���Һ��Ѱ�ť
		JButton jbutton_FindFriend = getSystemConfigButton(1);
		jbutton_FindFriend.setText("findfriend");
		
		//��ʾ������Ϣ��ť
		JButton jbutton_UserInfo = getSystemConfigButton(2);
		jbutton_UserInfo.setText("userinfo");
		
		//���ø�����Ϣ��ť
		JButton jbutton_InfoConfig = getSystemConfigButton(3);
		jbutton_InfoConfig.setText("infoconfig");
		
		jpanel_InformationManagement.add(jbutton_AddFriend);
		jpanel_InformationManagement.add(jbutton_FindFriend);
		jpanel_InformationManagement.add(jbutton_UserInfo);
		jpanel_InformationManagement.add(jbutton_InfoConfig);
		
	}

	/**
	 * ��ʼ�������б�
	 * @param jpanel_FriendsList
	 * @throws ConnectException 
	 */
	@SuppressWarnings("unchecked")
	private void initFriendsListJPanel(JPanel jpanel_FriendsList) throws ConnectException {
		
		jpanel_FriendsList.setBounds(0, 155, _width, 430);
		jpanel_FriendsList.setOpaque(false);
		
		jpanel_FriendsList.setLayout(null);
		
		//��ʾ���ѵ�JPanel
		JPanel jpanel_Friend = new JPanel();
		jpanel_Friend.setBounds(10, 10
				, jpanel_FriendsList.getWidth() - 20
				, jpanel_FriendsList.getHeight() - 10);
		jpanel_Friend.setLayout(new BorderLayout(0, 0));
		
		JScrollPane friendJScrollPane = new JScrollPane(); 
		
		FriendsListTree friendsListTree_RootNode = new FriendsListTree();
		
		FriendsListTree group_Myfrends = new FriendsListTree();
		group_Myfrends.set_groupText("myFriends");
		
		FriendsListTree group_Stranger = new FriendsListTree();;
		group_Stranger.set_groupText("stranger");
		
		List<Integer> friendsIDList = new ArrayList<Integer>();
		
		MessageModel messageModel = MessageManagement.getFrindIDMessageModel(_id.intValue());
		
		GetRequest getFrindIDRequest = new GetRequest(messageModel);
		getFrindIDRequest.sendRequest(true);
		
//		Object getFrindIDResultObject = getFrindIDRequest.sendRequest();
		
		friendsIDList = (ArrayList<Integer>) getFrindIDRequest.getReplyMessageContext().getObject();
		
		//��ú��ѻ�����Ϣ
		List<GetRequest> getUserFriendInfoRequestList = new ArrayList<>();
		
		//��ú���ͷ��
		List<Integer> getUserFriendImageList = new ArrayList<>();
		//TODO change to count
		for (Integer friendID : friendsIDList) {
			
			messageModel = MessageManagement.getUserFriendInfoMessageModel(friendID);
			GetRequest getUserFriendInfoRequest = new GetRequest(messageModel);
			getUserFriendInfoRequest.sendRequest(false);
			
//			Object getUserFriendInfoObject = getUserFriendInfoRequest.sendRequest();
			getUserFriendInfoRequestList.add(getUserFriendInfoRequest);

			messageModel = MessageManagement.getUserFriendImageMessageModel(friendID);
			GetRequest getUserFriendImageRequest = new GetRequest(messageModel);
			getUserFriendImageRequest.sendRequest(false);
			
			
		}
		Map<Integer, FriendsListTree> friendsListTreeMap = new HashMap<>();
		//�ݹ����÷����еĺ����б�
		setFriendsGroupListTree(getUserFriendInfoRequestList, getUserFriendImageList, friendsListTreeMap);
		setFriendsGroupListImageTree(getUserFriendImageList, friendsListTreeMap);
		
		setGroupList(group_Myfrends, friendsListTreeMap);
		
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
		//������нڵ�����˫���¼�
		groupListTreeRoot.addMouseListener(new FriendsListJTreeList());
		groupListTreeRoot.setUI(new BasicTreeUI() {

			@Override
			public void setLeftChildIndent(int newAmount) {
//				super.setLeftChildIndent(newAmount);
			}

			@Override
			public void setRightChildIndent(int newAmount) {
//				super.setRightChildIndent(newAmount);
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
	 * ���÷������list
	 * @param group_Myfrends
	 * @param friendsListTreeMap
	 */
	private void setGroupList(FriendsListTree group_Myfrends, Map<Integer, FriendsListTree> friendsListTreeMap) {
		for (FriendsListTree friendsNode : friendsListTreeMap.values()) {
			group_Myfrends.add(friendsNode);
		}
	}

	/**
	 * �ݹ����÷����еĺ����б�
	 * @param getUserFriendInfiRequestList
	 * @param getUserFriendImageList 
	 * @param friendsListTreeMap 
	 */
	private void setFriendsGroupListTree(List<GetRequest> getUserFriendInfiRequestList
			, List<Integer> getUserFriendImageList, Map<Integer, FriendsListTree> friendsListTreeMap) {
		
		for (GetRequest getRequest : getUserFriendInfiRequestList) {
			if (getRequest.getReplyMessageContext() != null) {
				
				UserFriendsInformation userFriendInformation = 
						(UserFriendsInformation) getRequest.getReplyMessageContext().getObject();
				
				FriendsListTree friendsNode = new FriendsListTree();
				
				friendsNode.set_userFriendInfo(userFriendInformation);
				friendsListTreeMap.put(userFriendInformation.getId().intValue(), friendsNode);
				getUserFriendImageList.add(userFriendInformation.getId().intValue());
//				group.add(friendsNode);
				
				getUserFriendInfiRequestList.remove(getRequest);
			}
		}
		
		if(!getUserFriendInfiRequestList.isEmpty()) setFriendsGroupListTree(getUserFriendInfiRequestList
				,getUserFriendImageList, friendsListTreeMap);
	}

	
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
		
		if(!getUserFriendImageList.isEmpty()) setFriendsGroupListImageTree(getUserFriendImageList, friendsListTreeMap);
		
//		for (GetRequest getRequest : getUserFriendImageRequestList) {
//			if (getRequest.getReplyMessageContext() != null) {
//				
//				UserFriendsInformation userFriendInformation = 
//						(UserFriendsInformation) getRequest.getReplyMessageContext().getObject();
//				
//				FriendsListTree friendsNode = new FriendsListTree();
//				
//				friendsNode.set_userFriendInfo(userFriendInformation);
//				
//				friendsListTreeMap.add(friendsNode);
//				
//				getUserFriendInfiRequestList.remove(getRequest);
//			}
//		}
		
//		if(!getUserFriendInfiRequestList.isEmpty()) setFriendsGroupListTree(getUserFriendInfiRequestList, friendsListTreeMap);
	}
	

	/**
	 * ��ʼ��������ʾ�û���Ϣ��JPanel
	 */
	private void initUserInformationJPanel(JPanel jpanel_UserInformation) {

		jpanel_UserInformation.setBounds(0, 30, _width, 155);
		jpanel_UserInformation.setLayout(null);
		jpanel_UserInformation.setOpaque(false);
//		jpanel_UserInformation.setBackground(new Color(255, 52, 179));

		JPanel jpanel_UserImage = getJpanelImage(_path);
		jpanel_UserImage.setBounds(10, 10, 80, 80);
		
		//�û��ǳ�
		Label nickNameLabel = new Label("nickName:  " + this._nickName);
		nickNameLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 10, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		//�û�״̬
		Label stateLabel = new Label("state:  " + this._loginState);
		stateLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 16 + nickNameLabel.getY() + nickNameLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		//�Ա�
		Label genderLabel = new Label("gender:  " + this._gender);
		genderLabel.setBounds(20 + jpanel_UserImage.getWidth()
				, 16 + stateLabel.getY() + stateLabel.getHeight()
				, jpanel_UserInformation.getWidth() - jpanel_UserImage.getWidth() - 30
				, 16);
		
		//����ǩ��
		Label personLabel = new Label("personality label:  " + this._personLabel);
		personLabel.setBounds(jpanel_UserImage.getX()
				, 10 + jpanel_UserImage.getY() + jpanel_UserImage.getHeight()
				, jpanel_UserInformation.getWidth() - 20
				, 16);
				
		//�ָ���
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
	 * �����ʾΪ�ָ��ߵ�label
	 * @return ��ʾΪ�ָ��ߵ�label
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
	 * �������ϵͳ���õ�JButton
	 * @param index ��������λ���±�
	 * @return
	 */
	protected JButton getSystemConfigButton(int index) {
		JButton jButtons_SystemConfig = new JButton();
		int jbutton_SystemConfig_Width = (_width - (10 * (4 + 1))) / 4;//������4�������10
//		int jbutton_SystemConfig_Y = 40 / 2 - 30 / 2;
		jButtons_SystemConfig.setBounds(
				10 * (index + 1) + jbutton_SystemConfig_Width * index
				, 5, jbutton_SystemConfig_Width, 30);
		
		return jButtons_SystemConfig;
	}
	
	/**
	 * �������ϵͳ���õ�JButton
	 * @param number �����������õ��ܸ���
	 * @param index ��������λ���±�
	 * @param spacing ˮƽ���
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
	 * �������ϵͳ���õ�JButton����
	 * @param number �����������õ��ܸ���
	 * @param spacing ˮƽ���
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
	 * �������ϵͳ���õ�JButton, ��ռλ��Ϊ���������м�λ��
	 * @param parentWidth �������Ŀ��
	 * @param parentHeight �������ĸ߶�
	 * @param buttonHeight ����ĸ߶�
	 * @param number �����������õ��ܸ���
	 * @param index ��������λ���±�
	 * @param spacing ˮƽ���
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
	 * ��ʼ�������� , ���������Ϣ
	 */
	private void initMainWindow() {
		initUserInformation();
		System.out.println(",W:" + _screenWidht + ",h:" + _screenHeight);
		
		//����Ϊ��ռ�����ķ���
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
		System.out.println(",TW:" + this._width + ",Th:" + this._height);
	}
	
	
	/**
	 * ��ʼ���û������Ϣ
	 */
	private void initUserInformation() {
		
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
				break;
		}
		
		//��ʼ���û��Ա�
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
		
		this._id = user.getId();
		this._nickName = user.getUserNick();
		this._path = user.getUserImagepath();
		this._personLabel = user.getPersonLabel();
		this._findSum = user.getFiendSum();
	}

	/**
	 * singleton model, ���������ڶ���
	 * @param username �û���
	 * @param password ����
	 * @return
	 */
	public synchronized static MainWindow createMainWindow(User user){
		if (_mainWindow == null) {
			
			if (user.getUserName() == null || user.getUserName() == null) {
				return null;
			}
			_mainWindow = new MainWindow(user);
		}
		return _mainWindow;
	}
	
	public static void main(String[] args) {
//		MainWindow.createMainWindow(EvenProcess.login("zxk", "zk001"));
	}
}
