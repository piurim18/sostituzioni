import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class PanelIniiziale extends JPanel {
    private final JButton carica = new JButton("Carica File");
    private final JButton prosegui = new JButton("Prosegui");

    public PanelIniiziale() {
        setLayout(new GridBagLayout());
        setBackground(new Color(108, 140, 188));

        Font titleFont = new Font("Segoe UI Semibold", Font.BOLD, 36);
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 18);


        JLabel titleLabel = new JLabel("GESTIONALE SOSTITUZIONI");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(40, 55, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setupButton(carica, buttonFont, new Color(70, 130, 180), Color.WHITE);
        setupButton(prosegui, buttonFont, new Color(46, 204, 113), Color.WHITE);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 30));
        buttonPanel.setOpaque(false);
        buttonPanel.add(carica);
        buttonPanel.add(prosegui);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        card.add(buttonPanel, BorderLayout.CENTER);
        card.setPreferredSize(new Dimension(350, 250));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(40, 0, 30, 0);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(card, gbc);

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
                BorderFactory.createLineBorder(bgColor.darker(), 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        Color originalBg = bgColor;
        Color hoverBg = bgColor.brighter();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
            }
        });
    }

}
