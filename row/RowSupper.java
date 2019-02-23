package row;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class RowSupper implements RowMode{

	public String[] getColumnPathArray() {
		ArrayList<String> userColumnArrayList = new ArrayList<String>();
		String[] fields = getFields(this);
		for (String field : fields) {
			String[] f = field.split("=");
			userColumnArrayList.add(f[0]);
		}
		return (String[]) userColumnArrayList.toArray(new String[userColumnArrayList.size()]);
	}
	
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

	public String[] getFields(RowMode row){
		String rowString = row.toString();
		String needRowString = rowString.substring(rowString.indexOf("[")+1, rowString.indexOf("]"));
		return needRowString.split(",");
	}
}
