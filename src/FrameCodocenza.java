import javax.swing.*;
import java.awt.*;

public class FrameCodocenza extends JFrame {

    JComboBox<String> codocenza = new JComboBox<>();
    String [] codoc = {"S","N"};

    public FrameCodocenza(){
        setTitle("Seleziona Codocenza");

        for (String d : codoc) {
            codocenza.addItem(d);
        }

        setLayout(new BorderLayout());

        add(codocenza, BorderLayout.CENTER);

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {

        new FrameCodocenza();


    }
    }

