package tablebeans;

import row.RowSupper;


public class User extends RowSupper {
	private String userName;
	private String passWord;
	private String userNick;
	private String userImagepath;
	private String userState;
	private Number fiendSum;
	private Number gender;
	private String personLabel;
	
	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserImagepath() {
		return userImagepath;
	}

	public void setUserImagepath(String userImagepath) {
		this.userImagepath = userImagepath;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public Number getFiendSum() {
		return fiendSum;
	}

	public void setFiendSum(Number fiendSum) {
		this.fiendSum = fiendSum;
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

	
	public String toString() {
		return "User [userName=" + userName + ", passWord=" + passWord + ", userNick="
				+ userNick + ", userImagepath=" + userImagepath + ", userState="
				+ userState + ", fiendSum=" + fiendSum + ", gender=" + gender
				+ ", personLabel=" + personLabel + "]";
	}

	
}
