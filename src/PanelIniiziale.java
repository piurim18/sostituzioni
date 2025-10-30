import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class PanelIniiziale extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(255, 255, 230); // Grigio chiaro, quasi bianco
    private static final Color PRIMARY_COLOR = new Color(192, 57, 43);    // Blu acceso (Carica File)
    private static final Color ACCENT_COLOR = new Color(124, 179, 66);     // Verde vivace (Prosegui)
    private static final Color TEXT_COLOR = new Color(0, 0, 0);         // Blu scuro per il testo

    private final JButton carica = new JButton("CARICA FILE");
    private final JButton prosegui = new JButton("PROSEGUI");

    public PanelIniiziale() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);



        JLabel titolo = new JLabel("GESTIONE ORARIO LEZIONI", SwingConstants.CENTER);
        titolo.setFont(new Font("Arial", Font.BOLD, 50)); // Font più grande e audace
        titolo.setForeground(TEXT_COLOR);


        JLabel sottotitolo = new JLabel("Carica il file CSV per iniziare", SwingConstants.CENTER);
        sottotitolo.setFont(new Font("Arial", Font.PLAIN, 18));
        sottotitolo.setForeground(TEXT_COLOR.darker());


        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Testo pulsanti in grassetto
        Dimension buttonDim = new Dimension(280, 70); // Pulsanti più grandi


        setupButton(carica, buttonFont, PRIMARY_COLOR, Color.BLACK, buttonDim);
        setupButton(prosegui, buttonFont, ACCENT_COLOR, Color.BLACK, buttonDim);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;


        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 10, 0);
        add(titolo, gbc);


        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 60, 0);
        add(sottotitolo, gbc);


        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 15, 0);
        add(carica, gbc);


        gbc.gridy = 3;
        gbc.insets = new Insets(25, 0, 50, 0);
        add(prosegui, gbc);


        carica.addActionListener(this::azioneCaricaFile);
        prosegui.addActionListener(this::azioneProsegui);
    }

    private void setupButton(JButton button, Font font, Color bgColor, Color fgColor, Dimension dim) {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setPreferredSize(dim);

        // 🔲 Cornice nera visibile
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 3),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            private final Color originalBg = bgColor;

            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(originalBg.brighter());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(originalBg);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                button.setBackground(originalBg.darker());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(originalBg.brighter());
            }
        });
    }




    private void azioneCaricaFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Seleziona File Orario (CSV)");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {

                LettoreFile.leggiFile(selectedFile);
                //JOptionPane.showMessageDialog(this, "File caricato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | CsvValidationException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore nel caricamento o nella validazione del file:\n" + ex.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void azioneProsegui(ActionEvent e) {

        new FrameScelta();
        SwingUtilities.getWindowAncestor(this).dispose();
    }
}