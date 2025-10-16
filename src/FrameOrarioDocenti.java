import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FrameOrarioDocenti extends JFrame {
    private JComboBox<String> comboDocenti;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new JButton("Indietro");

    private Map<String, Color> coloriDocenti = new HashMap<>();
    private String[][] docentiCelle = new String[6][7]; // 6 righe, 7 colonne

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

        for (int i = 0; i < comboDocenti.getItemCount(); i++) {
            String docente = comboDocenti.getItemAt(i);
            coloriDocenti.put(docente, generaColoreCasuale());
        }

        String[] giorniSettimana = {" ", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
        String[][] dati = {
                {"08:00", "", "", "", "", "", ""},
                {"09:00", "", "", "", "", "", ""},
                {"10:00", "", "", "", "", "", ""},
                {"11:00", "", "", "", "", "", ""},
                {"12:00", "", "", "", "", "", ""},
                {"13:00", "", "", "", "", "", ""}
        };

        modello = new DefaultTableModel(dati, giorniSettimana) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(modello);
        table.setFont(new Font("SansSerif", Font.BOLD, 18));
        table.setRowHeight(75);  // altezza fissa a 75
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        int[] larghezze = {80, 130, 130, 130, 130, 130, 130};
        for (int i = 0; i < larghezze.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(larghezze[i]);
        }

        table.setDefaultRenderer(Object.class, new ColorRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

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

    private Color generaColoreCasuale() {
        Random rand = new Random();
        int r = 150 + rand.nextInt(106);
        int g = 150 + rand.nextInt(106);
        int b = 150 + rand.nextInt(106);
        return new Color(r, g, b);
    }

    private void aggiornaTabella() {
        String docenteSelezionato = (String) comboDocenti.getSelectedItem();
        ArrayList<Lezione> orario = LettoreFile.getOrarioDocente(docenteSelezionato);

        for (int r = 0; r < table.getRowCount(); r++) {
            for (int c = 1; c < table.getColumnCount(); c++) {
                table.setValueAt("", r, c);
                docentiCelle[r][c] = null;
            }
            docentiCelle[r][0] = null;
        }

        for (Lezione l : orario) {
            String giorno = l.getGiorno().toLowerCase();
            String orarioInizio = l.getOrarioInizio();

            int colonna = -1;
            if (giorno.contains("lun")) colonna = 1;
            else if (giorno.contains("mar")) colonna = 2;
            else if (giorno.contains("mer")) colonna = 3;
            else if (giorno.contains("gio")) colonna = 4;
            else if (giorno.contains("ven")) colonna = 5;
            else if (giorno.contains("sab")) colonna = 6;

            int riga = -1;
            if (orarioInizio.startsWith("08")) riga = 0;
            else if (orarioInizio.startsWith("09")) riga = 1;
            else if (orarioInizio.startsWith("10")) riga = 2;
            else if (orarioInizio.startsWith("11")) riga = 3;
            else if (orarioInizio.startsWith("12")) riga = 4;
            else if (orarioInizio.startsWith("13")) riga = 5;

            if (colonna != -1 && riga != -1) {
                // testo con materia e classe a capo usando \n
                String contenuto = l.getMateria() + "\n" + l.getClasse();
                if (l.getCodocenza() != '\0' && l.getCodocenza() != 'N') {
                    contenuto += "\n(c)";
                }
                table.setValueAt(contenuto, riga, colonna);
                docentiCelle[riga][colonna] = l.getCognome()[0];
            }
        }

        table.repaint();
    }

    private class ColorRenderer extends DefaultTableCellRenderer {
        private final JTextArea textArea = new JTextArea();

        public ColorRenderer() {
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(true);
            textArea.setFont(new Font("SansSerif", Font.BOLD, 18));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (column == 0) {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }

            textArea.setText(value != null ? value.toString() : "");
            String docente = docentiCelle[row][column];
            if (docente != null && coloriDocenti.containsKey(docente)) {
                textArea.setBackground(coloriDocenti.get(docente));
            } else {
                textArea.setBackground(Color.WHITE);
            }
            textArea.setForeground(Color.BLACK);

            // Mantieni altezza fissa 75 come da origine
            table.setRowHeight(row, 75);

            return textArea;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrameOrarioDocenti::new);
    }
}
