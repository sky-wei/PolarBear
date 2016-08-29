package com.wei.polarbear.tools;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class DESedeCoder {

    private static final String KEY_ALGORITHM = "DESede"; 
    
    private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/ISO10126Padding";
    
    public byte[] initSecretKey() throws NoSuchAlgorithmException {
    	
    	KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
    	keyGenerator.init(168);
    	
    	SecretKey secretKey = keyGenerator.generateKey();
    	
    	return secretKey.getEncoded();
    }
    
    public Key toKey(byte[] key) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
    	
    	if (key == null || key.length <= 0)	return null;
    	
    	DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
    	
    	SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
    	
    	return secretKeyFactory.generateSecret(desKeySpec);
    }
    
    public byte[] encrypt(byte[] data, byte[] key) throws Exception {
    	
    	if (data == null || data.length <= 0
    		|| key == null || key.length <= 0) {
    		return null;
    	}
    	
    	Key k = toKey(key);
    	
    	if (k == null)		return null;
    	
    	Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    	cipher.init(Cipher.ENCRYPT_MODE, k);
    	
    	return cipher.doFinal(data);
    }
    
    public byte[] decryption(byte[] data, byte[] key) throws Exception {
    	
    	if (data == null || data.length <= 0
        		|| key == null || key.length <= 0) {
        		return null;
        }
    	
    	Key k = toKey(key);
    	
    	if (k == null) return null;
    	
    	Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    	cipher.init(Cipher.DECRYPT_MODE, k);
    	
    	return cipher.doFinal(data);
    }
    
    public String encrypt(String data, String key) throws Exception {
    	
    	if (data == null || data.trim().length() <= 0
    		|| key == null || key.trim().length() <= 0) {
    		return null;
    	}
        
    	return Tools.byteToHex(encrypt(data.getBytes(), strToDesedeKey(key)));
    }
    
    public String decryption(String data, String key) throws Exception {
    	
    	if (data == null || data.trim().length() <= 0
        		|| key == null || key.trim().length() <= 0) {
        		return null;
        }
    	
    	return new String(decryption(Tools.hexToByte(data.getBytes()), strToDesedeKey(key)));
    }
    
    public byte[] strToDesedeKey(String str) {
    	
    	if (str == null || str.trim().length() <= 0) {
    		return null;
    	}
    	
    	byte[] bytes = null;
    	byte[] strs = str.getBytes();
    	int len = strs.length;
    	int off = 0,offlen = 0;
    	
    	if (len <= 24) {
    		bytes = new byte[24];
    	} else {
    		bytes = new byte[(len + 6) - len % 6];
    	}
    	offlen = bytes.length;

    	while(off != offlen) {
    		
    		if ((offlen - off) >= len) {
    			System.arraycopy(strs, 0, bytes, off, len);
    			off += len;
    		} else if ((offlen - off) <= len) {
    			System.arraycopy(strs, 0, bytes, off, offlen - off);
    			off += (offlen - off);
    		}
    	}
    	
    	return bytes;
    }
}
