package parsefile;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXML {
	
	private static ParseXML parseXML = new ParseXML();
	
	private ParseXML() {
		
	}
	
	public static ParseXML createParseXML() {
		return parseXML;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 获得DBcontion information
	 * @param dbID
	 * @return
	 */
	public Element getDBXMLElement(String dbID) {
		SAXReader reader = new SAXReader();
		try {
			File dbXML = new File("./resources/db.xml");
			Document document = reader.read(dbXML);
			Element root = document.getRootElement();
			List<Element> dbElements = root.elements("db-connection");
			for (Element dbelement : dbElements) {
				
				if (dbelement.attributeValue("id").equals(dbID)) {
					return dbelement;
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得socketserver连接信息
	 * @param serverID
	 * @return
	 */
	public Element getServerXMLElement(String serverID) {
		SAXReader reader = new SAXReader();
		try {
			File dbXML = new File("./resources/server-information.xml");
			Document document = reader.read(dbXML);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> dbElements = root.elements("server");
			for (Element dbelement : dbElements) {
				
				if (dbelement.attributeValue("id").equals(serverID)) {
					return dbelement;
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 获得需要解析的XML文件root节点下的所需节点list
	 * @param parseFile 解析的XML文件 
	 * @param childNodeName 所需节点名
	 * @return 包含该文件中root节点下的所有所需节点的list
	 */
	public List<Element> getXMLElementList(File parseFile, String childNodeName) {
		SAXReader reader = new SAXReader();
		try {
			File file_XML = parseFile;
			Document document = reader.read(file_XML);
			Element root = document.getRootElement();
			return root.elements(childNodeName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url = new ParseXML().getDBXMLElement("001").elementText("url");
		System.out.println(url);
		
		String address = new ParseXML().getServerXMLElement("001").elementText("port");
		System.out.println(address);
		
	}
}
