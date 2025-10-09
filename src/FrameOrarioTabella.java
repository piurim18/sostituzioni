import javax.swing.*;
import java.awt.*;

public class FrameOrarioTabella extends JFrame {

    private String [] giorniSettimana = {" ", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"};
    private String [][] dati = {
            {"08:00", "", "", "", "", "", ""},
            {"09:00", "", "", "", "", "", ""},
            {"10:00", "", "", "", "", "", ""},
            {"11:00", "", "", "", "", "", ""},
            {"12:00", "", "", "", "", "", ""},
            {"13:00", "", "", "", "", "", ""},

    };
    private JTable table = new JTable(dati, giorniSettimana);


    public FrameOrarioTabella(){
        setTitle("Orario Docente");
        setSize(880, 530);

        table.setFont(new Font("SansSerif", Font.BOLD, 18));
        table.setRowHeight(75);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] larghezze = {80, 130, 130, 130, 130, 130, 130};
        for (int i = 0; i < larghezze.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(larghezze[i]);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new FrameOrarioTabella();
    }

}