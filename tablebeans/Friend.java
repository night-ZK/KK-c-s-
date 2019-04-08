package tablebeans;

import row.RowSupper;

public class Friend extends RowSupper implements BeansInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Number id;
	private Number user_id;
	private Number friend_id;
	private String user_group;
	private String friend_group;
	
	public Friend() {
	}

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public Number getUser_id() {
		return user_id;
	}

	public void setUser_id(Number user_id) {
		this.user_id = user_id;
	}

	public Number getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(Number friend_id) {
		this.friend_id = friend_id;
	}

	public String getUser_group() {
		return user_group;
	}

	public void setUser_group(String user_group) {
		this.user_group = user_group;
	}

	public String getFriend_group() {
		return friend_group;
	}

	public void setFriend_group(String friend_group) {
		this.friend_group = friend_group;
	}

	@Override
	public String toString() {
		return "Friend [id=" + id + ", user_id=" + user_id + ", friend_id=" + friend_id + ", user_group=" + user_group
				+ ", friend_group=" + friend_group + "]";
	}
	
}
