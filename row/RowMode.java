package row;

import java.sql.ResultSet;

public interface RowMode {
	/**
	 * 获得一个表对象中所有字段, 将它们存放在一个list中
	 * 并返回为String类型的数组
	 * @return 表对象中的所有字段
	 */
	public String[] getColumnPathArray();
	/**
	 * 参数为表对象中的字段名, 获得该字段的数据类型, 并返回
	 * @param fieldName 字段名 
	 * @return 字段的类型
	 */
	public String getDataType(String fieldName);
	/**
	 * 利用反射将数据通过set方法设置到表对象的相应字段中
	 * @param columnName 表对象的字段名
	 * @param value 从数据库中查出来的数据
	 */
	public void setValue(String column, Object value);
	/**
	 * 将表中一条取出的数据设置到当前表对象中
	 * @param result 从表中取得的一条数据
	 * @param row 
	 */
	public void setRow(ResultSet result);
}
