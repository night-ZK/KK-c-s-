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

public class EvenProcess {
	
	/**
	 * 处理登录业务
	 * @param userName 用户名
	 * @param passWord 密码
	 * @return
	 */
	public static boolean login(String userName, String passWord){
		Connection _conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			prepareStatement = _conn.prepareStatement("select * from user z where username = ? and password = ?");
			prepareStatement.setString(1, userName);
			prepareStatement.setString(2, passWord);
			result = prepareStatement.executeQuery();
			if (result.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			System.out.println("execute sql error..");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 通过userid从user表中获得用户信息
	 * @param userid 用户名ID
	 * @return
	 */
	public static User getUserInfo(int userID){
		User user = new User();
		Connection conn = ExeSql.createExeSql().getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet result = null;
		try {
			prepareStatement = conn.prepareStatement("select * from user z where z.id = ?");
			prepareStatement.setInt(1, userID);
			result = prepareStatement.executeQuery();
			while (result.next()) {
				setRow(result, user);
				
			}
		}catch (SQLException e) {
			System.out.println("get sql exception..");
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 通过username从user表中获得用户信息
	 * @param userName 用户名
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
				setRow(result, user);
				arrList.add(user);
			}
		}catch (SQLException e) {
			System.out.println("get UserImagePath exception..");
			e.printStackTrace();
		}
		return arrList;
	}
	
	/**
	 * 通过userID从friend表中获得用户的好友信息
	 * @param userID 用户ID
	 * @return 存放friend表信息的list
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
				//arraylistadd元素, 该元素需要时一个新的对象, 否则会产生数据紊乱
				Friend friend = new Friend();
				setRow(result, friend);
				arrList.add(friend);
			}
		}catch (SQLException e) {
//			throw new SQLException("sql exception..");
			e.printStackTrace();
		}
		return arrList;
	}
	
	/**
	 * 将表中一条取出的数据设置到一个表对象中
	 * @param result 从表中取得的一条数据
	 * @param row 
	 */
	protected static void setRow(ResultSet result, RowMode row){
		ResultSetMetaData rsmd;
		try {
			rsmd = result.getMetaData();
			int cou = rsmd.getColumnCount();
			for (int i = 1; i <= cou; i++) {
				row.setValue(rsmd.getColumnName(i), result.getObject(i));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
