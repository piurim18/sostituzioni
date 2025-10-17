import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FrameSelezionaLista {

    private JFrame frame;
    private JButton dropdownButton;
    private JDialog popupDialog;
    private Map<JCheckBox, String> checkBoxMap = new HashMap<>();
    private JButton conferma = new JButton("Conferma");
    private String mex = "<html>DOPO AVER SELEZIONATO I DOCENTI ASSENTI,<br>" +
            "CHIUDERE IL POPUP E CONFERMARE<br>" +
            "CLICCANDO L'APPOSITO TASTO CONFERMA</html>";
    private JLabel sms = new JLabel(mex);

    public FrameSelezionaLista() {
        frame = new JFrame("Seleziona Docenti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        ArrayList<String> docenti = new ArrayList<>();

        for (Lezione l : LettoreFile.lezioni) {
            String[] cognomi = l.getCognome();

            for (String cognome : cognomi) {
                docenti.add(cognome);
            }
        }


        dropdownButton = new JButton("Seleziona docenti ▼");
        sms.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(sms);
        centerPanel.add(Box.createVerticalGlue());


        frame.add(dropdownButton, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(conferma, BorderLayout.SOUTH);


        popupDialog = new JDialog(frame, "Seleziona Docenti", false);
        popupDialog.setSize(400, 400);


        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));



        for (String docente : docenti) {
            JCheckBox checkBox = new JCheckBox(docente);
            checkBoxMap.put(checkBox, docente);
            checkboxPanel.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(350, 350));
        popupDialog.add(scrollPane);


        dropdownButton.addActionListener(e -> {
            if (popupDialog.isVisible()) {
                popupDialog.setVisible(false);
            } else {
                Point location = dropdownButton.getLocationOnScreen();
                int x = location.x + (dropdownButton.getWidth() / 2) - (popupDialog.getWidth() / 2);
                int y = location.y + dropdownButton.getHeight();
                popupDialog.setLocation(x, y);
                popupDialog.setVisible(true);
            }
        });

        frame.setVisible(true);
    }


}
