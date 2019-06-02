package transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.dom4j.Element;

import parsefile.ParseXML;
import tools.ObjectTool;

public abstract class SocketClient implements Runnable{
	
	private static ParseXML parseXML;
	private static String serverID = "001";
	private static String host;
	private static int port;
	
	//µÈ´ý±êÖ¾
	public boolean isWait;
	public boolean hasRepley;
	
	private static Socket socket;
	
	protected OutputStream os;
	
	protected InputStream is;
	
	
	static {
		parseXML = ParseXML.createParseXML();
		Element element = parseXML.getServerXMLElement(serverID);
		host = element.elementText("host-address");
		port = Integer.parseInt(element.elementText("port"));
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected SocketClient() {
		try {
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Socket getSocket() {
		try {
			if (ObjectTool.isNull(socket) || socket.isClosed()) {
				socket = new Socket(host, port);
				System.out.println("a socket connect. this.socket is close: " + socket.isClosed());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	
}
