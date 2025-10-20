import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class FrameOrarioDocenti extends JFrame {
    private JComboBox<String> comboDocenti;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new JButton("Indietro");

    private final String[] giorni = {"LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private final String[] ore = {"08h00", "09h00", "10h00", "11h00", "12h00", "13h00"};

    private Map<String, Color> coloriMaterie = new HashMap<>();

    private Color generaColoreCasuale() {
        Random rand = new Random();
        int r = 100 + rand.nextInt(156);
        int g = 100 + rand.nextInt(156);
        int b = 100 + rand.nextInt(156);
        return new Color(r, g, b);
    }

    class ColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setVerticalAlignment(SwingConstants.CENTER);

            if (column == 0) {
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
                return cell;
            }

            String testo = (value != null) ? value.toString() : "";
            if (!testo.isEmpty()) {
                String materia;
                if (testo.toLowerCase().startsWith("<html")) {
                    // 1) sostituisco i <br> con newline, poi rimuovo gli altri tag
                    String withBreaks = testo.replaceAll("(?i)<br\\s*/?>", "\n");
                    String pulito = withBreaks.replaceAll("<[^>]*>", "").trim();
                    // 2) prendo la prima riga come materia
                    String[] parts = pulito.split("\\n");
                    materia = parts.length > 0 ? parts[0].trim() : pulito.trim();
                } else {
                    materia = testo.split("\\(")[0].trim();
                }

                Color colore = coloriMaterie.get(materia);
                if (colore != null) {
                    cell.setBackground(colore);
                } else {
                    cell.setBackground(Color.WHITE);
                }
            } else {
                cell.setBackground(Color.WHITE);
            }

            Color sfondo = cell.getBackground();
            int luminanza = (int) (0.299 * sfondo.getRed() + 0.587 * sfondo.getGreen() + 0.114 * sfondo.getBlue());
            cell.setForeground(luminanza < 128 ? Color.WHITE : Color.BLACK);

            return cell;
        }
    }

    public FrameOrarioDocenti() {
        setTitle("Orario Docenti");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona docente: "));
        comboDocenti = new JComboBox<>();

        Set<String> cognomiUnici = new HashSet<>();
        for (Lezione l : LettoreFile.lezioni) {
            for (String cognome : l.getCognome()) {
                if (cognomiUnici.add(cognome)) {
                    comboDocenti.addItem(cognome);
                }
            }
        }

        for (Lezione l : LettoreFile.lezioni) {
            String materia = l.getMateria();
            if (materia != null && !materia.trim().isEmpty() && !coloriMaterie.containsKey(materia)) {
                coloriMaterie.put(materia, generaColoreCasuale());
            }
        }

        pannelloNord.add(comboDocenti);
        add(pannelloNord, BorderLayout.NORTH);

        String[] colonne = new String[giorni.length + 1];
        colonne[0] = "Ora";
        System.arraycopy(giorni, 0, colonne, 1, giorni.length);

        modello = new DefaultTableModel(colonne, 0);
        for (String ora : ore) {
            Object[] riga = new Object[colonne.length];
            riga[0] = ora;
            for (int i = 1; i < colonne.length; i++) riga[i] = "";
            modello.addRow(riga);
        }

        table = new JTable(modello);
        table.setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setRowHeight(75);
        table.getTableHeader().setReorderingAllowed(false);

        // Nota: alcune LAF non mostrano i background nelle componenti "disabilitate"
        // quindi manteniamo la tabella abilitata ma disabilitiamo la selezione.
        table.setEnabled(true);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        table.setDefaultRenderer(Object.class, new ColorRenderer());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pannelloSud = new JPanel();
        pannelloSud.add(indietro);
        add(pannelloSud, BorderLayout.SOUTH);

        indietro.setBackground(new Color(70, 130, 180));
        indietro.addActionListener(e -> {
            dispose();
            new FrameScelta();
        });

        comboDocenti.addActionListener(e -> aggiornaTabella());

        setVisible(true);
    }

    private int getRigaOra(String ora) {
        for (int i = 0; i < ore.length; i++) {
            if (ora.startsWith(ore[i].substring(0, 2))) return i;
        }
        return -1;
    }

    private int getColonnaGiorno(String giorno) {
        giorno = giorno.toLowerCase();
        switch (giorno) {
            case "lunedì": case "lun": return 1;
            case "martedì": case "mar": return 2;
            case "mercoledì": case "mer": return 3;
            case "giovedì": case "gio": return 4;
            case "venerdì": case "ven": return 5;
            case "sabato": case "sab": return 6;
            default: return -1;
        }
    }

    private void aggiornaTabella() {
        String docenteSelezionato = (String) comboDocenti.getSelectedItem();
        if (docenteSelezionato == null) return;

        for (int r = 0; r < modello.getRowCount(); r++) {
            for (int c = 1; c < modello.getColumnCount(); c++) {
                modello.setValueAt("", r, c);
            }
        }

        ArrayList<Lezione> orarioDocente = new ArrayList<>();
        for (Lezione l : LettoreFile.lezioni) {
            for (String cognome : l.getCognome()) {
                if (cognome.equalsIgnoreCase(docenteSelezionato)) {
                    orarioDocente.add(l);
                    break;
                }
            }
        }

        for (Lezione l : orarioDocente) {
            int col = getColonnaGiorno(l.getGiorno());
            int row = getRigaOra(l.getOrarioInizio());

            if (col == -1 || row == -1) continue;

            int durata = 1;
            try {
                String durataStr = l.getDurata().replace("h00", "").trim();
                durata = Integer.parseInt(durataStr);
            } catch (Exception e) { }

            String testo = "<html><center>" + l.getMateria() + "<br>" + l.getClasse() + "</center></html>";

            for (int i = 0; i < durata && (row + i) < modello.getRowCount(); i++) {
                modello.setValueAt(testo, row + i, col);
            }
        }

        table.repaint();
    }

    public static void main(String[] args) {
        new FrameOrarioDocenti();
    }
}
