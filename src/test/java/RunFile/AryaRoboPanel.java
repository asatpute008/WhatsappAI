package RunFile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import OrderManage.mainClassOrderManage;
import WhatsappChats.mainClassAutoChats;

public class AryaRoboPanel extends JPanel{

	mainClassAutoChats main = new mainClassAutoChats();
	mainClassOrderManage main1 = new mainClassOrderManage();

	public AryaRoboPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(245, 245, 245)); // Light background

		// Title Label
		JLabel titleLabel = new JLabel("Arya ROBO", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
		titleLabel.setForeground(new Color(33, 33, 33));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		add(titleLabel, BorderLayout.NORTH);

		// Buttons Panel (centered with BoxLayout)
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(new Color(255, 255, 255));
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(30, 50, 30, 50),
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)
				));

		// WhatsApp Chats Button
		JButton startButton = createStyledButton("Start WhatsApp Chats", "icons/whatsapp.png");
		startButton.addActionListener(e -> {
			try {
				main.ExecuteWhatsappChats();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		});

		// Manage Orders Button
		JButton orderButton = createStyledButton("Manage Orders", "icons/orders.png");
		orderButton.addActionListener(e -> {
			try {
				main1.ExecuteOrdermManage();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		});

		// Add buttons with spacing
		buttonPanel.add(startButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // spacing
		buttonPanel.add(orderButton);

		// Center align the buttons panel
		JPanel centerWrapper = new JPanel(new GridBagLayout());
		centerWrapper.setBackground(new Color(245, 245, 245));
		centerWrapper.add(buttonPanel);

		add(centerWrapper, BorderLayout.CENTER);
	}

	// 🔹 Helper method to style buttons consistently with icons
	private JButton createStyledButton(String text, String iconPath) {
		JButton button = new JButton(text);

		// Load icon
		try {
			ImageIcon icon = new ImageIcon(iconPath);
			Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(scaled));
		} catch (Exception e) {
			System.out.println("⚠️ Icon not found: " + iconPath);
		}

		button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		button.setBackground(new Color(66, 133, 244));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Put icon before text
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setIconTextGap(12);

		// Hover effect
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(51, 102, 204));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(66, 133, 244));
			}
		});

		return button;
	}
}
