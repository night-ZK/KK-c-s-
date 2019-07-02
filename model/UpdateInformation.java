package model;

import message.MessageInterface;
import tablebeans.User;

public class UpdateInformation implements MessageInterface{

	private static final long serialVersionUID = 8L;

	private byte[] iconBytes;

	private User userInfo;

	public UpdateInformation(byte[] iconBytes, User userInfo) {
		this.iconBytes = iconBytes;
		this.userInfo = userInfo;
	}

	public byte[] getIconBytes() {
		return iconBytes;
	}

	public void setIconBytes(byte[] iconBytes) {
		this.iconBytes = iconBytes;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
}
