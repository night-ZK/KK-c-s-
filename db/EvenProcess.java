package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import row.RowMode;
import tablebeans.Friend;
import tablebeans.User;
import tablejson.JsonInterface;
import tablejson.UserFriendsInformation;

public class EvenProcess {
	
	/**
	 * �����¼ҵ��
	 * @param userName �û���
	 * @param passWord ����
	 * @return
	 */
	public static User login(String userName, String passWord){
		Connection _conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		User user = new User();
		try {
			prepareStatement = _conn.prepareStatement("select * from user z where username = ? and password = ?");
			prepareStatement.setString(1, userName);
			prepareStatement.setString(2, passWord);
			result = prepareStatement.executeQuery();
			if (result.next()) {
				user.setRow(result);
			}
		}
		catch (SQLException e) {
			System.out.println("execute sql error..");
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * ͨ��userid��user���л���û���Ϣ
	 * @param userid �û���ID
	 * @return
	 */
	public static User getUserInfo(int userID){
		//TODO privilege management
		User user = new User();
		Connection conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			prepareStatement = conn.prepareStatement("select * from user z where z.id = ?");
			prepareStatement.setInt(1, userID);
			result = prepareStatement.executeQuery();
			while (result.next()) {
				user.setRow(result);
			}
		}catch (SQLException e) {
			System.out.println("get sql exception..");
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * ͨ��userID��õ�ǰ�û��ĺ�����Ϣ
	 * @param userID
	 * @return
	 */
	public static UserFriendsInformation getUser_FriendInfo(int userID){
		UserFriendsInformation userFriendsInformation = new UserFriendsInformation();
		Connection conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			prepareStatement = conn.prepareStatement("select * from user z where z.id = ?");
			prepareStatement.setInt(1, userID);
			result = prepareStatement.executeQuery();
			while (result.next()) {
				userFriendsInformation.setRow(result);
			}
		}catch (SQLException e) {
			System.out.println("get sql exception..");
			e.printStackTrace();
		}
		return userFriendsInformation;
	}
	
	/**
	 * ͨ��username��user���л���û���Ϣ
	 * @param userName �û���
	 * @return
	 */
	public static ArrayList<User> getUserInfo(String userName){
		ArrayList<User>  arrList = new ArrayList<User>();
		Connection conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		User user = new User();
		try {
			prepareStatement = conn.prepareStatement("select * from user z where z.username = ?");
			prepareStatement.setString(1, userName);
			result = prepareStatement.executeQuery();
			while (result.next()) {
				user.setRow(result);
				arrList.add(user);
			}
		}catch (SQLException e) {
			System.out.println("get UserImagePath exception..");
			e.printStackTrace();
		}
		return arrList;
	}
	
	/**
	 * ͨ��userID��friend���л���û��ĺ�����Ϣ
	 * @param userID �û�ID
	 * @return ���friend����Ϣ��list
	 */
	public static ArrayList<Friend> getFriendInfo(int userID){
		ArrayList<Friend> arrList = new ArrayList<Friend>();
		Connection conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			prepareStatement = conn.prepareStatement("select * from friend f where f.user_id = ?");
			prepareStatement.setInt(1, userID);
			result = prepareStatement.executeQuery();
			while (result.next()) {
				//arraylistaddԪ��, ��Ԫ����Ҫʱһ���µĶ���, ����������������
				Friend friend = new Friend();
				friend.setRow(result);
				arrList.add(friend);
			}
		}catch (SQLException e) {
//			throw new SQLException("sql exception..");
			e.printStackTrace();
		}
		return arrList;
	}

}
