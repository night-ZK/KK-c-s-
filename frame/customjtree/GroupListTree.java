package frame.customjtree;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 分组节点类
 * 该类定义了分组节点的显示形式
 * @author zxk
 *
 */
public class GroupListTree extends DefaultMutableTreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon _groupImage;
	private String _groupText;
	private String _groupCharacterIcon;
	private int _groupWidht;
	private int _groupHeigth;
	private ImageIcon _groupOpenImage;
	private ImageIcon _groupCloseImage;
	public GroupListTree() {
	}
	public GroupListTree(ImageIcon _groupImage, String _groupText, String _groupCharacterIcon) {
		this._groupImage = _groupImage;
		this._groupText = _groupText;
		this._groupCharacterIcon = _groupCharacterIcon;
	}
	public GroupListTree(ImageIcon _groupImage, String _groupText, String _groupCharacterIcon, int _groupWidht,
			int _groupHeigth) {
		super();
		this._groupImage = _groupImage;
		this._groupText = _groupText;
		this._groupCharacterIcon = _groupCharacterIcon;
		this._groupWidht = _groupWidht;
		this._groupHeigth = _groupHeigth;
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
	public int get_groupWidht() {
		return _groupWidht;
	}
	public void set_groupWidht(int _groupWidht) {
		this._groupWidht = _groupWidht;
	}
	public int get_groupHeigth() {
		return _groupHeigth;
	}
	public void set_groupHeigth(int _groupHeigth) {
		this._groupHeigth = _groupHeigth;
	}
	public ImageIcon get_groupImage() {
		return _groupImage;
	}
	public void set_groupImage(ImageIcon _groupImage) {
		this._groupImage = _groupImage;
	}
	public String get_groupText() {
		return _groupText;
	}
	public void set_groupText(String _groupText) {
		this._groupText = _groupText;
	}
	public String get_groupCharacterIcon() {
		return _groupCharacterIcon;
	}
	public void set_groupCharacterIcon(String _groupCharacterIcon) {
		this._groupCharacterIcon = _groupCharacterIcon;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
