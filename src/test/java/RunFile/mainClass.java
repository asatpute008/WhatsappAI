package RunFile;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class mainClass {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Arya ROBO");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setContentPane(new AryaRoboPanel());
            frame.setVisible(true);
        });
    }
}
