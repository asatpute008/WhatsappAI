package RunFile;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class mainClass {
	
	public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Arya Robo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 🔑 Show login dialog
            LoginDialog loginDialog = new LoginDialog(frame);
            loginDialog.setVisible(true);

            if (loginDialog.isAuthenticated()) {
                frame.setContentPane(new AryaRoboPanel());
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
