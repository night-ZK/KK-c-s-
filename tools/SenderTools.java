package tools;

import java.io.IOException;
import java.io.OutputStream;
import message.MessageInterface;

public class SenderTools extends TransmitTool{

	private OutputStream os;
	
	public SenderTools(OutputStream os) {
		this.os = os;
	}
	
	/**
	 * ������Ϣ, ��Ϣ��һ��messageInterface����
	 * @param responesMessageModel
	 * @param os
	 * @throws IOException
	 */
	public SenderTools sendMessage(MessageInterface responesMessageModel) throws IOException {
		byte[] mgjByte = ObjectToByteArrays(responesMessageModel);
		
		writeForRule(mgjByte);
		
		return this;
	}

	/**
	 * ������Ϣ��
	 * @param responseLine
	 * @return
	 * @throws IOException
	 */
	public SenderTools sendLine(String responseLine) throws IOException {
		byte[] responseLineByte = responseLine.getBytes("UTF-8");
		
		System.out.println("responseLineByte.length: " + responseLineByte.length );
		
		writeForRule(responseLineByte);
		
		return this;
	}
	
	/**
	 * д����򣬲���OutputStreamд������
	 * @param b
	 * @return
	 * @throws IOException
	 */
	public SenderTools writeForRule(byte[] b) throws IOException {
		os.write(b.length >> 8);
		os.write(b.length);
		os.write(b);
		return this;
	}
	
	/**
	 * �������
	 * @return
	 * @throws IOException
	 */
	public SenderTools sendDone() throws IOException {
		os.flush();
		return this;
	}
	
	/**
	 * ����byte[]
	 * @param byteArrays
	 * @return
	 * @throws IOException
	 */
	public SenderTools sendByteArrays(byte[] byteArrays) throws IOException {
		writeForRule(byteArrays);
		return this;
	}
	
	/**
	 * ����byte[]
	 * @param byteArrays
	 * @return
	 * @throws IOException
	 */
	public SenderTools sendImageByteArrays(byte[] byteArrays) throws IOException {
		os.write(byteArrays);
		return this;
	}
	
	/**
	 * ����image��byte[], ���ͼƬ����50kb,����ָ�
	 * �ٷ���
	 * @param byteArrays
	 * @return
	 * @throws IOException
	 */
	public SenderTools sendImageByteArraysForBig(byte[] byteArrays) throws IOException {
		int p = byteArrays.length / 51200;
		if (p >= 1) {
			
			for(int i=0; i<p; i++) {
				os.write(byteArrays, i * 51200, 51200);
			}
			int off = p * 51200;
			int len = byteArrays.length - off;
			os.write(byteArrays, off, len);
			return this;
		}
		
		os.write(byteArrays);
		return this;
	}
}
