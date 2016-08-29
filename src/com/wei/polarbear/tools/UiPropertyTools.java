package com.wei.polarbear.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.wei.polarbear.bean.UiProperty;

public class UiPropertyTools {
	
	public UiProperty loadUiProperty(File propertyFile) {
		
		if (propertyFile == null
				|| !propertyFile.isFile()) {
			return null;
		}
		
		InputStream is = null;
		
		try {
			Properties properties = new Properties();
			
			is = new FileInputStream(propertyFile);
			properties.load(is);
			
			UiProperty uiProperty = new UiProperty();
			uiProperty.setProperties(properties);
			
			return uiProperty;
		} catch (FileNotFoundException e) {
			Tools.log.error(propertyFile + " 文件不存在了...", e);
		} catch (IOException e) {
			Tools.log.error("读取 " + propertyFile + " 出错了..." , e);
		} finally {
			try {
				if (is != null) is.close();
			} catch (Exception e) {
			}
		}
		
		return null;
	}

	public boolean saveUiProperty(File propertyFile, UiProperty uiProperty) {
		
		if (propertyFile == null
				|| uiProperty == null) {
			return false;
		}
		
		OutputStream out = null;
		
		try {
			out = new FileOutputStream(propertyFile);
			
			uiProperty.getProperties().store(out, "Tools Theme - jingcai.wei");
			
			return true;
		} catch (FileNotFoundException e) {
			Tools.log.error(propertyFile + " 文件不存在了...", e);
		} catch (IOException e) {
			Tools.log.error("保存 " + propertyFile + " 出错了..." , e);
		} finally {
			try {
				if (out != null) out.close();
			} catch (Exception e) {
			}
		}
		
		return false;
	}
}
