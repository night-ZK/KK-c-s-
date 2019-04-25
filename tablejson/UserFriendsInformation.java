package tablejson;

import java.awt.Image;

import javax.swing.ImageIcon;

import row.RowSupper;

public class UserFriendsInformation extends RowSupper implements JsonInterface{

	private static final long serialVersionUID = 1L;
	
	private Number id;
	private String userName;
	private String userNick;
	private String userState;
	private Number gender;
	private String personLabel;
	
	private ImageIcon userImage;

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public Number getGender() {
		return gender;
	}

	public void setGender(Number gender) {
		this.gender = gender;
	}

	public String getPersonLabel() {
		return personLabel;
	}

	public void setPersonLabel(String personLabel) {
		this.personLabel = personLabel;
	}

	public ImageIcon getUserImage() {
		return userImage;
	}

	public void setUserImage(ImageIcon userImage) {
		this.userImage = userImage;
	}

	@Override
	public String toString() {
		return "UserFriendsInformation [id=" + id + ", userName=" + userName + ", userNick=" + userNick + ", userState="
				+ userState + ", gender=" + gender + ", personLabel=" + personLabel + ", userImage=" + userImage + "]";
	}
}
