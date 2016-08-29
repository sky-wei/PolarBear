package com.wei.polarbear.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.UIManager;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import com.wei.polarbear.tools.Tools;

public class GuiTools {

	/**
	 * 设置窗口的显示的位置,如果relative为null则current默认显示在屏幕中间,
	 * 如果relative不为null,current则显示在relative窗体的中间位置
	 * @param relative 当前窗体的父窗体
	 * @param current 当前需要修改窗体
	 * @param width 当前窗体显示的宽
	 * @param height 当前窗体显示的高
	 */
	public static void revisionSize(Window relative, Window current, int width,int height) {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int screenWidth = dimension.width;
		int screenHeight = dimension.height;
		
		if (width > screenWidth) width = screenWidth;
		if (height > screenHeight) height = screenHeight;
		
		if (relative == null) {
			// 显示在当前屏幕中心位置
			current.setLocation((screenWidth - width) >> 1, (screenHeight - height) >> 1);
			current.setPreferredSize(new Dimension(width, height));
			return ;
		}
		
		// 显示在父窗体的中心位置
		current.setLocation(relative.getX() + ((relative.getWidth() - width) >> 1), 
				relative.getY() + ((relative.getHeight() - height) >> 1));
		current.setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * 设置当前程序的界面主题
	 * @param theme 主题文件路径
	 */
	public static void setGuiTheme(String theme) {
		
		try {
			NimRODLookAndFeel nimbusLookAndFeel = new NimRODLookAndFeel();
			NimRODTheme nimRODTheme = new NimRODTheme(theme);
			
			NimRODLookAndFeel.setCurrentTheme(nimRODTheme);
			UIManager.setLookAndFeel(nimbusLookAndFeel);
		} catch (Exception e) {
			Tools.log.error("Set Theme Exception!", e);
			try {
				// 出现异常使用系统默认主题
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {
				Tools.log.error("Set System Theme Exception!", e);
			}
		} 
	}
}
