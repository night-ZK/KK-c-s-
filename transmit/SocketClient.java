package transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.dom4j.Element;

import parsefile.ParseXML;

public abstract class SocketClient implements Runnable{
	
	private static ParseXML parseXML;
	private static String serverID = "001";
	private static String host;
	private static int port;
	
	//等待标志
	public boolean isWait;
	public boolean hasRepley;
	
	public static Socket socket;
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SocketClient() {
		try {
			this.os = socket.getOutputStream();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保持在线状态
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
	
}
