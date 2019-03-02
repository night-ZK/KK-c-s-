package frame.customjtree;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 分组节点类
 * 该类定义了分组节点的显示形式
 * @author zxk
 *
 */
public class GroupNodeRenderer extends DefaultTreeCellRenderer{

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		GroupListTree groupListTree = (GroupListTree)value;
		ImageIcon groupOpenIcon = new ImageIcon(groupListTree.get_groupOpenImage() + "");
		ImageIcon groupColseIcon = new ImageIcon(groupListTree.get_groupCloseImage() + "");
		
		setIcon(null);
		setOpenIcon(imageIconFilter(groupOpenIcon));
		setClosedIcon(imageIconFilter(groupColseIcon));
		setText(groupListTree.get_groupCharacterIcon() + " " + groupListTree.get_groupText());
		setSize(groupListTree.get_groupWidht(), groupListTree.get_groupHeigth());
		return this;
	}

	private ImageIcon imageIconFilter(ImageIcon groupIcon) {
		groupIcon.setImage(groupIcon.getImage()
				.getScaledInstance(5, 5, Image.SCALE_REPLICATE));
		return groupIcon;
	}
	
	
}
