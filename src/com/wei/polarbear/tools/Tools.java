package com.wei.polarbear.tools;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.io.OutputFormat;

public class Tools {
	
	private static char[] base64EncodeChars = new char[] {    
	       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',    
	       'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',    
	       'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',    
	       'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',    
	       'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',    
	       'o', 'p', 'q', 'r', 's', 't', 'u', 'v',    
	       'w', 'x', 'y', 'z', '0', '1', '2', '3',    
	       '4', '5', '6', '7', '8', '9', '+', '/' };
	
	private static byte[] base64DecodeChars = new byte[] {    
		   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,    
		   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,    
		   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,    
		   52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,    
		   -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,    
		   15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,    
		   -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,    
		   41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
	
	/** 用来生成随机数对象 */
	public static final Random RANDOM = new Random(System.currentTimeMillis());
	/** 用来格式化xml文件内容对象 */
	public static final OutputFormat OUTPUT_FORMAT = OutputFormat.createPrettyPrint();
	/** 用来格式化时间对象 */
	public static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** 日志输出对象 */
	public static final Logger log = Logger.getLogger("PolarBear");
	
	{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	/**
	 * 隐藏字符串中指定位置后的所有字符
	 * @param value 需要隐藏的字符串
	 * @param index 从索引位置开始隐藏字符串
	 * @return 隐藏字符串 
	 */
	public static String concealBack(String value, int index) {
		
		if (value == null || value.trim().length() <= 0) {
			return null;
		}
		
		if (index >= value.length()) {
			return value;
		}
		
		StringBuilder result = new StringBuilder();
		result.append(value.substring(0, index));
		
		int count = value.length() - index;
		
		for (int i = 0; i < count; i++) {
			result.append("@");
		}
		
		return result.toString();
	}
	
	/**
	 * 隐藏字符串中指定位置前的所有字符
	 * @param value 需要隐藏的字符串
	 * @param index 从索引位置开始结束隐藏字符串
	 * @return 隐藏字符串 
	 */
	public static String concealFirst(String value, int index) {
		
		if (value == null || value.trim().length() <= 0) {
			return null;
		}
		
		if (index > value.length()) {
			index = value.length();
		}
		
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < index; i++) {
			result.append("@");
		}
		
		result.append(value.substring(index));
		
		return result.toString();
	}
	
	/**
	 * 获取指定文件的图片
	 * @param file 图片路径
	 * @return Image
	 */
	public static Image getImage(File file) {
		
		if (file == null || !file.exists()) {
			return null;
		}
		
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			Tools.log.error("IOException!", e);
		}
		
		return null;
	}
	
	/**
	 * 加密相应的密码(为明码),并返回相应加密过后的密码
	 * @param cxpressly 未加密的明码
	 * @return 返回加密过后的密码
	 */
	public static String md5Encryption(String cxpressly) {
		
		if (cxpressly == null
			|| cxpressly.trim().length() <= 0) {
			log.debug("Cxpressly is null!");
			return null;
		}
		
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String base64 = base64Encode(md5.digest(cxpressly.getBytes("UTF-8")));
			return byteToHex(md5.digest(base64.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException!", e);
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException!", e);
		}
		
		return null;
	}
	
	public static String base64Encode(byte[] data) {
		
       StringBuffer sb = new StringBuffer();    
       int len = data.length;    
       int i = 0;    
       int b1, b2, b3;    
	    
       while (i < len) {
           b1 = data[i++] & 0xff;    
           if (i == len) {    
               sb.append(base64EncodeChars[b1 >>> 2]);    
               sb.append(base64EncodeChars[(b1 & 0x3) << 4]);    
               sb.append("==");    
               break;    
           }    
           b2 = data[i++] & 0xff;    
           if (i == len) {    
               sb.append(base64EncodeChars[b1 >>> 2]);    
               sb.append(    
                       base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);    
               sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);    
               sb.append("=");    
               break;    
           }    
           b3 = data[i++] & 0xff;    
           sb.append(base64EncodeChars[b1 >>> 2]);    
           sb.append(    
                   base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);    
           sb.append(    
                   base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);    
           sb.append(base64EncodeChars[b3 & 0x3f]);    
       }    
       return sb.toString();    
	}
	
	public static byte[] base64Decode(String str) {
		
       byte[] data = str.getBytes();    
       int len = data.length;    
       ByteArrayOutputStream buf = new ByteArrayOutputStream(len);    
       int i = 0;    
       int b1, b2, b3, b4;    
	    
       while (i < len) {
    
               
           do {    
               b1 = base64DecodeChars[data[i++]];    
           } while (i < len && b1 == -1);    
           if (b1 == -1) {    
               break;    
           }    
    
               
           do {    
               b2 = base64DecodeChars[data[i++]];    
           } while (i < len && b2 == -1);    
           if (b2 == -1) {    
               break;    
           }    
           buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));    
    
               
           do {    
               b3 = data[i++];    
               if (b3 == 61) {    
                   return buf.toByteArray();    
               }    
               b3 = base64DecodeChars[b3];    
           } while (i < len && b3 == -1);    
           if (b3 == -1) {    
               break;    
           }    
           buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));    
    
               
           do {    
               b4 = data[i++];    
               if (b4 == 61) {    
                   return buf.toByteArray();    
               }    
               b4 = base64DecodeChars[b4];    
           } while (i < len && b4 == -1);    
           if (b4 == -1) {    
               break;    
           }    
           buf.write((int) (((b3 & 0x03) << 6) | b4));    
       }  
	   return buf.toByteArray();    
	}
	
	public static String byteToHex(byte[] bytes) {
    	
    	if (bytes == null || bytes.length <= 0) {
    		return null;
    	}
    	
    	int len = bytes.length;
    	StringBuffer sb = new StringBuffer();
    	
    	for (int i = 0; i < len; i++) {
    		
    		int k = bytes[i] & 0xFF;
    		if (k < 16) {
    			sb.append("0");
    		}
    		sb.append(Integer.toHexString(k));
    	}
    	
    	return sb.toString();
    }
    
    public static byte[] hexToByte(byte[] bytes) {
    	
    	if (bytes == null)	return null;
    	
    	if((bytes.length%2)!=0) {
    		
    		throw new IllegalArgumentException("长度不是偶数");
    	}

    	int len = bytes.length;
    	byte[] b2 = new byte[bytes.length/2];

         for (int n = 0; n < len; n+=2) {
        	 
        	 String item = new String(bytes,n,2);
        	 b2[n/2] = (byte)Integer.parseInt(item,16);
         }

         return b2;
    }
}
