package RunFile;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginDialog extends JDialog {


    private boolean authenticated = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Login", true);
        setLayout(new GridBagLayout());
        setSize(350, 220);
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Login ID:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy = 0;
        add(userLabel, gbc);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(passLabel, gbc);
        gbc.gridx = 1;
        add(passField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 2;
        add(buttonPanel, gbc);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // 🔒 Simple check (you can replace with DB or file check)
            if (username.equals("admin") && password.equals("1234")) {
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> {
            authenticated = false;
            dispose();
        });
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
