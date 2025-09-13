package RunFile;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import OrderManage.mainClassOrderManage;
import WhatsappChats.mainClassAutoChats;

public class AryaRoboPanel extends JPanel{


    mainClassAutoChats main = new mainClassAutoChats();
    mainClassOrderManage main1 = new mainClassOrderManage();

    private static final String FILE_DIR = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target";

    public AryaRoboPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

     // Header Panel (for title + description)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(245, 245, 245));
        
     // Title Label
        JLabel titleLabel = new JLabel("ARYA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // Description
        JLabel description = new JLabel("Automatic handling WhatsApp messages", SwingConstants.CENTER);
        description.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        description.setForeground(new Color(80, 80, 80));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));

     // Add to header panel
        headerPanel.add(titleLabel);
        headerPanel.add(description);

        // Add header panel to NORTH
        add(headerPanel, BorderLayout.NORTH);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color(255,248,220));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 50, 30, 50),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)
        ));

        JButton startButton = createStyledButton("Start WhatsApp Chats", "src\\test\\resources\\icons\\whatsapp.png", new Color(66, 133, 244));
        startButton.addActionListener(e -> {
            try {
                main.ExecuteWhatsappChats();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton orderButton = createStyledButton("Manage Online Orders", "src\\test\\resources\\icons\\shopping-bag.png", new Color(255, 152, 0));
        orderButton.addActionListener(e -> {
            try {
                main1.ExecuteOrdermManage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(orderButton);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(255,248,220));
        centerWrapper.add(buttonPanel);

        add(centerWrapper, BorderLayout.CENTER);

        // 🔹 File List Section
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
        filePanel.setBackground(new Color(255,248,220));
        filePanel.setBorder(BorderFactory.createTitledBorder("Available Files"));

        loadFileList(filePanel);

        JScrollPane scrollPane = new JScrollPane(filePanel);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private Color Color(int i, int j, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	// Load files dynamically
    private void loadFileList(JPanel filePanel) {
        filePanel.removeAll(); // clear old list

        File dir = new File(FILE_DIR);
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f.isFile()) {
                    JPanel fileRow = new JPanel(new BorderLayout());
                    fileRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                    fileRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
                    fileRow.setBackground(Color.WHITE);

                    JLabel fileLabel = new JLabel("📄 " + f.getName());
                    fileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    fileLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

                    // Buttons panel
                    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    actionPanel.setOpaque(false);

                    JButton downloadBtn = createStyledSmallButton("Download", new Color(76, 175, 80));
                    downloadBtn.addActionListener(e -> {
                        try {
                            Desktop.getDesktop().open(f);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Error opening file: " + f.getName());
                        }
                    });

                    JButton deleteBtn = createStyledSmallButton("Delete", new Color(244, 67, 54));
                    deleteBtn.addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(this,
                                "Are you sure you want to delete " + f.getName() + "?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            if (f.delete()) {
                                JOptionPane.showMessageDialog(this, "File deleted: " + f.getName());
                                loadFileList(filePanel); // refresh file list
                                filePanel.revalidate();
                                filePanel.repaint();
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to delete: " + f.getName());
                            }
                        }
                    });

                    actionPanel.add(downloadBtn);
                    actionPanel.add(deleteBtn);

                    fileRow.add(fileLabel, BorderLayout.CENTER);
                    fileRow.add(actionPanel, BorderLayout.EAST);

                    filePanel.add(fileRow);
                }
            }
        } else {
            JLabel emptyLabel = new JLabel("No files available", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            filePanel.add(emptyLabel);
        }
    }

 // Styled big buttons (main actions)
    private JButton createStyledButton(String text, String iconPath, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 6, 20, 20);

                // Background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 20, 20);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(false);
                setOpaque(false);
            }
        };

        // Icon
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaled = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.out.println("⚠️ Icon not found: " + iconPath);
        }

        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);
        button.setMargin(new Insets(12, 20, 12, 20));

        // Hover effect (lighter shade)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    // Styled small buttons (Download/Delete)
    private JButton createStyledSmallButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }
}
