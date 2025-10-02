import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrarioDocente extends JFrame {

    private String [] giorniSettimana = {" ", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private String [][] dati = {
            {"08:00-09:00", "", "", "", "", "", ""},
            {"09:00-10:00", "", "", "", "", "", ""},
            {"10:00-11:00", "", "", "", "", "", ""},
            {"11:00-12:00", "", "", "", "", "", ""},
            {"12:00-13:00", "", "", "", "", "", ""},
            {"13:00-14:00", "", "", "", "", "", ""},
            {"14:00-15:00", "", "", "", "", "", ""}
    };
    private JTable table = new JTable(dati, giorniSettimana);

    public OrarioDocente(){
        setTitle("Orario Docente");
        setSize(600, 400);


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setVisible(true);
    }

    public static void main(String[] args) {

        new OrarioDocente();

    }
}
