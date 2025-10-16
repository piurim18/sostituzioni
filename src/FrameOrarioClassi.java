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
    private JButton indietro= new JButton("Indietro");;

    public FrameOrarioClassi() {
        setTitle("Orario Classi");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        setLayout(new BorderLayout());

        JPanel pannelloNord = new JPanel();
        pannelloNord.add(new JLabel("Seleziona classe: "));

        // Riempi la combo con i nomi dei docenti presenti nel file
        comboClassi = new JComboBox<>();
        for (Lezione l : LettoreFile.lezioni) {
            String primaClasse = l.getClasse();

            if (((DefaultComboBoxModel<String>) comboClassi.getModel()).getIndexOf(primaClasse) == -1) {
                comboClassi.addItem(primaClasse);
            }
        }


        pannelloNord.add(comboClassi);
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

        comboClassi.addActionListener(e -> aggiornaTabella());
        JPanel panelSud = new JPanel();
        panelSud.add(indietro);
        add(panelSud, BorderLayout.SOUTH);
        indietro.setBackground(new Color(70, 130, 180));
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
        String classeSelezionata = (String) comboClassi.getSelectedItem();
        ArrayList<Lezione> orario = LettoreFile.getOrarioDocente(classeSelezionata );

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
        new FrameOrarioClassi();
    }
}
