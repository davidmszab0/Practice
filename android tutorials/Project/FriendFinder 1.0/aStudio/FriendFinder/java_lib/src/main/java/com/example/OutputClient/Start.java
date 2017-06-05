package com.example.OutputClient;

import javax.swing.UIManager;

public class Start {
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Panel.background", OutputFrame.COLOR_DARK);
			UIManager.put("CheckBox.background", OutputFrame.COLOR_DARK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		OutputFrame frame = new OutputFrame();
		frame.setVisible(true);
	}

}
