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
 * 好友节点类
 * 该类定义了好友节点的显示形式
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
		
		//使用参数value获得FriendsListTree节点对象
		FriendsListTree friendsListTree = (FriendsListTree)value;
		//非根节点与其子节点
		if (leaf && friendsListTree.getParent() 
				!= tree.getModel().getRoot()) {
			
			//从FriendsListTree节点中获得icon
			ImageIcon friendIcon = friendsListTree.get_userImageIcon();
			//设置图片缩放比例
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
				//属于可展开的节点
				//设置最佳位置
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
