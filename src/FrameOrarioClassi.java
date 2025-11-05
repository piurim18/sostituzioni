import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FrameOrarioClassi extends JFrame {

    private final Color COLORE_PRIMARIO = new Color(70, 130, 180);
    private final Color COLORE_SECONDARIO = new Color(245, 245, 245);
    private final Color COLORE_BORDO = new Color(220, 220, 220);
    private final Font FONT_PRINCIPALE = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_TITOLO = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_TABELLA = new Font("Segoe UI", Font.BOLD, 14);

    private JComboBox<String> comboClassi;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new JButton("Indietro");

    private final String[] giorni = {"LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private final String[] ore = {"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};
    private Map<String, Color> coloriMaterie = new HashMap<>();

    public FrameOrarioClassi() {
        setTitle("Orario Classi");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona classe: "));
        comboClassi = new JComboBox<>();

        for (Lezione l : LettoreFile.lezioni) {
            String classe = l.getClasse();
            if (((DefaultComboBoxModel<String>) comboClassi.getModel()).getIndexOf(classe) == -1) {
                comboClassi.addItem(classe);
            }
        }

        for (Lezione l : LettoreFile.lezioni) {
            String materia = l.getMateria();
            if (materia != null && !materia.trim().isEmpty() && !coloriMaterie.containsKey(materia)) {
                coloriMaterie.put(materia, generaColoreCasuale());
            }
        }

        pannelloNord.add(comboClassi);
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
        table.setEnabled(true);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        table.setDefaultRenderer(Object.class, new ColorRenderer());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pannelloSud = new JPanel();
        pannelloSud.add(indietro);
        add(pannelloSud, BorderLayout.SOUTH);

        indietro.setBackground(new Color(70, 130, 180));
        indietro.setForeground(Color.WHITE);
        indietro.setFont(FONT_PRINCIPALE);
        indietro.addActionListener(e -> {
            this.dispose();
            new FramePanelScelta();
        });

        comboClassi.addActionListener(e -> aggiornaTabella());

        setVisible(true);
    }

    private Color generaColoreCasuale() {
        Random rand = new Random();
        int r = 100 + rand.nextInt(156);
        int g = 100 + rand.nextInt(156);
        int b = 100 + rand.nextInt(156);
        return new Color(r, g, b);
    }

    private int getRigaOra(String ora) {
        for (int i = 0; i < ore.length; i++) {
            if (ora.startsWith(ore[i].substring(0, 2))) {
                return i;
            }
        }
        return -1;
    }

    private void aggiornaTabella() {
        String classeSelezionata = (String) comboClassi.getSelectedItem();
        if (classeSelezionata == null) return;

        for (int r = 0; r < modello.getRowCount(); r++) {
            for (int c = 1; c < modello.getColumnCount(); c++) {
                modello.setValueAt("", r, c);
            }
        }

        ArrayList<ClasseCell> orario = LettoreFile.getgetoraclassis(classeSelezionata);
        for (ClasseCell l : orario) {
            String materia = l.getMateria();
            if (!coloriMaterie.containsKey(materia)) {
                coloriMaterie.put(materia, generaColoreCasuale());
            }

            for (int c = 1; c < modello.getColumnCount(); c++) {
                String colonna = modello.getColumnName(c);
                if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equalsIgnoreCase("lunedì")
                        || colonna.equalsIgnoreCase("MAR") && l.getGiorno().equalsIgnoreCase("martedì")
                        || colonna.equalsIgnoreCase("MER") && l.getGiorno().equalsIgnoreCase("mercoledì")
                        || colonna.equalsIgnoreCase("GIO") && l.getGiorno().equalsIgnoreCase("giovedì")
                        || colonna.equalsIgnoreCase("VEN") && l.getGiorno().equalsIgnoreCase("venerdì")
                        || colonna.equalsIgnoreCase("SAB") && l.getGiorno().equalsIgnoreCase("sabato")) {

                    int rigaOra = getRigaOra(l.getOrarioInizio());
                    int durata = 1;
                    try {

                        String durataStr = l.getDurata().replace("h", "").trim();
                        durata = Integer.parseInt(durataStr) / 100;
                    } catch (Exception ignored) {
                    }

                    for (int i = 0; i < durata; i++) {
                        int rigaCorrente = rigaOra + i;
                        if (rigaCorrente < modello.getRowCount()) {
                            String testo = "<html><center>" + materia + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
                            modello.setValueAt(testo, rigaCorrente, c);
                        }
                    }
                }
            }
        }
        table.repaint();
    }

    class ColorRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setVerticalAlignment(SwingConstants.CENTER);
            cell.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLORE_BORDO, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            if (column == 0) {
                cell.setBackground(COLORE_SECONDARIO);
                cell.setForeground(Color.BLACK);
                cell.setFont(FONT_TITOLO);
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

                    Color colore = coloriMaterie.get(materia);
                    cell.setBackground(colore != null ? colore : Color.WHITE);
                } else {
                    cell.setBackground(Color.WHITE);
                }

                Color sfondo = cell.getBackground();
                int luminanza = (int) (0.299 * sfondo.getRed() + 0.587 * sfondo.getGreen() + 0.114 * sfondo.getBlue());
                cell.setForeground(luminanza < 128 ? Color.WHITE : Color.BLACK);
                cell.setFont(FONT_TABELLA);
                return cell;
            }
        }
    }
}

