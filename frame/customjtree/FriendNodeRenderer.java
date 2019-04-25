package frame.customjtree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import listener.FriendsListJTreeList;

/**
 * ���ѽڵ���
 * ���ඨ���˺��ѽڵ����ʾ��ʽ
 * @author zxk
 *
 */
public class FriendNodeRenderer extends JLabel implements TreeCellRenderer{

	private static final long serialVersionUID = 1L;

	private static ImageIcon _groupOpenImage;
	private static ImageIcon _groupCloseImage;
	
	private int _subparentWidth;
	static {
		_groupOpenImage = null;
		_groupCloseImage = null;
	}
	
	public FriendNodeRenderer(int _parentWidth) {
		super();
		this._subparentWidth = _parentWidth;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		
		//ʹ�ò���value���FriendsListTree�ڵ����
		FriendsListTree friendsListTree = (FriendsListTree)value;
		//�Ǹ��ڵ������ӽڵ�
		if (leaf && friendsListTree.getParent() 
				!= tree.getModel().getRoot()) {
			
			//��FriendsListTree�ڵ��л��icon
			ImageIcon friendIcon = friendsListTree.get_userImageIcon();
			//����ͼƬ���ű���
			friendIcon = imageIconFilter(friendIcon, 60, 60);
			String userNickAndpersonlabel = "";
			if (friendsListTree.get_userFriendInfo().getPersonLabel() != null) {
				
				userNickAndpersonlabel = "<html>" + friendsListTree.get_userFriendInfo().getUserNick() 
						+ "<br/>" + friendsListTree
							.get_userFriendInfo().getPersonLabel() + "<html/>";
			}else {
				
				userNickAndpersonlabel = friendsListTree.get_userFriendInfo()
						.getUserNick();
			}
			
			setIcon(friendIcon);
			
			if (!friendsListTree.get_userFriendInfo()
					.getUserState().equals("0")) {
				
				setEnabled(false);
			}
			
			setText(userNickAndpersonlabel);
			setIconTextGap(10);
			setPreferredSize(new Dimension(_subparentWidth - 20, 65));
		}else {
			if (expanded) {
				//���ڿ�չ���Ľڵ�
				//�������λ��
				setPreferredSize(new Dimension(_subparentWidth - 20, 20));
				setText("- " + friendsListTree.get_groupText());
				setIcon(_groupOpenImage);
			}else {
				setPreferredSize(new Dimension(_subparentWidth, 20));
				setText("+ " + friendsListTree.get_groupText());
				setIcon(_groupCloseImage);
			}
			setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 1));
			setIconTextGap(5);
		}
	
		return this;
	}
	
	private ImageIcon imageIconFilter(ImageIcon groupIcon, int imageWidth, int imageHeight) {
		groupIcon.setImage(groupIcon.getImage()
				.getScaledInstance(imageWidth, imageHeight, Image.SCALE_REPLICATE));
		return groupIcon;
	}
}
