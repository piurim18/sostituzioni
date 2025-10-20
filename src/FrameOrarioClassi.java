import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FrameOrarioClassi extends JFrame {
    private JComboBox<String> comboClassi;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new JButton("Indietro");

    private final String[] giorni = {"LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private final String[] ore = {"08h00", "09h00", "10h00", "11h00", "12h00", "13h00"};
    private String cella;

    private Map<String, Color> coloriMaterie = new HashMap<>();

    private Color generaColoreCasuale() {
        Random rand = new Random();
        int r = 100 + rand.nextInt(156);
        int g = 100 + rand.nextInt(156);
        int b = 100 + rand.nextInt(156);
        return new Color(r, g, b);
    }

    // Renderer personalizzato per gestire testo multilinea e colore in base alla materia
    class ColorRenderer extends DefaultTableCellRenderer {
        private JTextArea textArea = new JTextArea();

        public ColorRenderer() {
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(true);
            textArea.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (column == 0) { // colonna ora (prima colonna)
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                return c;
            }

            String testo = (value != null) ? value.toString() : "";
            textArea.setText(testo);

            if (!testo.isEmpty()) {
                // Estrai la materia dal testo (prima parte prima della virgola)
                String materia = testo.split(",")[0].trim();

                Color colore = coloriMaterie.get(materia);
                if (colore != null) {
                    textArea.setBackground(colore);
                } else {
                    textArea.setBackground(Color.WHITE);
                }
            } else {
                textArea.setBackground(Color.WHITE);
            }

            return textArea;
        }
    }
    public FrameOrarioClassi() {
        setTitle("Orario Classi");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannello nord
        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona classe: "));
        comboClassi = new JComboBox<>();

        for (Lezione l : LettoreFile.lezioni) {
            String classe = l.getClasse();
            if (((DefaultComboBoxModel<String>) comboClassi.getModel()).getIndexOf(classe) == -1) {
                comboClassi.addItem(classe);

                // Assegna un colore casuale alla classe se non già assegnato
                coloriMaterie.put(classe, generaColoreCasuale());
            }
        }

        pannelloNord.add(comboClassi);
        add(pannelloNord, BorderLayout.NORTH);

        // Tabella orario
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
        table.setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setRowHeight(75);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        // Applica il renderer personalizzato per colorare le celle in base alla classe
        table.setDefaultRenderer(Object.class, new ColorRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottone indietro
        JPanel pannelloSud = new JPanel();
        pannelloSud.add(indietro);
        add(pannelloSud, BorderLayout.SOUTH);

        indietro.setBackground(new Color(70, 130, 180));
        indietro.addActionListener(e -> {
            dispose();
            new FrameScelta();
        });

        comboClassi.addActionListener(e -> aggiornatabella2());

        setVisible(true);
    }




    private int getRigaOra(String ora){
        for (int i = 0; i < ore.length; i++) {
            if (ora.startsWith(ore[i].substring(0, 2))) return i;
        }
        return -1;
    }


    private void aggiornatabella2() {

        String classeSelezionata = (String) comboClassi.getSelectedItem();
        ArrayList<ClasseCell> orario = LettoreFile.getgetoraclassis(classeSelezionata);

        for (int r = 0; r < modello.getRowCount(); r++) {
            for (int c = 1; c < modello.getColumnCount(); c++) {

                modello.setValueAt("", r, c);


            }
        }


//        for (ClasseCell l : orario) {
//
//            for (int r = 0; r < modello.getRowCount(); r++) {
//                for (int c = 1; c < modello.getColumnCount(); c++) {
//                    String colonna = modello.getColumnName(c);
//                    if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equals("lunedì")) {
//
//
//                        if (l.getOrarioInizio().equals(ore[0])) {
//                            System.out.println("CIAO: " + l.toString());
//                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
//                        }
//                    }
//                }
//            }
//
//            for (int r = 1; r < modello.getRowCount(); r++) {
//                for (int c = 1; c < modello.getColumnCount(); c++) {
//                    String colonna = modello.getColumnName(c);
//                    if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equals("lunedì")) {
//
//
//                        if (l.getOrarioInizio().equals(ore[1])) {
//                            System.out.println("CIAO: " + l.toString());
//                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
//                        }
//                    }
//                }
//            }
//
//        }

        /*
                                int rigaOra = getRigaOra(l.getOrarioInizio());
                        if (r == rigaOra){
                            System.out.println("CIAO: " +l.toString());
                            modello.setValueAt(l.getMateria() + ", " +l.getDurata(),r,c);
                        }
                    }
         */

        for (ClasseCell l : orario) {

            String materia = l.getMateria();

            // Assegna colore se non presente
            if (!coloriMaterie.containsKey(materia)) {
                coloriMaterie.put(materia, generaColoreCasuale());
            }


            //for (int r = 0; r < modello.getRowCount(); r++) {
            for (int c = 1; c < modello.getColumnCount(); c++) {
                String colonna = modello.getColumnName(c);

                if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equalsIgnoreCase("lunedì") || colonna.equalsIgnoreCase("MAR") && l.getGiorno().equalsIgnoreCase("Martedì") || colonna.equalsIgnoreCase("MER") && l.getGiorno().equalsIgnoreCase("mercoledì") || colonna.equalsIgnoreCase("GIO") && l.getGiorno().equalsIgnoreCase("giovedì") || colonna.equalsIgnoreCase("VEN") && l.getGiorno().equalsIgnoreCase("venerdì") || colonna.equalsIgnoreCase("SAB") && l.getGiorno().equalsIgnoreCase("sabato")) {
                    int rigaOra = getRigaOra(l.getOrarioInizio());
                    String durataStrstr = l.getDurata().replace("h00", "").trim();
                    int durata = Integer.parseInt(durataStrstr);
                    for (int i = 0; i < durata; i++) {
                        int rigaCorrente = rigaOra + i;
                        if (rigaCorrente < modello.getRowCount()) {
                            cella = "<html><center>" + l.getMateria() + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
                            //modello.setValueAt(cella, rigaCorrente, c);
                            modello.setValueAt(materia + ", "+String.join(", ", l.getCognome()),rigaCorrente,c);
                        }
                        // }
                    }
                }
            }
        }

//                    if (colonna.equalsIgnoreCase("MAR") && l.getGiorno().equalsIgnoreCase("Martedì")) {
//                        int rigaOra = getRigaOra(l.getOrarioInizio());
//                        String durataStrstr = l.getDurata().replace("h00", "").trim();
//                        int durata = Integer.parseInt(durataStrstr);
//                        for (int i = 0; i < durata; i++) {
//                            int rigaCorrente = rigaOra + i;
//                            if (rigaCorrente < modello.getRowCount()) {
//                                cella = "<html><center>" + l.getMateria() + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
//                                modello.setValueAt(cella, rigaCorrente, c);
//                            }
//                        }
//                    }
//
//                    if (colonna.equalsIgnoreCase("MER") && l.getGiorno().equalsIgnoreCase("mercoledì")) {
//                        int rigaOra = getRigaOra(l.getOrarioInizio());
//                        String durataStrstr = l.getDurata().replace("h00", "").trim();
//                        int durata = Integer.parseInt(durataStrstr);
//                        for (int i = 0; i < durata; i++) {
//                            int rigaCorrente = rigaOra + i;
//                            if (rigaCorrente < modello.getRowCount()) {
//                                cella = "<html><center>" + l.getMateria() + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
//                                modello.setValueAt(cella, rigaCorrente, c);
//                            }
//                        }
//                    }
//
//                    if (colonna.equalsIgnoreCase("GIO") && l.getGiorno().equalsIgnoreCase("giovedì")) {
//                        int rigaOra = getRigaOra(l.getOrarioInizio());
//                        String durataStrstr = l.getDurata().replace("h00", "").trim();
//                        int durata = Integer.parseInt(durataStrstr);
//                        for (int i = 0; i < durata; i++) {
//                            int rigaCorrente = rigaOra + i;
//                            if (rigaCorrente < modello.getRowCount()) {
//                                cella = "<html><center>" + l.getMateria() + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
//                                modello.setValueAt(cella, rigaCorrente, c);
//                            }
//                        }
//                    }
//
//                    if (colonna.equalsIgnoreCase("VEN") && l.getGiorno().equalsIgnoreCase("venerdì")) {
//                        int rigaOra = getRigaOra(l.getOrarioInizio());
//                        String durataStrstr = l.getDurata().replace("h00", "").trim();
//                        int durata = Integer.parseInt(durataStrstr);
//                        for (int i = 0; i < durata; i++) {
//                            int rigaCorrente = rigaOra + i;
//                            if (rigaCorrente < modello.getRowCount()) {
//                                cella = "<html><center>" + l.getMateria() + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
//                                modello.setValueAt(cella, rigaCorrente, c);
//                            }
//                        }
//                    }
//
//                    if (colonna.equalsIgnoreCase("SAB") && l.getGiorno().equalsIgnoreCase("sabato")) {
//                        int rigaOra = getRigaOra(l.getOrarioInizio());
//                        String durataStrstr = l.getDurata().replace("h00", "").trim();
//                        int durata = Integer.parseInt(durataStrstr);
//                        for (int i = 0; i < durata; i++) {
//                            int rigaCorrente = rigaOra + i;
//                            if (rigaCorrente < modello.getRowCount()) {
//                                cella = "<html><center>" + l.getMateria() + "<br>" + String.join(", ", l.getCognome()) + "</center></html>";
//                                modello.setValueAt(cella, rigaCorrente, c);
//                            }
//                        }
//                    }
        //}
        //}
        //}
        table.repaint();
    }

}