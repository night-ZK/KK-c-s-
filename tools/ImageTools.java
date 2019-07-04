package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

public class ImageTools {
	
	public static void saveImage(byte[] iconBytes, String path) {

		File iFile = new File(path);
		if(!iFile.exists())
			try {
				iFile.getParentFile().mkdirs();
				iFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		try (FileImageOutputStream fio 
				= new FileImageOutputStream(iFile)){
			
			fio.write(iconBytes, 0, iconBytes.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] getImageBytesByPath(String path){
		byte[] imageByte = null;
		FileImageInputStream fileImageInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			fileImageInputStream = new FileImageInputStream(new File(path));
			byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] bufByte = new byte[1024];
			int imageByteLength = -1;

			while ((imageByteLength = fileImageInputStream.read(bufByte)) != -1) {

				byteArrayOutputStream.write(bufByte, 0, imageByteLength);
			}

			imageByte = byteArrayOutputStream.toByteArray();

		}catch (IOException e){
			e.printStackTrace();
		}finally {
			try {
				if (!ObjectTool.isNull(byteArrayOutputStream)) byteArrayOutputStream.close();
				if (!ObjectTool.isNull(fileImageInputStream)) fileImageInputStream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return  imageByte;
	}
	
	public static byte[] getImageBytesByImage(Image image){
		byte[] imageByte = null;
		ByteArrayOutputStream bos = null;
		BufferedImage bi = null;
		try {
			
			bos = new ByteArrayOutputStream();
			bi = imageToBufferedImage(image);
			
			ImageIO.write(bi, "png", bos);
			imageByte = bos.toByteArray();
					
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			try {
				if (!ObjectTool.isNull(bos)) bos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return imageByte;
	}
	
	public static BufferedImage imageToBufferedImage(Image image) {
		BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bi.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		return bi;
	}

	/**
	 * ͼƬ�Աȶȵ���
	 * @param imgsrc
	 * @param contrast
	 * @return
	 */
    public static BufferedImage imgAdjustContrast(BufferedImage imgsrc, int contrast) {
        try {
            int contrast_average = 128;
            //����һ������͸���ȵ�ͼƬ
            BufferedImage back=new BufferedImage(imgsrc.getWidth(), imgsrc.getHeight(),BufferedImage.TYPE_INT_RGB);
            int width = imgsrc.getWidth();  
            int height = imgsrc.getHeight();  
            int pix;
            for (int i = 0; i < height; i++) { 
                for (int j = 0; j < width; j++) {  
                    int pixel = imgsrc.getRGB(j, i); 
                    Color color = new Color(pixel);

                    if (color.getRed() < contrast_average)
                    {
                        pix = color.getRed()- Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    }
                    else
                    {
                        pix = color.getRed() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int red= pix;
                    if (color.getGreen() < contrast_average)
                    {
                        pix = color.getGreen()- Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    }
                    else
                    {
                        pix = color.getGreen() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int green= pix;   
                    if (color.getBlue() < contrast_average)
                    {
                        pix = color.getBlue()- Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    }
                    else
                    {
                        pix = color.getBlue() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int blue= pix;  

                    color = new Color(red,green,blue);
                    int x=color.getRGB();
                    back.setRGB(j,i,x);
                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BufferedImage imgAdjustContrast(Image image, int contrast) {
    	return imgAdjustContrast(imageToBufferedImage(image), contrast);
    }
    
    public static ImageIcon toBlackWhite(Image image) {
        BufferedImage bufferedImage = createBlackWhiteImage(imageToBufferedImage(image), null);
        return new ImageIcon(bufferedImage);
    }
    
    /**
     * �ڰ��㷨��Ĭ��Ч��
     * @param image 
     * @return �µĺڰ׻�ͼƬ
     */
    public static BufferedImage createBlackWhiteImage(Image image) {
        return createBlackWhiteImage(imageToBufferedImage(image), null);
    }

    /**
     * �ڰ��㷨��Ĭ��Ч��
     * @param image
     * @radios ��ɫͨ�����ã�����Ϊ�졢�ơ� �̡� �ࡢ ����������ͨ��
     * @return �µĺڰ׻�ͼƬ
     */
    public static BufferedImage createBlackWhiteImage(BufferedImage image, float[] radios) {
    	int width = image.getWidth();   //��ȡλͼ�Ŀ�
    	int height = image.getHeight();  //��ȡλͼ�ĸ�
        BufferedImage result = new BufferedImage(width, height, image.getType());
        int alpha = 0xff000000;
        int r = 0;
        int g = 0;
        int b = 0;
        int max = 0;
        int min = 0;
        int mid = 0;
        int gray = 0;

        float radioMax = 0;
        float radioMaxMid = 0;

        if (radios == null) {
            //                    ��        ��         ��         ��         ��        ��
            radios = new float[]{0.4f,0.6f,0.4f,0.6f,0.2f,0.8f};
        }   
        for (int i = 0; i < width; i++) {//һ����ɨ��
            for (int j = 0; j < height; j++) {

                gray = image.getRGB(i, j);

                alpha = gray >>> 24;
                r = (gray>>16) & 0x000000ff;
                g = (gray >> 8) & 0x000000ff;
                b = gray & 0x000000ff;

                if (r >= g && r>=b) {
                    max = r;
                    radioMax = radios[0];
                }
                if (g>= r && g>=b) {
                    max = g;
                    radioMax = radios[2]; 
                }
                if (b >= r && b>=g) {
                    max = b;
                    radioMax = radios[4];
                }

                if (r<=g && r<=b) { // g+ b = cyan ��ɫ
                    min = r;
                    radioMaxMid = radios[3];
                }

                if (b <= r && b<=g) {//r+g = yellow ��ɫ
                    min = b;
                    radioMaxMid = radios[1];
                }
                if (g <= r && g<=b) {//r+b = m ���
                    min = g;
                    radioMaxMid = radios[5];
                }

                mid = r + g + b-max -min;

//              ��ʽ��gray= (max - mid) * ratio_max + (mid - min) * ratio_max_mid + min

                gray = (int) ((max - mid) * radioMax + (mid - min) * radioMaxMid + min);

                gray = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                result.setRGB(i, j, gray);
            }

        }
        return result;
    }

}
