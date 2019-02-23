package db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DEMO1 {
	protected static Connection _conn = null;
	protected static CallableStatement _callableStatement = null;
	protected static ResultSet _result = null;
	public static void main(String[] args) {
		String sql = "{call wpa_context.SET_CONTEXT(cc_app_cntxt_key.CO_CD,'3541')}";
		_conn = getConnection();
		try {
			_callableStatement = _conn.prepareCall(sql);
//			_callableStatement.setString(1, "TEST.DEMO01");
//			_callableStatement.registerOutParameter(1, Types.VARCHAR);
			_callableStatement.execute();
			System.out.println("success...");
			System.out.println("java.library.path:"+System.getProperty("java.library.path"));
			
		}
		catch (NullPointerException e){
			
		}
		catch (Exception e) {
			System.out.println("fail");
			System.out.println(e);
		}finally{
			try {
				if (_callableStatement != null) _callableStatement.close();
				if (_conn != null) _conn.close();
				System.out.println("close!");
			}
			catch (Exception e) {
				System.out.println("closeERROR!");
				e.printStackTrace();
			}
		}
//		SILZ_KonyuYokyuInfo.Ins01 ins01 = SILZ_KonyuYokyuInfo.crtIns01();
//		ins01.exec();
		
//		ExeSql exeSql = new ExeSql();
//		exeSql.exeSelect("select * from STZ_KONYU_SCK st where st.IRAI_KUBUN = '0'");
//		exeSql.resolitionResult(resu);
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:"+"thin:@192.168.130.113:1522:ORCL";
			String user = "C##MCF_MAT0824";
			String password = "C##MCF_MAT0824";
			return conn = DriverManager.getConnection(url, user, password);
		}
		catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			e.printStackTrace();
			return conn;
		}
		catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
			return conn;
		}
	}
}

