package frame.customjtree;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class FriendNodeRenderer extends DefaultTreeCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		//使用参数value获得FriendsListTree节点对象
		FriendsListTree friendsListTree = (FriendsListTree)value;
		//从FriendsListTree节点中获得icon
		ImageIcon friendIcon = new ImageIcon(friendsListTree.get_imageIcon() + "");
		//设置图片缩放比例
		friendIcon.setImage(friendIcon.getImage()
				.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		
		String userNickAndpersonlabel = "";
		if (friendsListTree.get_personLabel() != null) {
			userNickAndpersonlabel = "<html>" + friendsListTree.get_nickname() 
			+ "<br/>" + friendsListTree.get_personLabel() + "<html/>";
		}else {
			userNickAndpersonlabel = friendsListTree.get_nickname();
		}
		
		switch (friendsListTree.get_state()) {
		case "0":
			setIcon(friendIcon);
			break;
		
		case "1":
			//TODO 设置对比度
//			Imageicon outlogin = setContrast(friendIcon, 0.5f);
//			setIcon(outlogin);
			break;
		default:
			setIcon(null);
			break;
		}
		
		setText(userNickAndpersonlabel);

		return this;
	}
	
}
