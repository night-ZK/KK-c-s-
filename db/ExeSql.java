package db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.dom4j.Element;

import parsefile.ParseXML;

public class ExeSql {
	private static Connection _conn = null;
	private static ExeSql _exeSql = null;
	private static String _dbID = "002";
	
	public static String get_dbID() {
		return _dbID;
	}

	public static void set_dbID(String dbID) {
		_dbID = dbID;
	}

	private ExeSql(){
	}
	static{
//		String sql1 = "{call wpa_context.SET_CONTEXT(cc_app_cntxt_key.CO_CD,'J0001')}";
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			String url = "jdbc:oracle:"+"thin:@localhost:1522:ORCL";
//			String user = "C##MCF_MAT0824";
//			String password = "C##MCF_MAT0824";
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/db_zxk?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
//			String user = "name";
//			String password = "zk";
			
			//读取db.xml获得dbElement
			Element dbElement = ParseXML.createParseXML().getDBXMLElement(_dbID);
			
			//通过dbElement动态连接数据库
			Class.forName(dbElement.elementText("driver"));
			_conn = DriverManager.getConnection(dbElement.elementText("url"), dbElement.elementText("name")	, dbElement.elementText("password"));
			
//			CallableStatement callableStatement = _conn.prepareCall(sql1);
//			callableStatement.execute();
		}
		catch (ClassNotFoundException e) {
			System.out.println("driver load failurd..");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("initialization connection failed..");
			e.printStackTrace();
		}
	}
	public Connection getConnection(){
		return _conn;
	}
	
	public static ExeSql createExeSql(){
		if (_exeSql == null) {
			_exeSql = new ExeSql();
		}
		return _exeSql;
	}
	
//	MCRow mcRow = MCRow.create();
//	KonyuYokyuInfoR ifRow = KonyuYokyuInfoR.create();
//	public MCList<?> exeSelect(String sql) {
//		
//		PreparedStatement preparedStatement = null;
//		ResultSet result = null;
//		MCList<KonyuYokyuInfoR> ifL = MCList.create(KonyuYokyuInfoR.class);
//		ResultSetMetaData rsmd;
//		try {
//			preparedStatement = _conn.prepareStatement(sql);
//			result = preparedStatement.executeQuery();
//			
//			rsmd = result.getMetaData();
//			int cou = rsmd.getColumnCount();
//			while (result.next()) {
//				result.getString(1);
//				for (int i = 1; i <= cou; i++) {
//					rsmd.getColumnTypeName(i);
//					rsmd.getColumnType(i);
//					rsmd.getColumnLabel(i);
//					rsmd.getPrecision(i);
//					for (String colu : ifRow.getColumnPathArray()) {
//						if (rsmd.getColumnName(i).equalsIgnoreCase(colu)) {
//							setMCRow(ifRow, colu, i, result);
//							break;
//						}
//					}
//				}
//				ifL.add(ifRow);
//			}
//			
//			return ifL;
//		}
//		catch (NullPointerException e){
//			return ifL;
//		}
//		catch (Exception e) {
//			System.out.println("fail");
//			System.out.println(e);
//			return ifL;
//		}finally{
//			try {
//				if (preparedStatement != null) preparedStatement.close();
//				if (_conn != null) _conn.close();
//				System.out.println("close!");
//			}
//			catch (Exception e) {
//				System.out.println("closeERROR!");
//				e.printStackTrace();
//			}
//		}
//	}
	
//	private void setMCRow(MCRow row, String colu, int i, ResultSet result) throws SQLException {
//		try {
//			switch (row.getDataType(colu)) {
//				case NUMBER:
//					row.setNumber(colu, MCNumber.valueOf(result.getString(i)));
//					break;
//					
//				case STRING:
//					row.setString(colu, result.getString(i));
//					break;
//					
//				case DATE:
//					try {
//						row.setDate(colu, new MCDateFormat().doInputParse(result.getString(i)));						
//					}
//					catch (MCException e) {
//						row.setDate(colu, MCDate.valueOf(result.getString(i), "yyyy-MM-dd"));
//					}
//					break;
//					
//				case BOOLEAN:
//					row.setBoolean(colu, Boolean.valueOf(result.getString(i)));
//					break;
//					
//				default:
//					break;
//			}
//		}
//		catch (SQLException e) {
//			throw e;
//		}
//	}
//	
//	public MCList<?> resolitionResult(ResultSet result){
//		MCList<KonyuYokyuInfoR> ifL = MCList.create(KonyuYokyuInfoR.class);
//		ResultSetMetaData rsmd;
//		try {
//			rsmd = result.getMetaData();
//			int cou = rsmd.getColumnCount();
//			while (result.next()) {
//				result.getString(1);
//				for (int i = 1; i <= cou; i++) {
//					rsmd.getColumnTypeName(i);
//					rsmd.getColumnType(i);
//					rsmd.getColumnLabel(i);
//					rsmd.getPrecision(i);
//					for (String colu : ifRow.getColumnPathArray()) {
//						if (rsmd.getColumnName(i).equalsIgnoreCase(colu)) {
//							ifRow.setString(colu, result.getString(i));
//						}
//					}
//				}
//				ifL.add(ifRow);
//			}
//			return ifL;
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//			return ifL;
//		}
//	}
}
