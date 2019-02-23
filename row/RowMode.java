package row;

public interface RowMode {
	public String[] getColumnPathArray();
	public String getDataType(String fieldName);
	
	public void setValue(String column, Object value);
}
