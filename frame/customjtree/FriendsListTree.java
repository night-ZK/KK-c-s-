package frame.customjtree;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class FriendsListTree extends DefaultMutableTreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String _nickname;
	protected String _state;
	protected ImageIcon _imageIcon;
	protected String _personLabel;
	protected String _userAccount;
	
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
	
}
