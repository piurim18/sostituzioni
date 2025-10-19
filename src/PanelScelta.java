import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PanelScelta extends JPanel {
    private JButton docenti = new JButton(" Orario per docenti");
    private JButton classe = new JButton("Orario per classe");
    private JButton sostituzione = new JButton("Sostituzioni");

    public PanelScelta() {
        setLayout(new GridBagLayout());
        setBackground(new Color(162, 185, 216));


        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 18);


        setupButton(docenti, buttonFont, new Color(96, 177, 221), Color.WHITE);
        setupButton(classe, buttonFont, new Color(96, 177, 221), Color.WHITE);
        setupButton(sostituzione, buttonFont, new Color(96, 177, 221), Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridy = 0;
        add(docenti, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(60, 0, 0, 0);
        add(classe, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(60, 0, 0, 0);
        add(sostituzione, gbc);


        docenti.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrameOrarioDocenti();
                SwingUtilities.getWindowAncestor(PanelScelta.this).dispose();

            }
        });

        classe.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrameOrarioClassi();
                SwingUtilities.getWindowAncestor(PanelScelta.this).dispose();
            }
        });

        sostituzione.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrameSelezionaLista();
                SwingUtilities.getWindowAncestor(PanelScelta.this).dispose();
            }
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
