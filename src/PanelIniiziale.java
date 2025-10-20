import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class PanelIniiziale extends JPanel {
    private JButton carica = new JButton(" Carica File");
    private JButton prosegui = new JButton("Prosegui");

    public PanelIniiziale() {
        setLayout(new GridBagLayout());
        setBackground(new Color(162, 185, 216));


        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 18);


        setupButton(carica, buttonFont, new Color(70, 130, 180), Color.WHITE);
        setupButton(prosegui, buttonFont, new Color(46, 204, 113), Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridy = 0;
        add(carica, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(60, 0, 0, 0);
        add(prosegui, gbc);

        carica.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(PanelIniiziale.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    LettoreFile.leggiFile(selectedFile);
                } catch (IOException | CsvValidationException ex) {
                    JOptionPane.showMessageDialog(this, "Errore nel caricamento del file:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        prosegui.addActionListener((ActionEvent e) -> {
            new FrameScelta();
            SwingUtilities.getWindowAncestor(PanelIniiziale.this).dispose();
        });
    }

    private void setupButton(JButton button, Font font, Color bgColor, Color fgColor) {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker()),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


    }
}
