package frame.customComponent;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import tablejson.UserFriendsInformation;

/**
 * 好友节点类
 * 该类定义了好友节点中包含的组件
 * @author zxk
 *
 */
public class FriendsListTree implements TreeNode{
	
	private String _groupText;
	private ImageIcon _groupOpenImage;
	private ImageIcon _groupCloseImage;
	
	private ImageIcon _userImageIcon;
	private UserFriendsInformation _userFriendInfo;
	
	protected ArrayList<TreeNode> children = null;
	protected TreeNode parent;
	
	public FriendsListTree() {
		super();
	}

	public String get_groupText() {
		return _groupText;
	}

	public ImageIcon get_userImageIcon() {
		return _userImageIcon;
	}

	public void set_userImageIcon(ImageIcon _userImageIcon) {
		this._userImageIcon = _userImageIcon;
	}

	public UserFriendsInformation get_userFriendInfo() {
		return _userFriendInfo;
	}

	public void set_userFriendInfo(UserFriendsInformation _userFriendInfo) {
		this._userFriendInfo = _userFriendInfo;
	}

	public void set_groupText(String _groupText) {
		this._groupText = _groupText;
	}

	public ImageIcon get_groupOpenImage() {
		return _groupOpenImage;
	}

	public void set_groupOpenImage(ImageIcon _groupOpenImage) {
		this._groupOpenImage = _groupOpenImage;
	}

	public ImageIcon get_groupCloseImage() {
		return _groupCloseImage;
	}

	public void set_groupCloseImage(ImageIcon _groupCloseImage) {
		this._groupCloseImage = _groupCloseImage;
	}

	public void add(FriendsListTree friendsNode) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(friendsNode);
		//
		friendsNode.parent = this;
	}
	
	public void add(int index, FriendsListTree friendsNode) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(index, friendsNode);
		//
		friendsNode.parent = this;
	}
	
	public void clean() {
		children = null;
	}
	
	public boolean isroot() {
		return getParent() == null;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException(
					"the tree no children, please add children for tree..");
		}
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		if (children == null) {
			return -1;
		}
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * 获得当前节点下标
	 */
	@Override
	public int getIndex(TreeNode node) {
		if (node == null) {
			throw new IllegalArgumentException("argument is null..");
		}
		if (!isNodeChilder(node)) {
			return -1;
		}
		return children.indexOf(node);
	}
	
	public boolean srotRuleByState() {
		return this.get_userFriendInfo().getUserState().equals("0");
	}
	

	/**
	 * 判断参数是否属于子节点
	 * @param node
	 * @return
	 */
	private boolean isNodeChilder(TreeNode node) {
		if(node == null) {
			return false;
		}else {
			if (getChildCount() == 0) {
				return false;
			}else {
				return node.getParent() == this;
			}
		}
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return (getChildCount() == -1) && (getParent() != null);
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}
		
}
