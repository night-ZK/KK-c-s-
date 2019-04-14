package transmit;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.dom4j.Element;

import parsefile.ParseXML;

public class SocketClient {
	private static Socket socket;
	private static ParseXML parseXML;
	private static String serverID = "001";
	static {
		parseXML = ParseXML.createParseXML();
		Element element = parseXML.getServerXMLElement(serverID);
		try {
			socket = new Socket(element.elementText("host-address")
					, Integer.parseInt(element.elementText("port")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * ��������״̬
	 */
//	public void keepLoginState() {
//		Thread keepLogin_Runnable = new Thread() {
//			@Override
//			public void run() {
//				super.run();
//				//TODO change true
//				while(true) {
//					OutputStream os = null;
//					try {					
//						os = socket.getOutputStream();
//						synchronized(socket) {
//							os.write("i am login..".getBytes("UTF-8"));
//							os.flush();
//							Thread.sleep(60000);							
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}finally {
//						if(os != null)
//							try {
//								os.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//					}
//				}
//			}
//		};
//		ThreadConsole.useThreadPool().execute(keepLogin_Runnable);
//	}

	public static Socket getSocket() {
		return socket;
	}	
}
