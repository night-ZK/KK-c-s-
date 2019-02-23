package parsefile;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXML {
	@SuppressWarnings("unchecked")
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
	
	public static void main(String[] args) {
		String url = new ParseXML().getDBXMLElement("001").elementText("url");
		System.out.println(url);
	}
}
