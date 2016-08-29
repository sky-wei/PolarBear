package com.wei.polarbear.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import com.wei.polarbear.PolarBearMain;
import com.wei.polarbear.tools.Tools;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = -527176379813615392L;
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	
	public AboutDialog(Frame frame) {
		super(frame, true);
		
		setTitle("关于本程序");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		setResizable(false);
		
		initPanel();
		
		GuiTools.revisionSize(frame, this, WIDTH, HEIGHT);
		
		pack();
	}
	
	private void initPanel() {
		
		setLayout(null);
		
		Icon icon = new ImageIcon("./res/icon.png");
		JLabel lable = new JLabel(icon);
		lable.setBounds(30, 30, 128, 128);
		add(lable);
		
		lable = new JLabel("有志者,事竟成,");
		lable.setBounds(200, 20, 200, 30);
		add(lable);
		
		lable = new JLabel("破斧沉舟,百日秦关终属楚.");
		lable.setBounds(200, 40, 200, 30);
		add(lable);
		
		lable = new JLabel("苦心人,天不负,");
		lable.setBounds(200, 65, 200, 30);
		add(lable);
		
		lable = new JLabel("卧薪尝胆,三千越甲可吞吴.");
		lable.setBounds(200, 85, 200, 30);
		add(lable);
		
		lable = new JLabel("微笑的人生更精彩...");
		lable.setBounds(200, 110, 100, 30);
		add(lable);
		
		lable = new JLabel("-- jingcai.wei");
		lable.setBounds(280, 130, 100, 30);
		add(lable);
		
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setBounds(30, 170, 340, 2);
		add(separator);
		
		lable = new JLabel("版本: " + PolarBearMain.VERSION);
		lable.setBounds(30, 180, 200, 30);
		add(lable);
		
		lable = new JLabel("作者: jingcai.wei");
		lable.setBounds(30, 205, 200, 30);
		add(lable);
		
		lable = new JLabel("邮箱: jingcai.wei@163.com");
		lable.setBounds(30, 230, 200, 30);
		add(lable);
		
		JButton ok = new JButton("确定");
		ok.setBounds(305, 230, 65, 25);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog.this.dispose();
			}
		});
		add(ok);
	}
}
