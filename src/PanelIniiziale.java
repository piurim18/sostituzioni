import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class PanelIniiziale extends JPanel {
    private JButton carica = new JButton("Carica File");
    private JButton prosegui = new JButton("Prosegui");

    public PanelIniiziale() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 243, 247));

        Font buttonFont = new Font("Segoe UI Semibold", Font.PLAIN, 20);

        setupButton(carica, buttonFont, new Color(33, 150, 243), Color.WHITE);
        setupButton(prosegui, buttonFont, new Color(76, 175, 80), Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 40, 15, 40);

        gbc.gridy = 0;
        add(carica, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(50, 40, 15, 40);
        add(prosegui, gbc);


        carica.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(PanelIniiziale.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    LettoreFile.leggiFile(selectedFile);
                    JOptionPane.showMessageDialog(this, "File caricato correttamente!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | CsvValidationException ex) {
                    JOptionPane.showMessageDialog(this, "Errore nel caricamento del file:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        prosegui.addActionListener((ActionEvent e) -> {
            new FrameSceltaOrario();
            SwingUtilities.getWindowAncestor(PanelIniiziale.this).dispose();
        });
    }

    private void setupButton(JButton button, Font font, Color bgColor, Color fgColor) {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setPreferredSize(new Dimension(240, 60));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
}
