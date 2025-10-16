import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrameOrarioDocenti extends JFrame {
    private JComboBox<String> comboDocenti;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new  JButton("Indietro");

    public FrameOrarioDocenti() {
        setTitle("Orario Docenti");
        setSize(900, 600);
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

        String [] giorniSettimana = {" ", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
        String [][] dati = {
                {"08:00", "", "", "", "", "", ""},
                {"09:00", "", "", "", "", "", ""},
                {"10:00", "", "", "", "", "", ""},
                {"11:00", "", "", "", "", "", ""},
                {"12:00", "", "", "", "", "", ""},
                {"13:00", "", "", "", "", "", ""},

        };

        modello = new DefaultTableModel(giorniSettimana, 0);
        table = new JTable(dati, giorniSettimana);
        //    table = new JTable(modello);
        add(new JScrollPane(table), BorderLayout.CENTER);
        table.setFont(new Font("SansSerif", Font.BOLD, 18));
        table.setRowHeight(75);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.isCellEditable(0,0);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //   table.setBackground(Color.pink);
        table.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        int[] larghezze = {80, 130, 130, 130, 130, 130, 130};
        for (int i = 0; i < larghezze.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(larghezze[i]);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        comboDocenti.addActionListener(e -> aggiornaTabella());

        JPanel panelSud = new JPanel();
        indietro.setBackground(new Color(70, 130, 180));
        panelSud.add(indietro);
        add(panelSud, BorderLayout.SOUTH);
        indietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FrameSceltaOrario();
            }
        });


        setVisible(true);
    }

    private void aggiornaTabella() {
        String docenteSelezionato = (String) comboDocenti.getSelectedItem();
        ArrayList<Lezione> orario = LettoreFile.getOrarioDocente(docenteSelezionato);

        // Svuota la tabella (tranne colonna orari)
        for (int r = 0; r < table.getRowCount(); r++) {
            for (int c = 1; c < table.getColumnCount(); c++) {
                table.setValueAt("", r, c);
            }
        }

        for (Lezione l : orario) {
            String giorno = l.getGiorno().toLowerCase();
            String orarioInizio = l.getOrarioInizio();

            // Mappa giorno → colonna
            int colonna = -1;
            if (giorno.contains("lun")) colonna = 1;
            else if (giorno.contains("mar")) colonna = 2;
            else if (giorno.contains("mer")) colonna = 3;
            else if (giorno.contains("gio")) colonna = 4;
            else if (giorno.contains("ven")) colonna = 5;
            else if (giorno.contains("sab")) colonna = 6;

            // Mappa orario → riga
            int riga = -1;
            if (orarioInizio.startsWith("08")) riga = 0;
            else if (orarioInizio.startsWith("09")) riga = 1;
            else if (orarioInizio.startsWith("10")) riga = 2;
            else if (orarioInizio.startsWith("11")) riga = 3;
            else if (orarioInizio.startsWith("12")) riga = 4;
            else if (orarioInizio.startsWith("13")) riga = 5;

            if (colonna != -1 && riga != -1) {
                String contenuto = l.getMateria() + " " + l.getClasse();
                if (l.getCodocenza() != '\0' && l.getCodocenza() != 'N') {
                    contenuto += " (c)";
                }
                table.setValueAt(contenuto, riga, colonna);
            }
        }
    }



    public static void main(String[] args) {
        new FrameOrarioDocenti();
    }
}
