package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AryaRoboPanel extends JPanel {

	MainClass main = new MainClass();

    public AryaRoboPanel() {
        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Arya ROBO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // whatsapp chats Button
        JButton startButton = new JButton("Start WhatsApp Chats");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                try {
					main.whatsappChats();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
     // manage Order
        JButton OrderButton = new JButton("Manage Orders");
        OrderButton.setFont(new Font("Arial", Font.PLAIN, 18));
        OrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your function here
                try {
					main.whatsappChats();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        // Center align the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.BEFORE_FIRST_LINE);
        
        buttonPanel.add(OrderButton);
        add(OrderButton, BorderLayout.AFTER_LAST_LINE);
    }

}
