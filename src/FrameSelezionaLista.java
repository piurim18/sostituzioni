//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FrameSelezionaLista {
    private JFrame frame;
    private JButton dropdownButton;
    private JDialog popupDialog;
    private JButton conferma;
    private ArrayList<JCheckBox> checkBoxList = new ArrayList();
    private ArrayList<String> docentiList = new ArrayList();
    GestoreSostituzioni gestoreSostituzioni;

    public FrameSelezionaLista() {
        this.gestoreSostituzioni = new GestoreSostituzioni(LettoreFile.lezioni);
        this.frame = new JFrame("Seleziona Docenti");
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(400, 300);
        this.frame.setLocationRelativeTo((Component)null);
        this.frame.setLayout(new BorderLayout());
        JLabel istruz = new JLabel("<html>Seleziona i docenti assenti<br>e premi 'Conferma'</html>", 0);
        this.dropdownButton = new JButton("Scegli docenti");
        this.conferma = new JButton("Conferma");
        Set<String> nomiUnici = new HashSet();

        for(Lezione l : LettoreFile.lezioni) {
            for(String cognome : l.getCognome()) {
                String nomePulito = cognome.trim().toLowerCase();
                if (nomiUnici.add(nomePulito)) {
                    this.docentiList.add(cognome);
                }
            }
        }

        this.popupDialog = new JDialog(this.frame, "Docenti", false);
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BoxLayout(checkPanel, 1));

        for(String docente : this.docentiList) {
            JCheckBox checkBox = new JCheckBox(docente);
            this.checkBoxList.add(checkBox);
            checkPanel.add(checkBox);
        }

        this.popupDialog.add(new JScrollPane(checkPanel));
        this.popupDialog.setSize(300, 300);
        this.dropdownButton.addActionListener((e) -> {
            Point p = this.dropdownButton.getLocationOnScreen();
            this.popupDialog.setLocation(p.x, p.y + this.dropdownButton.getHeight());
            this.popupDialog.setVisible(true);
        });
        this.conferma.addActionListener((e) -> {
            ArrayList<String> docentiAssentiSelezionati = new ArrayList();

            for(int i = 0; i < this.checkBoxList.size(); ++i) {
                if (((JCheckBox)this.checkBoxList.get(i)).isSelected()) {
                    docentiAssentiSelezionati.add((String)this.docentiList.get(i));
                }
            }

            JOptionPane.showMessageDialog(this.frame, "Docenti selezionati:\n" + String.join("\n", docentiAssentiSelezionati));
            ArrayList<String[]> tutteSostituzioni = new ArrayList();

            for(String docente : docentiAssentiSelezionati) {
                ArrayList<String[]> sostituzioni = this.gestoreSostituzioni.getSostitutiCompresenza(docente, GiornoSettimana.getGiorno());
                tutteSostituzioni.addAll(sostituzioni);
            }

            new FrameSostituzioneCompresenza(tutteSostituzioni);
            this.frame.dispose();
        });
        this.frame.add(istruz, "Center");
        this.frame.add(this.dropdownButton, "North");
        this.frame.add(this.conferma, "South");
        this.frame.setVisible(true);
    }
}
