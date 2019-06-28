package model;

import message.MessageInterface;

public class UpdateInformation implements MessageInterface{

	private static final long serialVersionUID = 8L;

	private byte[] iconBytes;
	private String nickName;
	private String status;
	private int gener;
	private String personLable;
	
	private int userId;
	
	public UpdateInformation(byte[] iconBytes, String nickName, String status, int gener, String personLable, int userId) {
		super();
		this.iconBytes = iconBytes;
		this.nickName = nickName;
		this.status = status;
		this.gener = gener;
		this.personLable = personLable;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte[] getIconBytes() {
		return iconBytes;
	}
	public void setIconBytes(byte[] iconBytes) {
		this.iconBytes = iconBytes;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getGener() {
		return gener;
	}
	public void setGener(int gener) {
		this.gener = gener;
	}
	public String getPersonLable() {
		return personLable;
	}
	public void setPersonLable(String personLable) {
		this.personLable = personLable;
	}
	
}
