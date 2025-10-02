import javax.swing.*;
import java.awt.*;

public class FrameDocenti extends JFrame {

    JComboBox<String> docente = new JComboBox<>();
    String [] dati = {"docente1","docente2","docente3"};

    public FrameDocenti() {

        setTitle("Seleziona Docente");

        for (String d : dati) {
            docente.addItem(d);
        }

        setLayout(new BorderLayout());

        add(docente, BorderLayout.CENTER);

        setSize(150, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {

        new FrameIniziale();
        new FrameDocenti();


    }

}
