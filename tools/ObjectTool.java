package tools;

import java.lang.reflect.Field;

public class ObjectTool {
	/**
	 * 检测对象是否为空
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		Class<?> cla = object.getClass();
		Field[] fields = cla.getDeclaredFields();
		Field[] supperFiedls = cla.getSuperclass().getDeclaredFields();
		
		boolean isEmpty = true;
		
		try {
		
			for (Field field : fields) {
				field.setAccessible(true);
				if(field.get(object) != null) {
					isEmpty = false;
					return isEmpty;
				}
			}
			
			for (Field field : supperFiedls) {
				field.setAccessible(true);
				if(field.get(object) != null) {
					isEmpty = false;
					return isEmpty;
				}
			}
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return isEmpty;
	}
	
	/**
	 * 检测对象是否设置完全
	 * @return
	 */
	public static boolean isDoable(Object object) {
		Class<?> cla = object.getClass();
		Field[] fields = cla.getDeclaredFields();
		
		boolean isDoable = true;
		
		try {
		
			for (Field field : fields) {
				field.setAccessible(true);
				if(field.get(object) == null) {
					isDoable = false;
					return isDoable;
				}
			}
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return isDoable;
	}
}
