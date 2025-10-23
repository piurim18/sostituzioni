import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelScelta extends JPanel {


    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Grigio chiaro
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Blu scuro per il testo


    private static final Color COLOR_DOCENTI = new Color(255, 179, 186);     // Blu Acceso
    private static final Color COLOR_CLASSI = new Color(155, 89, 182);      // Viola Elegante
    private static final Color COLOR_SOSTITUZIONI = new Color(255, 62, 150); // Arancione Vivace

    private final JButton docenti = new JButton("ORARIO DOCENTI");
    private final JButton classe = new JButton("ORARIO CLASSI");
    private final JButton sostituzione = new JButton("GESTIONE SOSTITUZIONI");


    private static final Dimension BUTTON_DIM = new Dimension(350, 80);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 22);

    public PanelScelta() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);


        JLabel titolo = new JLabel("SELEZIONA OPZIONE", SwingConstants.CENTER);
        titolo.setFont(new Font("Arial", Font.BOLD, 40));
        titolo.setForeground(TEXT_COLOR);


        setupButton(docenti, BUTTON_FONT, COLOR_DOCENTI, Color.WHITE, BUTTON_DIM);
        setupButton(classe, BUTTON_FONT, COLOR_CLASSI, Color.WHITE, BUTTON_DIM);
        setupButton(sostituzione, BUTTON_FONT, COLOR_SOSTITUZIONI, Color.WHITE, BUTTON_DIM);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;


        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 40, 0);
        add(titolo, gbc);


        gbc.gridy = 1;
        gbc.insets = new Insets(15, 0, 15, 0);
        add(docenti, gbc);


        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 15, 0);
        add(classe, gbc);


        gbc.gridy = 3;
        gbc.insets = new Insets(15, 0, 50, 0);
        add(sostituzione, gbc);


        docenti.addActionListener(e -> {
            new FrameOrarioDocenti();
            SwingUtilities.getWindowAncestor(PanelScelta.this).dispose();
        });

        classe.addActionListener(e -> {
            new FrameOrarioClassi();
            SwingUtilities.getWindowAncestor(PanelScelta.this).dispose();
        });

        sostituzione.addActionListener(e -> {
            new FrameSelezionaLista();
            SwingUtilities.getWindowAncestor(PanelScelta.this).dispose();
        });
    }

    private void setupButton(JButton button, Font font, Color bgColor, Color fgColor, Dimension dim) {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setPreferredSize(dim);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker().darker(), 0),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);


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
}