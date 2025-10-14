import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FrameOrarioDocenti extends JFrame {
    private JComboBox<String> comboDocenti;
    private JTable table;
    private DefaultTableModel modello;

    public FrameOrarioDocenti() {
        setTitle("Orario Docenti");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona docente: "));


        comboDocenti = new JComboBox<>();
        for (Lezione l : LettoreFile.lezioni) {
            String primoCognome = l.getCognome()[0];

            if (((DefaultComboBoxModel<String>) comboDocenti.getModel()).getIndexOf(primoCognome) == -1) {
                comboDocenti.addItem(primoCognome);
            }
        }

        pannelloNord.add(comboDocenti);
        add(pannelloNord, BorderLayout.NORTH);

        String[] giorniSettimana = {" ", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
        String[][] dati = {
                {"08:00", "", "", "", "", "", ""},
                {"09:00", "", "", "", "", "", ""},
                {"10:00", "", "", "", "", "", ""},
                {"11:00", "", "", "", "", "", ""},
                {"12:00", "", "", "", "", "", ""},
                {"13:00", "", "", "", "", "", ""},
        };

        modello = new DefaultTableModel(giorniSettimana, 0);
        table = new JTable(dati, giorniSettimana);
        add(new JScrollPane(table), BorderLayout.CENTER);
        table.setFont(new Font("SansSerif", Font.BOLD, 18));
        table.setRowHeight(75);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.isCellEditable(0, 0);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        int[] larghezze = {80, 130, 130, 130, 130, 130, 130};
        for (int i = 0; i < larghezze.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(larghezze[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);


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

    public static void main(String[] args) {
        new FrameOrarioDocenti();
    }
}
