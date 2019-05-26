package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import customexception.RequestParameterExcetion;
import message.MessageHead;

public class TransmitTool {
	
	/**
	 * 获取requestMap的key值
	 * @param messageHead
	 * @return
	 */
	public static String getRequestMapKey(MessageHead messageHead) {
		
		Integer headType = messageHead.getType();
		Long sendTime = messageHead.getRequestTime();
		Integer requestNo = messageHead.getRequestNO();
		return "Type: " + headType + ", sendTime: " + sendTime
				+ ", requestNo: " + requestNo; 
		
	}
	
	/**
	 * 对象转化成byte[]
	 * @param o
	 * @return
	 * @throws IOException
	 */
	public static byte[] ObjectToByteArrays(Object o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		
		byte[] messageModelByteArrays = baos.toByteArray();
		
		if(!ObjectTool.isNull(oos)) oos.close();
		if(!ObjectTool.isNull(baos)) baos.close();
		
		return messageModelByteArrays;
	}
	
	/**
	 * 对象转化成byte[]
	 * @param o
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static Object byteArraysToObject(byte[] bs) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object o = ois.readObject();
		
		if(!ObjectTool.isNull(ois)) ois.close();
		if(!ObjectTool.isNull(bais)) bais.close();
		
		return o;
	}
	
	/**
	 * 对象转json
	 * @param ob
	 * @return
	 */
	public static String objectToJson(Object ob) {
		String json = "class: " + ob.getClass().getName() + ";" 
				+ ob.toString();
		return json;
	}
	
	/**
	 * json转对象
	 * @param ob
	 * @return
	 */
	public static Object jsonToObject(String json) {
		String[] rs = json.split(";");
		String className = rs[0].split(":")[1].trim();
		try {
			Class<?> cl = Class.forName(className);
			Object o = cl.newInstance();
			String[] fileds = getFields(rs[1]);		
			for(String filed : fileds) {
				String[] kv = filed.split("=");
				String needField = kv[0].trim();
				Field field = cl.getDeclaredField(needField);
				Class<?> fieldType = field.getType();
				field.setAccessible(true);
				field.set(o, fieldType.cast(kv[0]));
			}
			return o;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置对象属性
	 * @param o
	 * @param subJson
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static Object setObject(Class<?> cl, String subJson) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Object o = cl.newInstance();
		String[] fileds = getFields(subJson);		
		for(String filed : fileds) {
			String[] kv = filed.split("=");
			String needField = kv[0].trim();
			Field field = cl.getDeclaredField(needField);
			Class<?> fieldType = field.getType();
			field.setAccessible(true);
			field.set(o, fieldType.cast(kv[0]));
		}
		return o;
		
//		Map<String, String> filedMap = new HashMap<>();
		
//		for (Entry<String, String> fm : filedMap.entrySet()) {
//			String needField = fm.getKey();
//			Field field = cl.getDeclaredField(needField);
//			field.setAccessible(true);
//			field.set(o, fm.getValue());	
//		}		
	}

	/**
	 * 获得表对象中的所有字段, 通过“,”拆分, 并返回一个String类型的数组
	 * @param row 表对象
	 * @return 字段数组
	 */
	public static String[] getFields(String subJson){
		String needRowString = subJson.substring(subJson.indexOf("[")+1, subJson.indexOf("]"));
		return needRowString.split(",");
	}

	public static Map<String, String> analysisRequestParameters(String subRequest) throws RequestParameterExcetion {
		Map<String, String> parametersMap = new HashMap<>();
		String[] parameters = subRequest.split("&");
		
		//TODO 检测parameter中是否包含"时间戳-type:"(拆分用特殊字符&), 包含则替换相应字符
		
		for (String parameter : parameters) {
			String[] pArrays = parameter.split("=");
			
			if (pArrays.length != 2) 
				throw new RequestParameterExcetion("request parameter set error..");
			
			//TODO 检测parameter中是否包含"时间戳-type:"(拆分用特殊字符=), 包含则替换相应字符
			parametersMap.put(pArrays[0].trim(), pArrays[1]);
			
		}
		
		return parametersMap;
	}

	
}
