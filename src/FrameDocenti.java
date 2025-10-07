import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FrameDocenti extends JFrame {
    private JComboBox<String> comboDocenti;
    private JTable tabella;
    private DefaultTableModel modello;

    public FrameDocenti() {
        setTitle("Orario Docenti");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona docente: "));

        // Riempi la combo con i nomi dei docenti presenti nel file
        comboDocenti = new JComboBox<>();
        for (Lezione l : LettoreFile.lezioni) {
            String primoCognome = l.getCognome()[0]; // prendi il primo cognome dell'array

            if (((DefaultComboBoxModel<String>) comboDocenti.getModel()).getIndexOf(primoCognome) == -1) {
                comboDocenti.addItem(primoCognome);
            }
        }


        pannelloNord.add(comboDocenti);
        add(pannelloNord, BorderLayout.NORTH);

        String[] colonne = {"Giorno", "Orario", "Materia", "Classe", "Durata", "Co-docenza"};
        modello = new DefaultTableModel(colonne, 0);
        tabella = new JTable(modello);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        comboDocenti.addActionListener(e -> aggiornaTabella());

        setVisible(true);
    }

    private void aggiornaTabella() {
        String docenteSelezionato = (String) comboDocenti.getSelectedItem();
        ArrayList<Lezione> orario = LettoreFile.getOrarioDocente(docenteSelezionato);

        modello.setRowCount(0);

        for (Lezione l : orario) {
            modello.addRow(new Object[]{
                    l.getGiorno(),
                    l.getOrarioInizio(),
                    l.getMateria(),
                    l.getClasse(),
                    l.getDurata(),
                    l.getCodocenza()
            });
        }
    }
}
