package transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.dom4j.Element;

import parsefile.ParseXML;
import threadmangagement.ThreadConsole;

public class SocketClient {
	private static Socket socket;
	private static InputStream is;
	private static OutputStream os;
	private static ParseXML parseXML;
	private static String serverID = "001";
	static {
		parseXML = ParseXML.createParseXML();
		Element element = parseXML.getServerXMLElement(serverID);
		try {
			socket = new Socket(element.elementText("host-address"), Integer.parseInt(element.elementText("port")));
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * 保持在线状态
	 */
	public void keepLoginState() {
		//
//		Runnable keepLogin_Runnable = () -> {
//			try {
//				while(true) {
//					os.write("i am login..".getBytes("UTF-8"));
//					os.flush();
//					
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		};
		
		Thread keepLogin_Runnable = new Thread() {
			@Override
			public void run() {
				super.run();
				//TODO change true
				while(true) {
					try {
						os.write("i am login..".getBytes("UTF-8"));
						os.flush();
						Thread.sleep(60000);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		ThreadConsole.useThreadPool().execute(keepLogin_Runnable);
	}
	
}
