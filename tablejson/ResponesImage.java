package tablejson;

import java.util.Arrays;

import row.RowSupper;

public class ResponesImage extends RowSupper implements JsonInterface{

	private static final long serialVersionUID = 1L;
	
	private String imageDescribe;
	private byte[] imageByte;
	
	public ResponesImage(String imageDescribe, byte[] imageByte) {
		this.imageDescribe = imageDescribe;
		this.imageByte = imageByte;
	}
	public String getImageDescribe() {
		return imageDescribe;
	}
	public void setImageDescribe(String imageDescribe) {
		this.imageDescribe = imageDescribe;
	}
	public byte[] getImageByte() {
		return imageByte;
	}
	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}
	@Override
	public String toString() {
		return "ResponesImage [imageDescribe=" + imageDescribe + ", imageByte=" + Arrays.toString(imageByte) + "]";
	}

}
