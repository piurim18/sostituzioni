import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrameOrarioClassi extends JFrame {
    private JComboBox<String> comboClassi;
    private JTable table;
    private DefaultTableModel modello;
    private JButton indietro = new JButton("Indietro");

    private final String[] giorni = {"LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private final String[] ore = {"08h00", "09h00", "10h00", "11h00", "12h00", "13h00"};

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

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottone indietro
        JPanel pannelloSud = new JPanel();
        pannelloSud.add(indietro);
        add(pannelloSud, BorderLayout.SOUTH);

        indietro.setBackground(new Color(70, 130, 180));
        indietro.addActionListener(e -> {
            dispose();
            new FrameSceltaOrario();
        });

        comboClassi.addActionListener(e -> aggiornatabella2());

        setVisible(true);
    }

//    private void aggiornaTabella() {
//        String classeSelezionata = (String) comboClassi.getSelectedItem();
//        ArrayList<Lezione> orario = LettoreFile.getOrarioClasse(classeSelezionata);
//
//        // Svuota le celle
//        for (int r = 0; r < modello.getRowCount(); r++) {
//            for (int c = 1; c < modello.getColumnCount(); c++) {
//                modello.setValueAt("", r, c);
//            }
//        }
//
//        // Inserisci le lezioni
//        for (Lezione l : orario) {
//            int colonnaGiorno = getColonnaGiorno(l.getGiorno());
//            int rigaOra = getRigaOra(l.getOrarioInizio());
//
//            if (colonnaGiorno != -1 && rigaOra != -1) {
//                String testo = "<html><center>" + l.getMateria() + "<br>Prof. " + String.join(", ", l.getCognome()) + "</center></html>";
//                modello.setValueAt(testo, rigaOra, colonnaGiorno);
//            }
//        }
//    }

    private void aggiornaTabella () {
        String classeSelezionata = (String) comboClassi.getSelectedItem();
        ArrayList<ClasseCell> orario = LettoreFile.getgetoraclassis(classeSelezionata);

        for (int r = 0; r < modello.getRowCount(); r++) {
            for (int c = 1; c < modello.getColumnCount(); c++) {

                modello.setValueAt("", r, c);

            }
        }


//        for (int r = 0; r < modello.getRowCount(); r++) {
//            for (int c = 1; c < modello.getColumnCount(); c++) { // partiamo da 1 se 0 è Giorno
//                String colonna = modello.getColumnName(c); // prende il nome della colonna
//                if (colonna.equalsIgnoreCase("LUN")) {
//                    modello.setValueAt("lun", r, c);
//                }
//                if (colonna.equalsIgnoreCase("SAB")) {
//                    modello.setValueAt("sab", r, c);
//                }
//            }
//        }


        for (ClasseCell l : orario) {

            for (int r = 0; r < modello.getRowCount(); r++) {
                for (int c = 1; c < modello.getColumnCount(); c++) {
                    String colonna = modello.getColumnName(c);
                    if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equals("lunedì")) {


                        if (l.getOrarioInizio().equals(ore[0])) {
                            System.out.println("CIAO: " + l.toString());
                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
                        }
                        if (l.getOrarioInizio().equals(ore[1])) {
                            System.out.println("CIAO: " + l.toString());
                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
                        }
                        if (l.getOrarioInizio().equals(ore[2])) {
                            System.out.println("CIAO: " + l.toString());
                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
                        }
                        if (l.getOrarioInizio().equals(ore[3])) {
                            System.out.println("CIAO: " + l.toString());
                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
                        }
                        if (l.getOrarioInizio().equals(ore[4])) {
                            System.out.println("CIAO: " + l.toString());
                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
                        }
                        if (l.getOrarioInizio().equals(ore[5])) {
                            System.out.println("CIAO: " + l.toString());
                            modello.setValueAt(l.getMateria() + ", " + l.getDurata(), r, c);
                        }

                    }
                }
            }

//            int colonnaGiorno = getColonnaGiorno(l.getGiorno());
//            int rigaOra = getRigaOra(l.getOrarioInizio());
//
//            if (colonnaGiorno != -1 && rigaOra != -1) {
//                String testo = "<html><center>" + l.getMateria() + "<br>Prof. " + String.join(", ", l.getGiorno()) + "</center></html>";
//                modello.setValueAt(testo, rigaOra, colonnaGiorno);
//            }


        }


        // Inserisci le lezioni
//        for (ClasseCell l : orario) {
//            int colonnaGiorno = getColonnaGiorno(l.getGiorno());
//            int rigaOra = getRigaOra(l.getOrarioInizio());
//
//            if (colonnaGiorno != -1 && rigaOra != -1) {
//                String testo = "<html><center>" + l.getMateria() + "<br>Prof. " + String.join(", ", l.getGiorno()) + "</center></html>";
//                modello.setValueAt(testo, rigaOra, colonnaGiorno);
//            }
//        }


    }

    private int getColonnaGiorno (String giorno){
        for (int i = 0; i < giorni.length; i++) {
            if (giorno.equalsIgnoreCase(giorni[i]))
                return i + 1;
        }
        return -1;
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
            for (int r = 0; r < modello.getRowCount(); r++) {
                for (int c = 1; c < modello.getColumnCount(); c++) {
                    String colonna = modello.getColumnName(c);
                    if (colonna.equalsIgnoreCase("LUN") && l.getGiorno().equalsIgnoreCase("lunedì")) {
                        int rigaOra = getRigaOra(l.getOrarioInizio());
                        String durataStrstr = l.getDurata().replace("h00", "").trim();
                        int durata = Integer.parseInt(durataStrstr);
                        for (int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < modello.getRowCount()) {
                                modello.setValueAt(l.getMateria() + ", " + l.getDurata(), rigaCorrente, c);
                            }
                        }
                    }

                    if (colonna.equalsIgnoreCase("MAR") && l.getGiorno().equalsIgnoreCase("Martedì")) {
                        int rigaOra = getRigaOra(l.getOrarioInizio());
                        String durataStrstr = l.getDurata().replace("h00", "").trim();
                        int durata = Integer.parseInt(durataStrstr);
                        for (int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < modello.getRowCount()) {
                                modello.setValueAt(l.getMateria() + ", " + l.getDurata(), rigaCorrente, c);
                            }
                        }
                    }

                    if (colonna.equalsIgnoreCase("MER") && l.getGiorno().equalsIgnoreCase("mercoledì")) {
                        int rigaOra = getRigaOra(l.getOrarioInizio());
                        String durataStrstr = l.getDurata().replace("h00", "").trim();
                        int durata = Integer.parseInt(durataStrstr);
                        for (int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < modello.getRowCount()) {
                                modello.setValueAt(l.getMateria() + ", " + l.getDurata(), rigaCorrente, c);
                            }
                        }
                    }

                    if (colonna.equalsIgnoreCase("GIO") && l.getGiorno().equalsIgnoreCase("giovedì")) {
                        int rigaOra = getRigaOra(l.getOrarioInizio());
                        String durataStrstr = l.getDurata().replace("h00", "").trim();
                        int durata = Integer.parseInt(durataStrstr);
                        for (int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < modello.getRowCount()) {
                                modello.setValueAt(l.getMateria() + ", " + l.getDurata(), rigaCorrente, c);
                            }
                        }
                    }

                    if (colonna.equalsIgnoreCase("VEN") && l.getGiorno().equalsIgnoreCase("venerdì")) {
                        int rigaOra = getRigaOra(l.getOrarioInizio());
                        String durataStrstr = l.getDurata().replace("h00", "").trim();
                        int durata = Integer.parseInt(durataStrstr);
                        for (int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < modello.getRowCount()) {
                                modello.setValueAt(l.getMateria() + ", " + l.getDurata(), rigaCorrente, c);
                            }
                        }
                    }

                    if (colonna.equalsIgnoreCase("SAB") && l.getGiorno().equalsIgnoreCase("sabato")) {
                        int rigaOra = getRigaOra(l.getOrarioInizio());
                        String durataStrstr = l.getDurata().replace("h00", "").trim();
                        int durata = Integer.parseInt(durataStrstr);
                        for (int i = 0; i < durata; i++) {
                            int rigaCorrente = rigaOra + i;
                            if (rigaCorrente < modello.getRowCount()) {
                                modello.setValueAt(l.getMateria() + ", " + l.getDurata(), rigaCorrente, c);
                            }
                        }
                    }


                }
            }
        }


    }
}