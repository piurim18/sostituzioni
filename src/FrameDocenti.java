import javax.swing.*;
import java.awt.*;

public class FrameDocenti extends JFrame {

    JComboBox<String> docente = new JComboBox<>();
    String [] dati = {"Cognome1", "Cognome2", "Cognome3", "Cognome4", "Cognome5", "Cognome6", "Cognome7", "Cognome8", "Cognome9", "Cognome10", "Cognome11", "Cognome12", "Cognome13", "Cognome14", "Cognome15", "Cognome16", "Cognome17", "Cognome18", "Cognome19", "Cognome20", "Cognome21", "Cognome22", "Cognome23", "Cognome24", "Cognome25", "Cognome26", "Cognome27", "Cognome28", "Cognome29", "Cognome30", "Cognome31", "Cognome32", "Cognome33", "Cognome34", "Cognome35", "Cognome36", "Cognome37", "Cognome38", "Cognome39", "Cognome40", "Cognome41", "Cognome42", "Cognome43", "Cognome44", "Cognome45", "Cognome46", "Cognome47", "Cognome48", "Cognome49", "Cognome50", "Cognome51", "Cognome52", "Cognome53", "Cognome54", "Cognome55", "Cognome56", "Cognome57", "Cognome58", "Cognome59", "Cognome60", "Cognome61", "Cognome62", "Cognome63", "Cognome64", "Cognome65", "Cognome66", "Cognome67", "Cognome68", "Cognome69", "Cognome70", "Cognome71", "Cognome72", "Cognome73"};

    public FrameDocenti() {

        setTitle("Seleziona Docente");

        for (String d : dati) {
            docente.addItem(d);
        }

        setLayout(new BorderLayout());

        add(docente, BorderLayout.CENTER);

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {

        new FrameIniziale();
        new FrameDocenti();


    }

}
