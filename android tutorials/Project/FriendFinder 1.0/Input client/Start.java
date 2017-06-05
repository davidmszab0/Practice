import javax.swing.UIManager;

/** Class for running the system */
public class Start {
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		StartFrame frame = new StartFrame();
		frame.setVisible(true);
	}

}
