//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrameOrarioDocenti extends JFrame {
    private JComboBox<String> comboDocenti;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new JButton("Indietro");
    private final String[] giorni = new String[]{"LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private final String[] ore = new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};
    private Map<String, Color> coloriMaterie = new HashMap();

    private Color generaColoreCasuale() {
        Random rand = new Random();
        int r = 100 + rand.nextInt(156);
        int g = 100 + rand.nextInt(156);
        int b = 100 + rand.nextInt(156);
        return new Color(r, g, b);
    }

    public FrameOrarioDocenti() {
        this.setTitle("Orario Docenti");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona docente: "));
        this.comboDocenti = new JComboBox();
        Set<String> cognomiUnici = new HashSet();

        for(Lezione l : LettoreFile.lezioni) {
            for(String cognome : l.getCognome()) {
                if (cognomiUnici.add(cognome)) {
                    this.comboDocenti.addItem(cognome);
                }
            }
        }

        for(Lezione l : LettoreFile.lezioni) {
            String materia = l.getMateria();
            if (materia != null && !materia.trim().isEmpty() && !this.coloriMaterie.containsKey(materia)) {
                this.coloriMaterie.put(materia, this.generaColoreCasuale());
            }
        }

        pannelloNord.add(this.comboDocenti);
        this.add(pannelloNord, "North");
        String[] colonne = new String[this.giorni.length + 1];
        colonne[0] = "Ora";
        System.arraycopy(this.giorni, 0, colonne, 1, this.giorni.length);
        this.modello = new DefaultTableModel(colonne, 0);

        for(String ora : this.ore) {
            Object[] riga = new Object[colonne.length];
            riga[0] = ora;

            for(int i = 1; i < colonne.length; ++i) {
                riga[i] = "";
            }

            this.modello.addRow(riga);
        }

        this.table = new JTable(this.modello);
        this.table.setFont(new Font("SansSerif", 1, 14));
        this.table.setRowHeight(75);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setEnabled(true);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        this.table.setDefaultRenderer(Object.class, new ColorRenderer());
        this.add(new JScrollPane(this.table), "Center");
        JPanel pannelloSud = new JPanel();
        pannelloSud.add(this.indietro);
        this.add(pannelloSud, "South");
        this.indietro.setBackground(new Color(70, 130, 180));
        this.indietro.addActionListener((e) -> {
            this.dispose();
            new FrameScelta();
        });
        this.comboDocenti.addActionListener((e) -> this.aggiornaTabella());
        this.setVisible(true);
    }

    private int getRigaOra(String ora) {
        for(int i = 0; i < this.ore.length; ++i) {
            if (ora.startsWith(this.ore[i].substring(0, 2))) {
                return i;
            }
        }

        return -1;
    }

    private int getColonnaGiorno(String giorno) {
        switch (giorno.toLowerCase()) {
            case "lunedì":
            case "lun":
                return 1;
            case "martedì":
            case "mar":
                return 2;
            case "mercoledì":
            case "mer":
                return 3;
            case "giovedì":
            case "gio":
                return 4;
            case "venerdì":
            case "ven":
                return 5;
            case "sabato":
            case "sab":
                return 6;
            default:
                return -1;
        }
    }

    private void aggiornaTabella() {
        String docenteSelezionato = (String)this.comboDocenti.getSelectedItem();
        if (docenteSelezionato != null) {
            for(int r = 0; r < this.modello.getRowCount(); ++r) {
                for(int c = 1; c < this.modello.getColumnCount(); ++c) {
                    this.modello.setValueAt("", r, c);
                }
            }

            ArrayList<Lezione> orarioDocente = new ArrayList();

            for(Lezione l : LettoreFile.lezioni) {
                for(String cognome : l.getCognome()) {
                    if (cognome.trim().equalsIgnoreCase(docenteSelezionato.trim())) {
                        orarioDocente.add(l);
                        break;
                    }
                }
            }

            for(Lezione l : orarioDocente) {
                String materia = l.getMateria();
                if (!this.coloriMaterie.containsKey(materia)) {
                    this.coloriMaterie.put(materia, this.generaColoreCasuale());
                }

                for(int c = 1; c < this.modello.getColumnCount(); ++c) {
                    String colonna = this.modello.getColumnName(c);
                    if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equalsIgnoreCase("lunedì") || colonna.equalsIgnoreCase("MAR") && l.getGiorno().equalsIgnoreCase("martedì") || colonna.equalsIgnoreCase("MER") && l.getGiorno().equalsIgnoreCase("mercoledì") || colonna.equalsIgnoreCase("GIO") && l.getGiorno().equalsIgnoreCase("giovedì") || colonna.equalsIgnoreCase("VEN") && l.getGiorno().equalsIgnoreCase("venerdì") || colonna.equalsIgnoreCase("SAB") && l.getGiorno().equalsIgnoreCase("sabato")) {
                        int rigaOra = this.getRigaOra(l.getOrarioInizio());
                        int durata = 1;

                        try {
                            String durataStr = l.getDurata().replace("h", "").trim();
                            durata = Integer.parseInt(durataStr) / 100;
                        } catch (Exception var13) {
                            durata = 1;
                        }
                        System.out.println(durata);

                        for(int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < this.modello.getRowCount()) {
                                String testo = "<html><center>" + materia + "<br>" + String.join(", ", l.getClasse()) + "</center></html>";
                                this.modello.setValueAt(testo, rigaCorrente, c);
                            }
                        }
                    }
                }
            }

            this.table.repaint();
        }
    }

    public static void main(String[] args) {
        new FrameOrarioDocenti();
    }

    class ColorRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setHorizontalAlignment(0);
            cell.setVerticalAlignment(0);
            if (column == 0) {
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
                return cell;
            } else {
                String testo = value != null ? value.toString() : "";
                if (!testo.isEmpty()) {
                    String materia;
                    if (testo.toLowerCase().startsWith("<html")) {
                        String withBreaks = testo.replaceAll("(?i)<br\\s*/?>", "\n");
                        String pulito = withBreaks.replaceAll("<[^>]*>", "").trim();
                        String[] parts = pulito.split("\\n");
                        materia = parts.length > 0 ? parts[0].trim() : pulito.trim();
                    } else {
                        materia = testo.split("\\(")[0].trim();
                    }

                    Color colore = (Color)FrameOrarioDocenti.this.coloriMaterie.get(materia);
                    if (colore != null) {
                        cell.setBackground(colore);
                    } else {
                        cell.setBackground(Color.WHITE);
                    }
                } else {
                    cell.setBackground(Color.WHITE);
                }

                Color sfondo = cell.getBackground();
                int luminanza = (int)(0.299 * (double)sfondo.getRed() + 0.587 * (double)sfondo.getGreen() + 0.114 * (double)sfondo.getBlue());
                cell.setForeground(luminanza < 128 ? Color.WHITE : Color.BLACK);
                return cell;
            }
        }
    }
}
