package RunFile;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.util.Arrays;
import java.util.Comparator;

public class FetchedFilesPanel extends JPanel {
    private final JPanel filesListPanel;   // where each file entry is shown
    private final File sourceDirectory;    // change to your actual source

    // Constructor
    public FetchedFilesPanel(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory; // e.g. new File("D:/Aniket/WhatsappChats")
        setLayout(new BorderLayout());

        // Header with Refresh button (right side)
        JPanel header = new JPanel(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));

        // Use your createStyledButton if you already have one.
        JButton refreshButton = createStyledButton("Refresh Files", new Color(100, 127, 188));
        // Add click behavior
        refreshButton.addActionListener(e -> refreshFetchedFiles());
        controls.add(refreshButton);

        header.add(controls, BorderLayout.NORTH);
        add(header, BorderLayout.NORTH);

        // Files list area (vertical list inside a scroll pane)
        filesListPanel = new JPanel();
        filesListPanel.setLayout(new BoxLayout(filesListPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(filesListPanel);
        add(scroll, BorderLayout.CENTER);

        // initial load
        refreshFetchedFiles();
    }

    // Public method you can call from outside instead of clicking the button
    public void refreshFetchedFiles() {
        // Use SwingWorker to fetch on background thread, update UI on EDT
        SwingWorker<List<File>, Void> worker = new SwingWorker() {
            @Override
            protected List<File> doInBackground() {
                File[] arr = (sourceDirectory != null) ? sourceDirectory.listFiles() : null;
                List<File> list = (arr == null) ? Collections.emptyList() : Arrays.asList(arr);
                // optional: sort by name or date
                list.sort(Comparator.comparing(File::getName));
                return list;
            }

            @Override
            protected void done() {
                try {
                    List<File> files = (List<File>) get();
                    filesListPanel.removeAll();

                    if (files.isEmpty()) {
                        filesListPanel.add(new JLabel("No files found"));
                    } else {
                        for (File f : files) {
                            filesListPanel.add(createFileEntry(f));
                        }
                    }

                    filesListPanel.revalidate();
                    filesListPanel.repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Window w = SwingUtilities.getWindowAncestor(FetchedFilesPanel.this);
                    JOptionPane.showMessageDialog(w,
                            "Error refreshing files: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    // Create per-file UI row (filename + open button)
    private JComponent createFileEntry(File file) {
        JPanel row = new JPanel(new BorderLayout(8, 8));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // keep rows uniform

        JLabel nameLabel = new JLabel(file.getName());
        nameLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        row.add(nameLabel, BorderLayout.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        JButton openBtn = new JButton("Open");
        openBtn.addActionListener(e -> openFile(file));
        right.add(openBtn);

        // optional: delete button
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete " + file.getName() + " ?",
                    "Confirm delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (file.delete()) {
                    refreshFetchedFiles(); // refresh after delete
                } else {
                    JOptionPane.showMessageDialog(this, "Could not delete file.");
                }
            }
        });
        right.add(deleteBtn);

        row.add(right, BorderLayout.EAST);
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        return row;
    }

    // How to open file (desktop) — adjust for your needs
    private void openFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "Open not supported on this platform.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
        }
    }

    // If you don't have a createStyledButton, here's one example.
    // If you already have your own createStyledButton, omit this and use yours.
    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(120, 28));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        return b;
    }
}
