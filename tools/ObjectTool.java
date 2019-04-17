package tools;

import java.lang.reflect.Field;

import message.MessageHead;

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
				if(field.get(object) != null 
						&& !field.getName().equals("serialVersionUID")
						&& field.getType().isAssignableFrom(Boolean.class)) {
					System.out.println("field.get(object): " + field.get(object)+ ", field.getName():" + field.getName());
					isEmpty = false;
					return isEmpty;
				}
			}
			
			for (Field field : supperFiedls) {
				field.setAccessible(true);
				if(field.get(object) != null
						&& !field.getName().equals("serialVersionUID")
						&& field.getType().isAssignableFrom(Boolean.class)) {
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
	
	public static boolean isNull(Object object) {
		return object == null || object.toString().equals("");
	}
	
	public static boolean isRequestHeadDoable(MessageHead messageHead) {
		
		return !isNull(messageHead.getType()) 
				&& !isNull(messageHead.getRequestDataType())
				&& !isNull(messageHead.getRequestDescribe())
				&& !isNull(messageHead.getRequestTime());
		
	}
}
