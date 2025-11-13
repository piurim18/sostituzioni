//import Sostituzioni.GestoreSostituzioni;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public class FrameSelezionaLista {
    private JFrame frame;
    private JButton dropdownButton;
    private JDialog popupDialog;
    private JButton confermaButton;
    private JButton indietroButton;
    private JComboBox<String> giornoComboBox;
    private ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
    private ArrayList<String> docentiList = new ArrayList<>();
    private GestoreSostituzioni gestoreSostituzioni;

    private final Color PRIMARY_COLOR = new Color(41, 128, 185);    // azzurrino
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);  // blu chiaro
    private final Color ACCENT_COLOR = new Color(46, 204, 113);     // verdino
    private final Color WARNING_COLOR = new Color(231, 76, 60);     // rosso
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245); // grigio chiaro
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(52, 73, 94);

    public FrameSelezionaLista() {
        gestoreSostituzioni = new GestoreSostituzioni(LettoreFile.lezioni);
        estraiDocentiUnici();

        initializeUI();
        setupEventListeners();
        frame.setVisible(true);
    }

    private void initializeUI() {
        frame = new JFrame("Selezione Docenti Assenti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(15, 15));
        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel titoloLabel = new JLabel("GESTIONE SOSTITUZIONI", SwingConstants.CENTER);
        titoloLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titoloLabel.setForeground(PRIMARY_COLOR);
        titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JLabel istruzioniLabel = new JLabel(
                "<html><div style='text-align: center; padding: 10px; color: #555; font-size: 14px;'>"
                        + "Seleziona il giorno e i docenti assenti, poi conferma per generare le sostituzioni"
                        + "</div></html>",
                SwingConstants.CENTER
        );
        istruzioniLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        String[] giorni = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"};
        giornoComboBox = new JComboBox<>(giorni);
        giornoComboBox.setPreferredSize(new Dimension(250, 40));
        giornoComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        giornoComboBox.setBackground(CARD_COLOR);
        giornoComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        dropdownButton = createStyledButton("Seleziona Docenti Assenti", SECONDARY_COLOR);
        dropdownButton.setPreferredSize(new Dimension(300, 45));

        confermaButton = createStyledButton("Conferma e Genera Sostituzioni", ACCENT_COLOR);

        confermaButton.setPreferredSize(new Dimension(350, 50));

        indietroButton = createStyledButton("Indietro", new Color(149, 165, 166));
        indietroButton.setPreferredSize(new Dimension(120, 40));

        setupPopupDocenti();

        JPanel topPanel = createCardPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(titoloLabel, BorderLayout.NORTH);
        topPanel.add(istruzioniLabel, BorderLayout.CENTER);

        JPanel centerPanel = createCardPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel giornoPanel = createFormPanel();
        JLabel giornoLabel = createFormLabel("Giorno:");
        giornoPanel.add(giornoLabel);
        giornoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        giornoPanel.add(giornoComboBox);
        giornoPanel.setMaximumSize(new Dimension(500, 50));

        JPanel docentiPanel = createFormPanel();
        JLabel docentiLabel = createFormLabel("Docenti assenti:");
        docentiPanel.add(docentiLabel);
        docentiPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        docentiPanel.add(dropdownButton);
        docentiPanel.setMaximumSize(new Dimension(500, 50));

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(giornoPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(docentiPanel);
        centerPanel.add(Box.createVerticalGlue());

        JPanel bottomPanel = createCardPanel();
        bottomPanel.setLayout(new BorderLayout(10, 0));

        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actionButtonsPanel.setBackground(CARD_COLOR);
        actionButtonsPanel.add(indietroButton);
        actionButtonsPanel.add(confermaButton);

        bottomPanel.add(actionButtonsPanel, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, backgroundColor, 0, getHeight(), backgroundColor.darker());
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(backgroundColor.brighter(), 2),
                        BorderFactory.createEmptyBorder(8, 18, 8, 18)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }
        });

        return button;
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        label.setPreferredSize(new Dimension(120, 30));
        return label;
    }

    private void estraiDocentiUnici() {
        Set<String> nomiUnici = new HashSet<>();
        docentiList.clear();

        if (LettoreFile.lezioni == null) {
            System.err.println("ATTENZIONE: LettoreFile.lezioni è null!");
        }

        for (Lezione lezione : LettoreFile.lezioni) {
            if (lezione != null && lezione.getCognome() != null) {
                for (String cognome : lezione.getCognome()) {
                    if (cognome != null) {
                        String nomePulito = cognome.trim();
                        if (!nomePulito.isEmpty() && nomiUnici.add(nomePulito.toLowerCase())) {
                            docentiList.add(nomePulito);
                        }
                    }
                }
            }
        }

        if (docentiList.isEmpty()) {
            System.out.println("Nessun docente trovato");
        }

        docentiList.sort(String::compareToIgnoreCase);
        System.out.println("Docenti caricati: " + docentiList.size());
    }

    private void setupPopupDocenti() {
        popupDialog = new JDialog(frame, "Seleziona Docenti Assenti", true);
        popupDialog.setLayout(new BorderLayout(10, 10));
        popupDialog.setSize(600, 600);
        popupDialog.setLocationRelativeTo(frame);
        popupDialog.getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = createCardPanel();
        JLabel headerLabel = new JLabel("Seleziona i docenti assenti:");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(headerLabel);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setBackground(CARD_COLOR);
        checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        checkBoxList.clear();
        for (String docente : docentiList) {
            JCheckBox checkBox = createStyledCheckBox(docente);
            checkBoxList.add(checkBox);
            checkBoxPanel.add(checkBox);
            checkBoxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.setPreferredSize(new Dimension(350, 250));

        JPanel buttonPanel = createCardPanel();
        JButton selezioneTuttiButton = createStyledButton("Seleziona Tutti", SECONDARY_COLOR);
        JButton deselezioneTuttiButton = createStyledButton("Deseleziona", WARNING_COLOR);
        JButton okButton = createStyledButton("OK", ACCENT_COLOR);

        selezioneTuttiButton.setPreferredSize(new Dimension(140, 35));
        deselezioneTuttiButton.setPreferredSize(new Dimension(140, 35));
        okButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(selezioneTuttiButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deselezioneTuttiButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(okButton);

        selezioneTuttiButton.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxList) {
                checkBox.setSelected(true);
            }
        });

        deselezioneTuttiButton.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxList) {
                checkBox.setSelected(false);
            }
        });

        okButton.addActionListener(e -> {
            popupDialog.setVisible(false);
            aggiornaTestoBottone();
        });

        popupDialog.add(headerPanel, BorderLayout.NORTH);
        popupDialog.add(scrollPane, BorderLayout.CENTER);
        popupDialog.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setBackground(CARD_COLOR);
        checkBox.setForeground(TEXT_COLOR);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return checkBox;
    }

    private void setupEventListeners() {
        dropdownButton.addActionListener(e -> {
            Point location = dropdownButton.getLocationOnScreen();
            popupDialog.setLocation(location.x, location.y + dropdownButton.getHeight() + 5);
            popupDialog.setVisible(true);
        });

        confermaButton.addActionListener(e -> {
            elaboraConferma();
        });

        indietroButton.addActionListener(e -> {
            frame.setVisible(false);
            new FramePanelScelta();
            //new FrameScelta().setVisible(true);
        });
    }

    private void elaboraConferma() {
        ArrayList<String> docentiAssentiSelezionati = getDocentiAssentiSelezionati();

        if (docentiAssentiSelezionati.isEmpty()) {
            showCustomDialog("Attenzione",
                    "Seleziona almeno un docente assente.",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String giornoSelezionato = (String) giornoComboBox.getSelectedItem();
        mostraRiepilogo(giornoSelezionato, docentiAssentiSelezionati);
    }



    private void showCustomDialog(String title, String message, int messageType) {


        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(frame, label, title, messageType);
    }

    private ArrayList<String> getDocentiAssentiSelezionati() {
        ArrayList<String> selezionati = new ArrayList<>();
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isSelected()) {
                selezionati.add(docentiList.get(i));
            }
        }
        return selezionati;
    }

    private void aggiornaTestoBottone() {
        int count = getDocentiAssentiSelezionati().size();
        if (count == 0) {
            dropdownButton.setText("Seleziona Docenti Assenti");
        } else {
            dropdownButton.setText("Docenti Assenti Selezionati: " + count);
        }
    }

    private void mostraRiepilogo(String giorno, ArrayList<String> docentiAssenti) {
        StringBuilder messaggio = new StringBuilder();
        messaggio.append("<html><div style='text-align: center; font-family: Segoe UI;'>");
        messaggio.append("<h3 style='color: " + toHex(PRIMARY_COLOR) + "; margin-bottom: 15px;'>RIEPILOGO SELEZIONE</h3>");
        messaggio.append("<div style='background: #f8f9fa; padding: 15px; border-radius: 8px; margin: 10px 0;'>");
        messaggio.append("<p style='margin: 5px 0;'><b style='color: " + toHex(TEXT_COLOR) + ";'>Giorno:</b> <span style='color: " + toHex(SECONDARY_COLOR) + ";'>" + giorno + "</span></p>");
        messaggio.append("<p style='margin: 5px 0;'><b style='color: " + toHex(TEXT_COLOR) + ";'>Docenti assenti:</b> <span style='color: " + toHex(ACCENT_COLOR) + ";'>" + docentiAssenti.size() + "</span></p>");
        messaggio.append("</div>");
        messaggio.append("<div style='text-align: left; max-height: 150px; overflow-y: auto; padding: 10px; background: white; border-radius: 5px; border: 1px solid #eee;'>");

        for (String docente : docentiAssenti) {
            messaggio.append("<p style='margin: 8px 0; padding-left: 10px; border-left: 3px solid " + toHex(ACCENT_COLOR) + ";'>• " + docente + "</p>");
        }

        messaggio.append("</div>");
        messaggio.append("<p style='margin-top: 15px; color: #666;'>Premi OK per generare le sostituzioni</p>");
        messaggio.append("</div></html>");

        int result = JOptionPane.showConfirmDialog(frame,
                messaggio.toString(),
                "Conferma Sostituzioni",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            gestoreSostituzioni.setDocentiAssenti(docentiAssenti);

            ArrayList<Object[]> tutteSostituzioni = new ArrayList<>();
            for (String docente : docentiAssenti) {
                ArrayList<String[]> sostituzioni = gestoreSostituzioni.getSostitutiCompresenza(docente, giorno);

                tutteSostituzioni.add(new Object[]{docente, sostituzioni});
            }

            new FrameSostituzioni(tutteSostituzioni, docentiAssenti, giorno);
            frame.dispose();
        }
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new FrameSelezionaLista();
        });
    }
}