package row;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class RowSupper implements RowMode{

	/**
	 * 获得一个表对象中所有字段, 将它们存放在一个list中
	 * 并返回为String类型的数组
	 * @return 表对象中的所有字段
	 */
	public String[] getColumnPathArray() {
		ArrayList<String> userColumnArrayList = new ArrayList<String>();
		String[] fields = getFields(this);
		for (String field : fields) {
			String[] f = field.split("=");
			userColumnArrayList.add(f[0]);
		}
		return (String[]) userColumnArrayList.toArray(new String[userColumnArrayList.size()]);
	}
	
	/**
	 * 参数为表对象中的字段名, 获得该字段的数据类型, 并返回
	 * @param fieldName 字段名 
	 * @return 字段的类型
	 */
	public String getDataType(String fieldName) {
		String[] fields = getFields(this);
		for (String field : fields) {
			String[] f = field.split("=");
			if (f[0].equalsIgnoreCase(fieldName)) {
				return f[1];
			}
		}
		return null;
	}
	
	/**
	 * 利用反射将数据通过set方法设置到表对象的相应字段中
	 * @param columnName 表对象的字段名
	 * @param value 从数据库中查出来的数据
	 */
	public void setValue(String columnName, Object value) {
		try {
			String[] columnArray = this.getColumnPathArray();
			Class<? extends RowSupper> reflectClass = this.getClass();
			for (String field : columnArray) {
				String needField = field.trim();
				String headF = needField.substring(0, 1).toUpperCase();
				String tailF = needField.substring(1);
				String methodField = headF + tailF;
				if (needField.equalsIgnoreCase(columnName)) {
					Field realityField = reflectClass.getDeclaredField(needField);
					realityField.setAccessible(true);
					Class<?> fieldClass = realityField.getType();
					Method method = reflectClass.getMethod("set"+methodField, fieldClass);
					method.invoke(this, fieldClass.cast(value));
					break;
				}
			}
		}catch (NoSuchMethodException | SecurityException e) {
			
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得表对象中的所有字段, 通过“,”拆分, 并返回一个String类型的数组
	 * @param row 表对象
	 * @return 字段数组
	 */
	public String[] getFields(RowMode row){
		String rowString = row.toString();
		String needRowString = rowString.substring(rowString.indexOf("[")+1, rowString.indexOf("]"));
		return needRowString.split(",");
	}
	
	/**
	 * 将表中一条取出的数据设置到当前表对象中
	 * @param result 从表中取得的一条数据
	 * @param row 
	 */
	@Override
	public void setRow(ResultSet result){
		ResultSetMetaData rsmd;
		try {
			rsmd = result.getMetaData();
			int cou = rsmd.getColumnCount();
			for (int i = 1; i <= cou; i++) {
				this.setValue(rsmd.getColumnName(i), result.getObject(i));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
