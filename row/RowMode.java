package row;

import java.sql.ResultSet;

public interface RowMode {
	/**
	 * ���һ��������������ֶ�, �����Ǵ����һ��list��
	 * ������ΪString���͵�����
	 * @return ������е������ֶ�
	 */
	public String[] getColumnPathArray();
	/**
	 * ����Ϊ������е��ֶ���, ��ø��ֶε���������, ������
	 * @param fieldName �ֶ��� 
	 * @return �ֶε�����
	 */
	public String getDataType(String fieldName);
	/**
	 * ���÷��佫����ͨ��set�������õ���������Ӧ�ֶ���
	 * @param columnName �������ֶ���
	 * @param value �����ݿ��в����������
	 */
	public void setValue(String column, Object value);
	/**
	 * ������һ��ȡ�����������õ���ǰ�������
	 * @param result �ӱ���ȡ�õ�һ������
	 * @param row 
	 */
	public void setRow(ResultSet result);
}
