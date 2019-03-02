package frame.customjtree;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

/**
 * 好友节点类
 * 该类定义了好友节点中包含的组件
 * @author zxk
 *
 */
public class FriendsListTree implements TreeNode{
	
	protected String _nickname;
	protected String _state;
	protected ImageIcon _imageIcon;
	protected String _personLabel;
	protected String _userAccount;
	private String _groupText;
	private ImageIcon _groupOpenImage;
	private ImageIcon _groupCloseImage;
	
	protected ArrayList<TreeNode> children = null;
	protected TreeNode parent;
	
	public FriendsListTree() {
		super();
	}

	public FriendsListTree(String userName, String state) {
		super();
		this._nickname = userName;
		this._state = state;
	}
	
	public FriendsListTree(String _nickname, String _state, ImageIcon _imageIcon, String _personLabel,
			String _userAccount) {
		super();
		this._nickname = _nickname;
		this._state = _state;
		this._imageIcon = _imageIcon;
		this._personLabel = _personLabel;
		this._userAccount = _userAccount;
	}

	
	public String get_groupText() {
		return _groupText;
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

	public String get_nickname() {
		return _nickname;
	}

	public void set_nickname(String _nickname) {
		this._nickname = _nickname;
	}

	public String get_state() {
		return _state;
	}

	public void set_state(String _state) {
		this._state = _state;
	}

	public ImageIcon get_imageIcon() {
		return _imageIcon;
	}

	public void set_imageIcon(ImageIcon _imageIcon) {
		this._imageIcon = _imageIcon;
	}

	public String get_personLabel() {
		return _personLabel;
	}

	public void set_personLabel(String _personLabel) {
		this._personLabel = _personLabel;
	}

	public String get_userAccount() {
		return _userAccount;
	}

	public void set_userAccount(String _userAccount) {
		this._userAccount = _userAccount;
	}

	public void add(FriendsListTree friendsNode) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(friendsNode);
		//
		friendsNode.parent = this;
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
